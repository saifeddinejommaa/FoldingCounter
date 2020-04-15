package com.jommaa.foldingcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_counter_down.*

class CounterDownActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_down)
    }

    override fun onResume() {
        super.onResume()
        counterDownView.resume(70)
    }
}