package com.jommaa.foldingcounter

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_counter_up.*

class CounterUpActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_counter_up)

    }

    override fun onResume() {
        super.onResume()
        counterView.resume(false,true)
    }
}