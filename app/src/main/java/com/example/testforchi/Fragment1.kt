package com.example.testforchi

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Fragment1 : Fragment() {

    enum class SortingOption {
        NAME,
        AGE,
        STUDENT_STATUS,
        DESCRIPTION
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val userList = mutableListOf<User>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment1, container, false)

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userList.addAll(retrieveUserList())

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserAdapter(requireContext(), userList, { user ->
            showUserDetailsFragment(user)
        },
            { user -> showUserDetailsFragment(user) },
            { user -> showDeleteUserDialog(user) })

        recyclerView.adapter = adapter

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment1, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_by_name -> {
                sortAndRefreshList(SortingOption.NAME)
                return true
            }
            R.id.menu_sort_by_age -> {
                sortAndRefreshList(SortingOption.AGE)
                return true
            }
            R.id.menu_sort_by_student_status -> {
                sortAndRefreshList(SortingOption.STUDENT_STATUS)
                return true
            }
            R.id.menu_sort_by_description -> {
                sortAndRefreshList(SortingOption.DESCRIPTION)
                return true
            }
            android.R.id.home -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showUserDetailsFragment(user: User) {
        val fragment = UserDetailsFragment.newInstance(user)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun saveUserList(users: List<User>) {
        val json = Gson().toJson(users)
        val editor = sharedPreferences.edit()
        editor.putString("user_list", json)
        editor.apply()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteUser(user: User) {
        userList.remove(user)
        saveUserList(userList)
        adapter.notifyDataSetChanged()
    }

    private fun showDeleteUserDialog(user: User) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("You want to delete this User")
        alertDialogBuilder.setMessage("Are you sure you want to delete this user?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            deleteUser(user)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshUserList() {
        userList.clear()
        userList.addAll(retrieveUserList())
        adapter.notifyDataSetChanged()
        showUserAddedSuccessfullyMessage()
    }

    private fun retrieveUserList(): List<User> {
        val json = sharedPreferences.getString("user_list", null)
        return if (json != null) {
            val typeToken = object : TypeToken<List<User>>() {}.type
            Gson().fromJson(json, typeToken)
        } else {
            emptyList()
        }
    }

    private fun showUserAddedSuccessfullyMessage() {
        Snackbar.make(
            requireView(),
            "User added successfully",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortAndRefreshList(sortingOption: SortingOption) {
        when (sortingOption) {
            SortingOption.NAME -> {
                userList.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
            }
            SortingOption.AGE -> userList.sortBy { it.age }
            SortingOption.STUDENT_STATUS -> {
                userList.sortedWith(compareBy { if (!it.isStudent) 1 else 0 })
            }
            SortingOption.DESCRIPTION -> {
                userList.sortBy { it.description.length }
            }
        }

        adapter.notifyDataSetChanged()
    }

}



