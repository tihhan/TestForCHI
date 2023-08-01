package com.example.testforchi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class UserDetailsFragment : Fragment() {



    companion object {
        private const val ARG_USER = "user"

        fun newInstance(user: User): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)



        val user = arguments?.getParcelable<User>(ARG_USER)

        user?.let {
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            val ageTextView: TextView = view.findViewById(R.id.ageTextView)
            val isStudentTextView: TextView = view.findViewById(R.id.isStudentTextView)
            val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)

            nameTextView.text = it.name
            ageTextView.text = it.age.toString()
            descriptionTextView.text = it.description
            isStudentTextView.text = if (it.isStudent) "Student" else "Not a student"
        }








        return view
    }



//DOESN'T WORK
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Detail info"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

   //requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
   //    override fun handleOnBackPressed() {
   //        findNavController().navigate(R.id.action_UserDetailsFragment_to_MainScreenFragment)
   //    }
   //})



}

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
