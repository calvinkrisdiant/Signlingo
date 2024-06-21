package com.capstone.signlingo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.signlingo.adapter.NewsAdapter
import com.capstone.signlingo.adapter.TestimonialsAdapter
import com.capstone.signlingo.adapter.TipsAdapter
import com.capstone.signlingo.data.News
import com.capstone.signlingo.data.Testimonial
import com.capstone.signlingo.data.Tips
import com.capstone.signlingo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        displayUsername()
    }

    private fun setupRecyclerViews() {
        // Setup News RecyclerView
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.newsRecyclerView.adapter = NewsAdapter(getNewsData())

        // Setup Tips and Tricks RecyclerView
        binding.tipsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.tipsRecyclerView.adapter = TipsAdapter(getTipsData())

        // Setup Testimonials RecyclerView
        binding.testimonialsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.testimonialsRecyclerView.adapter = TestimonialsAdapter(getTestimonialsData())
    }

    private fun displayUsername() {
        val sharedPref = requireActivity().getSharedPreferences("SignLingoPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "User")
        binding.usernameTextView.text = "Hi, $userName"
    }

    private fun getNewsData(): List<News> {
        // Replace with your data source
        return listOf(
            News("Sign Language Evolution through Computer Modeling", "Recent research uses computational programs..."),
            News("Sign Language Week 2024 in the UK", "In the UK, this week is celebrated as Sign Language Week..."),
            News("Sign Language Recognition in South Africa", "South African Sign Language will become the 12th official language..."),
            News("Sign Language as a Subject in Mississippi", "The state of Mississippi now allows students to choose sign language..."),
            News("Sign Language Learning Center in Juba, South Sudan", "A new sign language learning center opened in Juba...")
        )
    }

    private fun getTipsData(): List<Tips> {
        // Replace with your data source
        return listOf(
            Tips("Practice in Front of the Mirror", "Check your hand movements and make sure they are correct."),
            Tips("Learn One New Word Every Day", "Increase your vocabulary by learning a new word every day."),
            Tips("Don't Be Afraid to Do Wrong", "Mistakes are part of the learning process."),
            Tips("Practice with Friends", "Invite friends or family to study together."),
            Tips("Use SignLingo App Every Day!", "Consistency is key in learning sign language.")
        )
    }

    private fun getTestimonialsData(): List<Testimonial> {
        // Replace with your data source
        return listOf(
            Testimonial("Anna", "SignLingo has completely changed the way I learn sign language. It's very user-friendly and the scan feature is very helpful!", 5),
            Testimonial("Budi", "I find the dictionary feature in SignLingo very helpful. Now I can easily search and learn new signs every day.", 4),
            Testimonial("Farhan", "The scan feature in SignLingo is very innovative. I can figure out any sign just by pointing the camera at the hand shape. Very cool!", 5),
            Testimonial("Carla", "With SignLingo, I can learn sign language anywhere and anytime. The tips and tricks feature is also very useful to speed up my learning process.", 5),
            Testimonial("Gina", "Learning sign language has become easier with SignLingo. It has really helped me in understanding and remembering signs.", 4)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
