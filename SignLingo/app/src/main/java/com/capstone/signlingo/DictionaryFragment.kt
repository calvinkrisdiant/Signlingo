package com.capstone.signlingo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.signlingo.adapter.DictionaryAdapter
import com.capstone.signlingo.data.Letter
import com.capstone.signlingo.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {

    private var _binding: com.capstone.signlingo.databinding.FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val letters = getLetterData()
        val adapter = DictionaryAdapter(letters) { letter ->
            showOverlay(letter)
        }

        binding.lettersRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.lettersRecyclerView.adapter = adapter
    }

    private fun getLetterData(): List<Letter> {
        return listOf(
            Letter("Letter A", R.drawable.letter_a),
            Letter("Letter B", R.drawable.letter_b),
            Letter("Letter C", R.drawable.letter_c),
            Letter("Letter D", R.drawable.letter_d),
            Letter("Letter E", R.drawable.letter_e),
            Letter("Letter F", R.drawable.letter_f),
            Letter("Letter G", R.drawable.letter_g),
            Letter("Letter H", R.drawable.letter_h),
            Letter("Letter I", R.drawable.letter_i),
            Letter("Letter J", R.drawable.letter_j),
            Letter("Letter K", R.drawable.letter_k),
            Letter("Letter L", R.drawable.letter_l),
            Letter("Letter M", R.drawable.letter_m),
            Letter("Letter N", R.drawable.letter_n),
            Letter("Letter O", R.drawable.letter_o),
            Letter("Letter P", R.drawable.letter_p),
            Letter("Letter Q", R.drawable.letter_q),
            Letter("Letter R", R.drawable.letter_r),
            Letter("Letter S", R.drawable.letter_s),
            Letter("Letter T", R.drawable.letter_t),
            Letter("Letter U", R.drawable.letter_u),
            Letter("Letter V", R.drawable.letter_v),
            Letter("Letter W", R.drawable.letter_w),
            Letter("Letter X", R.drawable.letter_x),
            Letter("Letter Y", R.drawable.letter_y),
            Letter("Letter Z", R.drawable.letter_z)
        )
    }

    private fun showOverlay(letter: Letter) {
        val dialogFragment = LetterDialogFragment(letter)
        dialogFragment.show(parentFragmentManager, LetterDialogFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
