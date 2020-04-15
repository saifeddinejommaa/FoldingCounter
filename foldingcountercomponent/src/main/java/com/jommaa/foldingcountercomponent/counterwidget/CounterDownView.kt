package com.jommaa.counterwidget

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.ViewCompat
import com.jommaa.foldingcountercomponent.counterwidget.BaseCounterView
import com.jommaa.foldingcountercomponent.utils.CounterType
import kotlinx.android.synthetic.main.clock.view.*


class CounterDownView( context : Context, attrs : AttributeSet?) : BaseCounterView(context,CounterType.CounterDown, attrs) {

    //private var totalTime : Long = 0

    override fun setTime(startedTime: Long) {
        mPause = false
        this.startedTime = startedTime

        var time = this.startedTime
        val hourHeight = ( time / 36000).toInt()
        charHighHour!!.setChar(9-hourHeight)

        time -= hourHeight * 36000.toLong()

        val hourLow = ( time / 3600).toInt()
        charLowHour!!.setChar(9-hourLow)

        time -= hourLow * 3600.toLong()

        val minuteHeight = ( time / 600).toInt()
        charHighMinute!!.setChar(5 - minuteHeight)

        time-= minuteHeight * 600.toLong()

        val minuteLow = ( time / 60).toInt()
        charLowMinute!!.setChar(9 - minuteLow)

        time -= minuteLow * 60.toLong()

        val secHeight = ( time / 10).toInt()
        charHighSecond!!.setChar(5 - secHeight)

        time -= secHeight * 10.toLong()

        val secLow =  time.toInt()
        charLowSecond!!.setChar(9 - secLow)
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    fun resume(startedTime : Long) {
        setTime(startedTime)
    }

    constructor(context :Context): this(context,null)

    override fun run() {
        if(mPause){
            return;
        }
        charLowSecond.start()
        if (startedTime % 10 == 0L) {
            charHighSecond.start()
        }
        if (startedTime % 60 == 0L) {
            charLowMinute.start()
        }
        if (startedTime % 600 == 0L) {
            charHighMinute.start()
        }
        if (startedTime % 3600 == 0L) {
           charLowHour.start()
        }
        if (startedTime % 36000 == 0L) {
           charHighHour.start()
        }
         startedTime-=1

        if(startedTime.toInt()!=0) {
            ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
        }

        }
    }
