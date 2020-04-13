package com.jommaa.counterwidget

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.ViewCompat
import com.jommaa.foldingcountercomponent.counterwidget.BaseCounterView
import kotlinx.android.synthetic.main.clock.view.*


class CounterDownView( context : Context, attrs : AttributeSet) : BaseCounterView(context, attrs) {

    override fun setTime(shouldPause: Boolean, shouldRun: Boolean) {
        mPause = false

        val now = System.currentTimeMillis()
        elapsedTime = (now - startedTime) / 1000
        totalTime -= elapsedTime

        var time = totalTime
        val hourHeight = (time / 36000).toInt()
        charHighHour!!.setChar(9 - hourHeight)

        time -= hourHeight * 36000.toLong()

        val hourLow = (time / 3600).toInt()
        charLowHour!!.setChar(9 - hourLow)

        time -= hourLow * 3600.toLong()

        val minuteHeight = (time / 600).toInt()
        charHighMinute!!.setChar(5 - minuteHeight)

        time -= minuteHeight * 600.toLong()

        val minuteLow = (time / 60).toInt()
        charLowMinute!!.setChar(9 - minuteLow)

        time -= minuteLow * 60.toLong()

        val secHeight = (time / 10).toInt()
        charHighSecond!!.setChar(5 - secHeight)

        time -= secHeight * 10.toLong()

        val secLow = time.toInt()
        charLowSecond!!.setChar(9 - secLow)

        elapsedTime = 0
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    fun resume(shouldPause: Boolean, shouldRun: Boolean) {
        setTime(shouldPause, shouldRun)
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    override fun run() {
        if(mPause){
            return;
        }
        charLowSecond.start();
        if (elapsedTime % 10 == 0L) {
            charHighSecond.start();
        }
        if (elapsedTime % 60 == 0L) {
            charLowMinute.start();
        }
        if (elapsedTime % 600 == 0L) {
            charHighMinute.start();
        }
        if (elapsedTime % 3600 == 0L) {
           charLowHour.start();
        }
        if (elapsedTime % 36000 == 0L) {
           charHighHour.start();
        }
        elapsedTime += 1;
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }
}