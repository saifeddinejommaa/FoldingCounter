package com.jommaa.foldingcounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CounterUp.setOnClickListener { startActivity(Intent(this, CounterUpActivity::class.java)) }
        CounterDown.setOnClickListener { startActivity(Intent(this, CounterDownActivity::class.java)) }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}
