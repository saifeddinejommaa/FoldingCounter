package com.jommaa.counterwidget


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.jommaa.foldingcountercomponent.R
import com.jommaa.foldingcountercomponent.counterwidget.BaseCounterView
import kotlinx.android.synthetic.main.clock.view.*


/**
 * Created by JOMMAA on 05/11/2019.
 */

class CounterView(
        context: Context,
        attrs: AttributeSet)  : BaseCounterView(context,attrs) {

    fun setTime(shouldPause: Boolean, shouldRun: Boolean) {
        mPause = shouldPause
        isRunning = shouldRun
        var time = elapsedTime
        val hour = time.toInt() / 3600
        val hourHeight = hour / 10
        val hourMin = hour - hourHeight * 10
        charHighHour!!.setChar(hourHeight)
        charLowHour!!.setChar(hourMin)
        time -= (hour * 3600).toLong()

        val minute = (time / 60).toInt()
        val minuteHeight = minute / 10
        val minuteLow = minute - minuteHeight * 10
        charHighMinute!!.setChar(minuteHeight)
        charLowMinute!!.setChar(minuteLow)
        time -= (minute * 60).toLong()

        val secHeight = (time / 10).toInt()
        charHighSecond!!.setChar(secHeight)

        time -= (secHeight * 10).toLong()

        val secLow = time.toInt()
        charLowSecond!!.setChar(secLow)

        elapsedTime = (secLow + secHeight * 10
                + minuteLow * 60 + minuteHeight * 600
                + hourMin * 3600 + hourHeight * 36000).toLong()
    }

    fun resume(shouldPause: Boolean, shouldRun: Boolean) {
        setTime(shouldPause, shouldRun)
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    override fun run() {
        if (mPause) {
            return
        }
        elapsedTime += 1
       charLowSecond!!.start()
        if (elapsedTime % 10 == 0L) {
            charHighSecond!!.start()
        }
        if (elapsedTime % 60 == 0L) {
            charLowMinute!!.start()
        }
        if (elapsedTime % 600 == 0L) {
           charHighMinute!!.start()
        }
        if (elapsedTime % 3600 == 0L) {
            charLowHour!!.start()
        }
        if (elapsedTime % 36000 == 0L) {
            charHighHour!!.start()
        }

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }


}
