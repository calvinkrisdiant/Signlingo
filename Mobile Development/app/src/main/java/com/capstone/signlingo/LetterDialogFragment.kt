package com.capstone.signlingo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.signlingo.data.Letter
import com.capstone.signlingo.databinding.FragmentLetterDialogBinding

class LetterDialogFragment(private val letter: Letter) : DialogFragment() {

    private var _binding: FragmentLetterDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.letterImage.setImageResource(letter.imageResId)
        binding.letterTitle.text = letter.title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    companion object {
        const val TAG = "LetterDialogFragment"
    }
}
