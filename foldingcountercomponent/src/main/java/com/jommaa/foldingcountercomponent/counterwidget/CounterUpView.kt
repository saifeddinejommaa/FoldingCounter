package com.jommaa.counterwidget


import android.content.Context
import android.util.AttributeSet
import androidx.core.view.ViewCompat
import com.jommaa.foldingcountercomponent.counterwidget.BaseCounterView
import com.jommaa.foldingcountercomponent.utils.CounterType
import kotlinx.android.synthetic.main.clock.view.*


/**
 * Created by JOMMAA on 05/11/2019.
 */

class CounterUpView(
        context: Context,
        attrs: AttributeSet)  : BaseCounterView(context,CounterType.CounterUP, attrs) {

    override fun  setTime(startedTime : Long) {
        mPause=false
        this.startedTime = startedTime;
        var time = this.startedTime
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
        /*
        startedTime = (secLow + secHeight * 10
                + minuteLow * 60 + minuteHeight * 600
                + hourMin * 3600 + hourHeight * 36000).toLong()
                */
    }

    fun resume(startedTime: Long) {
        setTime(startedTime)
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    override fun run() {
        if (mPause) {
            return
        }
        startedTime += 1
       charLowSecond!!.start()
        if (startedTime % 10 == 0L) {
            charHighSecond!!.start()
        }
        if (startedTime % 60 == 0L) {
            charLowMinute!!.start()
        }
        if (startedTime % 600 == 0L) {
           charHighMinute!!.start()
        }
        if (startedTime % 3600 == 0L) {
            charLowHour!!.start()
        }
        if (startedTime % 36000 == 0L) {
            charHighHour!!.start()
        }

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }


}
