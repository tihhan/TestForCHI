package com.example.testforchi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

@Suppress("DEPRECATION")
class FragmentHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_host)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, CounterFragment())
            fragmentTransaction.commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is CounterFragment) {
            // Get the counter value from the CounterFragment
            val fragmentCounter = fragment.getCounter()

            // Set the result to pass back to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("counter", fragmentCounter)
            setResult(Activity.RESULT_OK, resultIntent)
        }

        onBackPressed()
        return true
    }
}
