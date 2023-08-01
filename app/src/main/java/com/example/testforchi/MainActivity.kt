package com.example.testforchi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var textViewCounter: TextView
    private lateinit var buttonIncrement: Button
    private var counter = 0

    companion object {
        private const val REQUEST_CODE_COUNTER_FRAGMENT = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCounter = findViewById(R.id.tvCounterActivity)
        buttonIncrement = findViewById(R.id.btnIncrement)

        buttonIncrement.setOnClickListener {
            counter++
            textViewCounter.text = counter.toString()
        }

        buttonIncrement.setOnClickListener {
            val intent = Intent(this, FragmentHostActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_COUNTER_FRAGMENT)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_COUNTER_FRAGMENT && resultCode == Activity.RESULT_OK) {
            val counterFromFragment = data?.getIntExtra("counter", 0) ?: 0
            counter = counterFromFragment
            textViewCounter.text = counter.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("counter", 0)
        textViewCounter.text = counter.toString()
    }



    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is CounterFragment) {
            val fragmentCounter = fragment as CounterFragment
            textViewCounter.text = fragmentCounter.getCounter().toString()
        }
        super.onBackPressed()
    }

}