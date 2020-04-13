package com.jommaa.foldingcounter

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.jommaa.counterwidget.CounterDownView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_counter_down.*

class CounterDownActivity : AppCompatActivity()  {

    lateinit var counterDownView: CounterDownView
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_counter_down)
        counterDownView = findViewById<CounterDownView>(R.id.counterDown)
    }

    override fun onResume() {
        super.onResume()
        counterDownView.resume(false,true)
    }
}