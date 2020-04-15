package com.jommaa.foldingcountercomponent.counterwidget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.jommaa.foldingcountercomponent.R
import com.jommaa.foldingcountercomponent.utils.CounterType
import kotlinx.android.synthetic.main.clock.view.*

abstract class BaseCounterView (context: Context,counterType: CounterType?,
attrs: AttributeSet?)  : LinearLayout(context,attrs), Runnable {


   // protected val startedTime = System.currentTimeMillis()
    protected var mPause = true
    protected var startedTime: Long = 0

     var isRunning = false
        protected set
    protected val mClock = this

    constructor(context: Context):this(context,null,null){
    }

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

        when(counterType){
            CounterType.CounterDown -> {
                charHighMinute.chars = COUNTER_DOWN_SEXAGISIMAL
                charHighHour.chars = COUNTER_DOWN_DECIMAL
                charLowHour.chars = COUNTER_DOWN_DECIMAL
                charHighSecond.chars = COUNTER_DOWN_SEXAGISIMAL
                charLowMinute.chars = COUNTER_DOWN_DECIMAL
                charLowSecond.chars = COUNTER_DOWN_DECIMAL
            }
            CounterType.CounterUP -> {
                charHighMinute.chars = COUNTER_UP_SEXAGISIMAL
                charHighHour.chars = COUNTER_UP_DECIMAL
                charLowHour.chars = COUNTER_UP_DECIMAL
                charHighSecond.chars = COUNTER_UP_SEXAGISIMAL
                charLowMinute.chars = COUNTER_UP_DECIMAL
                charLowSecond.chars = COUNTER_UP_DECIMAL
            }

        }

        attributes.recycle()

    }

    abstract fun setTime(startedTime :Long)

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

        private val COUNTER_UP_SEXAGISIMAL = charArrayOf('0', '1', '2', '3', '4', '5')
        private val COUNTER_DOWN_SEXAGISIMAL = charArrayOf('5', '4', '3', '2', '1', '0')

        private val COUNTER_UP_DECIMAL = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        private val COUNTER_DOWN_DECIMAL = charArrayOf('9', '8', '7', '6', '5', '4', '3', '2', '1', '0')

        private val High_HOUR = charArrayOf('0','1','2')
    }

}