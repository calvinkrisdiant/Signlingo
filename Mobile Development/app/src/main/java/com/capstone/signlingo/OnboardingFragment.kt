package com.capstone.signlingo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class OnboardingFragment : Fragment() {

    private var title: String? = null
    private var description: String? = null
    private var imageResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
            imageResId = it.getInt(ARG_IMAGE_RES_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
//        view.findViewById<TextView>(R.id.textViewTitle).text = title
        view.findViewById<TextView>(R.id.textViewDescription).text = description
        view.findViewById<ImageView>(R.id.imageView).setImageResource(imageResId)
        return view
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE_RES_ID = "image_res_id"

        @JvmStatic
        fun newInstance(title: String, description: String, imageResId: Int) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putInt(ARG_IMAGE_RES_ID, imageResId)
                }
            }
    }
}
