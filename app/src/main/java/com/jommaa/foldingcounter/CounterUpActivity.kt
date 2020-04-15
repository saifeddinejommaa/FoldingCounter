package com.jommaa.foldingcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_counter_up.*

class CounterUpActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_up)

    }

    override fun onResume() {
        super.onResume()
        counterUpView.resume(30)
    }
}