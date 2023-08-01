package com.example.testforchi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION")
class CounterFragment : Fragment() {
    private lateinit var textViewCounter: TextView
    private lateinit var buttonIncrement: Button
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewCounter = view.findViewById(R.id.tvCounterFragment)
        buttonIncrement = view.findViewById(R.id.btnIncrementFragment)

        buttonIncrement.setOnClickListener {
            counter++
            textViewCounter.text = counter.toString()
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter", 0)
            textViewCounter.text = counter.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val intent = Intent()
        intent.putExtra("counter", counter)
        requireActivity().setResult(Activity.RESULT_OK, intent)
    }

    fun getCounter(): Int {
        return counter
    }
}
