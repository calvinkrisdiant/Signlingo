package com.capstone.signlingo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.signlingo.api.ApiService
import com.capstone.signlingo.databinding.FragmentScanBinding
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var imagePreview: ImageView
    private lateinit var galleryButton: Button
    private lateinit var cameraButton: Button
    private lateinit var analyzeButton: Button
    private lateinit var apiService: ApiService
    private var selectedImageUri: Uri? = null
    private var capturedImage: Bitmap? = null
    private var currentUri: Uri? = null
    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentUri = uri
            withUCrop(currentUri!!)
        }
    }
    private val launchCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    selectedImageUri = resultUri
                    setPreview()
                }
            }
        }
    private fun setPreview() {
        Glide.with(requireContext())
            .load(selectedImageUri)
            .centerCrop()
            .transform(RoundedCorners(32))
            .into(binding.imagePreview)
    }


    private fun withUCrop(uri: Uri) {
        val timeStamp = Date().time
        val cachedImage = File(requireActivity().cacheDir, "cropped_image_${timeStamp}.jpg")
        val destinationUri = Uri.fromFile(cachedImage)
        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launchCrop.launch(this)
        }
    }
    private val timeStamp: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
            Date()
        )
    fun getImageUri(context: Context): Uri {
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
            }
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        return uri ?: getImageUriForPreQ(context)
    }

    private fun getImageUriForPreQ(context: Context): Uri {
        val filesDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }
    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            withUCrop(currentUri!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        binding.galleryButton.setOnClickListener {
            launchGallery.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        binding.cameraButton.setOnClickListener {
            currentUri = getImageUri(requireActivity())
            launchCamera.launch(currentUri)
        }

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://model-signlingo-gl27v7jzyq-et.a.run.app/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.analyzeButton.setOnClickListener {
            selectedImageUri?.let { uri ->
                analyzeImage(uri)
            } ?: run {
                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun analyzeImage(uri: Uri) {
        val path = getPathFromUri(requireContext(), uri)
        if (path == null) {
            Toast.makeText(requireContext(), "Failed to get file path from URI", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(path)
        if (!file.exists()) {
            Toast.makeText(requireContext(), "File does not exist", Toast.LENGTH_SHORT).show()
            return
        }

        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

        Log.d("Upload", "Uploading file: ${file.name}")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.uploadImage(body)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Upload", "Response: $responseBody")
                        if (responseBody != null) {
                            handleResponse(responseBody)
                        } else {
                            Toast.makeText(requireContext(), "Response is empty", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = "Upload failed: ${response.message()}"
                        Log.e("ScanFragment", "Upload failed: $errorBody")
                        binding.predictionResult.text = "The gesture is incorrect"
                        Toast.makeText(requireContext(), "$errorMessage. Error body: $errorBody", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Upload error: ${e.message}"
                Log.e("ScanFragment", errorMessage)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String? {
        var filePath: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            if (idx != -1) {
                filePath = cursor.getString(idx)
            }
            cursor.close()
        }
        return filePath ?: uri.path
    }

    private fun handleResponse(responseBody: String) {
        try {
            val jsonResponse = JSONObject(responseBody)
            if (jsonResponse.has("error")) {
                val error = jsonResponse.getString("error")
                val confidence = jsonResponse.getDouble("confidence")
                binding.predictionResult.text = "Error: $error, Confidence: $confidence"
            } else if (jsonResponse.has("class")) {
                val gestureClass = jsonResponse.getString("class")
                val confidence = jsonResponse.getDouble("confidence")
                binding.predictionResult.text = "Class: $gestureClass, Confidence: ${(confidence*100).toInt()}%"
            } else {
                binding.predictionResult.text = "Unexpected response format"
                Toast.makeText(requireContext(), "Unexpected response format", Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            binding.predictionResult.text = "Failed to parse response"
            Toast.makeText(requireContext(), "Failed to parse response", Toast.LENGTH_SHORT).show()
        }
    }
}
