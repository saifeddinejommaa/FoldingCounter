package com.jommaa.foldingcountercomponent.counterwidget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.jommaa.foldingcountercomponent.R
import kotlinx.android.synthetic.main.clock.view.*

abstract class BaseCounterView (context: Context,
attrs: AttributeSet)  : LinearLayout(context,attrs), Runnable {

    protected var totalTime = 10 * 60 * 60.toLong()
    protected val startedTime = System.currentTimeMillis()
    protected var mPause = true

    protected var elapsedTime: Long = 0

     var isRunning = false
        protected set
    protected val mClock = this

    init {
        inflate(context, R.layout.clock, this)
        orientation = HORIZONTAL
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BaseCounterView)
        val textSize = attributes.getDimensionPixelSize(R.styleable.BaseCounterView_digitTextSize,60)
        val padding = attributes.getDimensionPixelSize(R.styleable.BaseCounterView_digitPadding, -1)

        val cornerSize = attributes.getDimensionPixelSize(R.styleable.BaseCounterView_digitCornerSize, -1)
        val textColor = attributes.getColor(R.styleable.BaseCounterView_digitTextColor,1)
        val backgroundColor = attributes.getColor(R.styleable.BaseCounterView_digitBackgroundColor,1)
        val dividerColor = attributes.getColor(R.styleable.BaseCounterView_digitDeviderColor,1)
        val springColor = attributes.getColor(R.styleable.BaseCounterView_digitspringColor,1)
        val withtext: Boolean = attributes.getBoolean(R.styleable.BaseCounterView_withtext,true)

        // High Second
        charHighSecond.textSize = textSize.toFloat()
        charHighSecond.fontColor = backgroundColor
        charHighSecond.textColor = textColor
        charHighSecond.paddingWidth = padding
        charHighSecond.cornerSize = cornerSize
        charHighSecond.paddingHeight= padding
        charHighSecond.dividerColor = dividerColor
        charHighSecond.springColor =  springColor
        charHighSecond.chars = BaseCounterView.SEXAGISIMAL

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
        charHighMinute.chars = BaseCounterView.SEXAGISIMAL
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

        if(!withtext){
            HourText.visibility = View.GONE
            MinutesText.visibility = View.GONE
            SecondText.visibility = View.GONE
        }

        attributes.recycle()

    }

    abstract fun setTime(shouldPause: Boolean, shouldRun: Boolean)



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

    companion object {

        private val HOURS = charArrayOf('0', '1', '2')

        private val SEXAGISIMAL = charArrayOf('0', '1', '2', '3', '4', '5')
    }

}