package com.jommaa.counterwidget


import android.content.Context
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jommaa.foldingcountercomponent.R
import kotlinx.android.synthetic.main.clock.view.*

/**
 * Created by JOMMAA on 05/11/2019.
 */

class CounterView(
        context: Context,
        attrs: AttributeSet)  : LinearLayout(context,attrs), Runnable {

    private val mClock = this

    private var mPause = true

    var elapsedTime: Long = 0

    var isRunning = false
        private set

    init {
        inflate(context,R.layout.clock, this)
        orientation = HORIZONTAL
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.CounterView)
            val textSize = attributes.getDimensionPixelSize(R.styleable.CounterView_digitTextSize,60)
            val padding = attributes.getDimensionPixelSize(R.styleable.CounterView_digitPadding, -1)

            val cornerSize = attributes.getDimensionPixelSize(R.styleable.CounterView_digitCornerSize, -1)
            val textColor = attributes.getColor(R.styleable.CounterView_digitTextColor,1)
            val backgroundColor = attributes.getColor(R.styleable.CounterView_digitBackgroundColor,1)
            val dividerColor = attributes.getColor(R.styleable.CounterView_digitDeviderColor,1)
            val springColor = attributes.getColor(R.styleable.CounterView_digitspringColor,1)

            // High Second
            charHighSecond.textSize = textSize.toFloat()
            charHighSecond.fontColor = backgroundColor
            charHighSecond.textColor = textColor
            charHighSecond.paddingWidth = padding
            charHighSecond.cornerSize = cornerSize
            charHighSecond.paddingHeight= padding
            charHighSecond.dividerColor = dividerColor
            charHighSecond.springColor =  springColor
            charHighSecond.chars = SEXAGISIMAL

            //Low Second
            charLowSecond.textSize = textSize.toFloat()
            charLowSecond.fontColor = backgroundColor
            charLowSecond.textColor = textColor
            charLowSecond.paddingWidth = padding
            charLowSecond.cornerSize = cornerSize
            charLowSecond.paddingHeight= padding
            charLowSecond.dividerColor = dividerColor
            charLowSecond.springColor =  springColor

            //Hight Minutes
            charHighMinute.textSize = textSize.toFloat()
            charHighMinute.fontColor = backgroundColor
            charHighMinute.textColor = textColor
            charHighMinute.paddingWidth = padding
            charHighMinute.cornerSize = cornerSize
            charHighMinute.paddingHeight= padding
            charHighMinute.dividerColor = dividerColor
            charHighMinute.chars = SEXAGISIMAL
            charHighMinute.springColor =  springColor

            //Low Minutes
            charLowMinute.textSize = textSize.toFloat()
            charLowMinute.fontColor = backgroundColor
            charLowMinute.textColor = textColor
            charLowMinute.paddingWidth = padding
            charLowMinute.cornerSize = cornerSize
            charLowMinute.paddingHeight= padding
            charLowMinute.dividerColor = dividerColor
            charLowMinute.springColor =  springColor


        //High Hour
            charHighHour.textSize = textSize.toFloat()
            charHighHour.fontColor = backgroundColor
            charHighHour.textColor = textColor
            charHighHour.paddingWidth = padding
            charHighHour.cornerSize = cornerSize
            charHighHour.paddingHeight= padding
            charHighHour.dividerColor = dividerColor
            charHighHour.springColor =  springColor


        //Low Hour
            charLowHour.textSize = textSize.toFloat()
            charLowHour.fontColor = backgroundColor
            charLowHour.textColor = textColor
            charLowHour.paddingWidth = padding
            charLowHour.cornerSize = cornerSize
            charLowHour.paddingHeight= padding
            charLowHour.dividerColor = dividerColor
            charLowHour.springColor =  springColor

            attributes.recycle()

    }



    fun pause() {
        mPause = true
        charHighSecond!!.sync()
        charLowSecond!!.sync()
        charHighMinute!!.sync()
        charLowMinute!!.sync()
        charHighHour!!.sync()
        charLowHour!!.sync()
        isRunning = false
    }

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

    companion object {

        private val HOURS = charArrayOf('0', '1', '2')

        private val SEXAGISIMAL = charArrayOf('0', '1', '2', '3', '4', '5')
    }
}
