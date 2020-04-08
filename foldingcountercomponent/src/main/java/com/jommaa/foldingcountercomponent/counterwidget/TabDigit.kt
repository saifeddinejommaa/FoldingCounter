package com.example.numericcounterlibrary.counterwidget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.example.jommaa.customnumericcounter.R
import com.example.numericcounterlibrary.utils.MatrixHelper

import java.util.ArrayList

/**
 * Created by JOMMAA on 10/04/2019.
 */
class TabDigit @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0): View(context, attrs, defStyleAttr), Runnable {

    private var state = LOWER_POSITION

     var mTime: Long = -1
     var mElapsedTime = 1000.0f

   lateinit  var mTopTab: Tab

    lateinit var mBottomTab: Tab

    lateinit var mMiddleTab: Tab

    private val tabs = ArrayList<Tab>(3)

    private val mProjectionMatrix = Matrix()

    private var mAlpha = 0

    private var mCornerSize: Int = 0

    lateinit var mNumberPaint: Paint

    lateinit var mDividerPaint: Paint

    lateinit var mBackgroundPaint: Paint

    lateinit var mSpringPaint: Paint

    private val mTextMeasured = Rect()

    private var mPaddingWidth = 0

    private var mPaddingHeight = 0


    /**
     * Sets chars that are going to be displayed.
     * Note: **That only one digit is allow per character.**
     *
     * @param chars
     */
    var chars = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    var textSize: Float
        get() = mNumberPaint.textSize
        set(size) {
            mNumberPaint.textSize = size.toFloat()
            requestLayout()
        }

    var paddingHeight: Int
        get() = mPaddingHeight
        set(padding) {
            mPaddingHeight = padding
            requestLayout()
        }

    var paddingWidth: Int
        get() = mPaddingWidth
        set(padding) {
            mPaddingWidth = padding
            requestLayout()
        }

    var textColor: Int
        get() = mNumberPaint.color
        set(color) {
            mNumberPaint.color = color
        }

    var cornerSize: Int
        get() = mCornerSize
        set(cornerSize) {
            mCornerSize = cornerSize
            invalidate()
        }

    var fontColor: Int
        get() = mBackgroundPaint.color
        set(color){
            mBackgroundPaint.color = color
            requestLayout()
        }

    var dividerColor: Int
        get() = mDividerPaint.color
        set(color){
            mDividerPaint.color = color
            requestLayout()
        }

    var springColor : Int
     get() = mSpringPaint.color
    set(color){
        mSpringPaint.color = color
        requestLayout()
    }

    init {
        initPaints()
        initTabs()
    }

    private fun initPaints() {

        mNumberPaint = Paint()
        mNumberPaint.isAntiAlias = true
        mNumberPaint.style = Paint.Style.FILL_AND_STROKE
        mNumberPaint.strokeWidth = 7f

        mDividerPaint = Paint()
        mDividerPaint.isAntiAlias = true
        mDividerPaint.style = Paint.Style.FILL_AND_STROKE
        mDividerPaint.strokeWidth = 1f

        mBackgroundPaint = Paint()
        mBackgroundPaint.isAntiAlias = true

        mSpringPaint = Paint()
        mSpringPaint.isAntiAlias = true
        mSpringPaint.color = Color.RED
        mSpringPaint.style = Paint.Style.FILL_AND_STROKE
        mSpringPaint.strokeWidth = 11f
    }

    private fun initTabs() {
        // top Tab
        mTopTab = Tab()
        mTopTab.rotate(180)
        tabs.add(mTopTab)

        // bottom Tab
        mBottomTab = Tab()
        tabs.add(mBottomTab)

        // middle Tab
        mMiddleTab = Tab()
        tabs.add(mMiddleTab)

        setInternalChar(0)
    }

    fun setChar(index: Int) {
        setInternalChar(index)
        invalidate()
    }

    private fun setInternalChar(index: Int) {
        for (tab in tabs) {
            tab.setChar(index)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        calculateTextSize(mTextMeasured)

        val childWidth = mTextMeasured.width() + mPaddingWidth
        val childHeight = mTextMeasured.height() + mPaddingHeight
        measureTabs(childWidth, childHeight)

        val maxChildWidth = mMiddleTab.maxWith()
        val maxChildHeight = 2 * mMiddleTab.maxHeight()
        val resolvedWidth = View.resolveSize(maxChildWidth, widthMeasureSpec)
        val resolvedHeight = View.resolveSize(maxChildHeight, heightMeasureSpec)

        setMeasuredDimension(resolvedWidth, resolvedHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            setupProjectionMatrix()
        }
    }

    private fun setupProjectionMatrix() {
        mProjectionMatrix.reset()
        val centerY:Float =  (height / 2).toFloat()
        val centerX:Float = (width / 2).toFloat()
        MatrixHelper.translate(mProjectionMatrix, centerX, -centerY, 0.toFloat())
    }

    private fun measureTabs(width: Int, height: Int) {
        for (tab in tabs) {
            tab.measure(width, height)
        }
    }

    private fun drawTabs(canvas: Canvas) {
        for (tab in tabs) {
            tab.draw(canvas)
        }
    }

    private fun drawDivider(canvas: Canvas) {
        canvas.save()
        canvas.concat(mProjectionMatrix)
        canvas.drawLine(tabs[0].mStartBounds.left, 0f, tabs[0].mStartBounds.left + tabs[0].mStartBounds.width(), 0f, mDividerPaint)
        canvas.restore()
    }

    private fun calculateTextSize(rect: Rect) {
        mNumberPaint.getTextBounds("8", 0, 1, rect)
    }

    fun start() {
        makeSureCycleIsClosed()
        mTime = System.currentTimeMillis()
        invalidate()
    }

    fun elapsedTime(elapsedTime: Float) {
        mElapsedTime = elapsedTime
    }

    override fun onDraw(canvas: Canvas) {
        drawTabs(canvas)
        drawDivider(canvas)

        ViewCompat.postOnAnimationDelayed(this, this, 30)
    }

    override fun run() {
        if (mTime == -1L) {
            return
        }
        when (state) {
            LOWER_POSITION -> {
                nextBottomTab()
            }
            MIDDLE_POSITION -> {
                if (mAlpha > 90) {
                    nextMiddleTab()
                }
            }
            UPPER_POSITION -> {
                if (mAlpha >= 180) {
                    nextTopTab()
                }
            }
        }

        if (mTime!=-1L) {
            val delta = System.currentTimeMillis() - mTime
            mAlpha = (180 * (1 - (1 * mElapsedTime - delta) / (1 * mElapsedTime))).toInt()
            mMiddleTab.rotate(mAlpha)
        }
        invalidate()
    }

    private fun nextBottomTab() {
        mBottomTab.next()
        state = MIDDLE_POSITION
    }

    private fun nextMiddleTab() {
        mMiddleTab.next()
        state = UPPER_POSITION
    }

    private fun nextTopTab() {
        mTopTab.next()
        mTime = -1L
        state = LOWER_POSITION
    }

    fun sync() {
        makeSureCycleIsClosed()
        invalidate()
        postInvalidate()
    }

    private fun makeSureCycleIsClosed() {
        if (mTime==-1L) {
            return
        }
        when (state) {
            LOWER_POSITION -> {
                nextMiddleTab()
            }
            UPPER_POSITION -> {
                nextTopTab()
            }
            MIDDLE_POSITION -> {
                nextTopTab()
            }
        }
        mMiddleTab.rotate(180)
    }

    inner class Tab {

         val mModelViewMatrix = Matrix()

         val mModelViewProjectionMatrix = Matrix()

         val mRotationModelViewMatrix = Matrix()

         val mStartBounds = RectF()

         val mEndBounds = RectF()

         var mCurrIndex = 0

         var mAlpha: Int = 0

         val mMeasuredMatrixHeight = Matrix()

         val mMeasuredMatrixWidth = Matrix()


        fun measure(width: Int, height: Int) {
            val area = Rect(-width / 2, 0, width / 2, height / 2)
            mStartBounds.set(area)
            mEndBounds.set(area)
            mEndBounds.offset(0f, (-height / 2).toFloat())
        }

        fun maxWith(): Int {
            val rect = RectF(mStartBounds)
            val projectionMatrix = Matrix()
            MatrixHelper.translate(projectionMatrix, mStartBounds.left, -mStartBounds.top, 0.toFloat())
            mMeasuredMatrixWidth.reset()
            mMeasuredMatrixWidth.setConcat(projectionMatrix, MatrixHelper.ROTATE_X_90)
            mMeasuredMatrixWidth.mapRect(rect)
            return rect.width().toInt() - resources.getDimension(R.dimen.corner_small_radius).toInt()
        }

        fun maxHeight(): Int {
            val rect = RectF(mStartBounds)
            val projectionMatrix = Matrix()
            mMeasuredMatrixHeight.reset()
            mMeasuredMatrixHeight.setConcat(projectionMatrix, MatrixHelper.ROTATE_X_0)
            mMeasuredMatrixHeight.mapRect(rect)
            return rect.height().toInt()
        }

        fun setChar(index: Int) {
            mCurrIndex = if (index < 0 || index > chars.size) 0 else index
        }

        operator fun next() {
            mCurrIndex++
            if (mCurrIndex >= chars.size) {
                mCurrIndex = 0
            }
        }

        fun rotate(alpha: Int) {
            mAlpha = alpha
            MatrixHelper.rotateX(mRotationModelViewMatrix, alpha)
        }

        fun draw(canvas: Canvas) {
            drawBackground(canvas)
            drawText(canvas)
            //  drawBorders(canvas);
        }

        private fun drawBackground(canvas: Canvas) {
            canvas.save()
            mModelViewMatrix.set(mRotationModelViewMatrix)
            applyTransformation(canvas, mModelViewMatrix)
            val path = getPath(20f, false, false, true, true)
            canvas.drawPath(path, mBackgroundPaint)
            canvas.drawLine(mStartBounds.left + resources.getDimension(R.dimen.corner_small_radius).toInt(), 0f, mStartBounds.left + resources.getDimension(R.dimen.corner_small_radius).toInt(), (-resources.getDimension(R.dimen.digit_borders_height).toInt()).toFloat(), mSpringPaint)
            canvas.drawLine(mStartBounds.left + resources.getDimension(R.dimen.corner_small_radius).toInt(), 0f, mStartBounds.left + resources.getDimension(R.dimen.corner_small_radius).toInt(), resources.getDimension(R.dimen.corner_small_radius).toInt().toFloat(), mSpringPaint)

            canvas.drawLine(mStartBounds.right - resources.getDimension(R.dimen.corner_small_radius).toInt(), 0f, mStartBounds.right - resources.getDimension(R.dimen.corner_small_radius).toInt(), (-resources.getDimension(R.dimen.digit_borders_height).toInt()).toFloat(), mSpringPaint)
            canvas.drawLine(mStartBounds.right - resources.getDimension(R.dimen.corner_small_radius).toInt(), 0f, mStartBounds.right - resources.getDimension(R.dimen.corner_small_radius).toInt(), resources.getDimension(R.dimen.corner_small_radius).toInt().toFloat(), mSpringPaint)

            canvas.restore()
        }

        private fun getPath(radius: Float, topLeft: Boolean, topRight: Boolean,
                            bottomRight: Boolean, bottomLeft: Boolean): Path {

            val path = Path()
            val radii = FloatArray(8)

            if (topLeft) {
                radii[0] = radius
                radii[1] = radius
            }

            if (topRight) {
                radii[2] = radius
                radii[3] = radius
            }

            if (bottomRight) {
                radii[4] = radius
                radii[5] = radius
            }

            if (bottomLeft) {
                radii[6] = radius
                radii[7] = radius
            }

            path.addRoundRect(mStartBounds,
                    radii, Path.Direction.CW)

            return path
        }

        private fun drawText(canvas: Canvas) {
            canvas.save()
            mModelViewMatrix.set(mRotationModelViewMatrix)
            var clip = mStartBounds
            if (mAlpha > 90) {
                mModelViewMatrix.setConcat(mModelViewMatrix, MatrixHelper.MIRROR_X)
                clip = mEndBounds
            }
            applyTransformation(canvas, mModelViewMatrix)
            canvas.clipRect(clip)
            canvas.drawText(Character.toString(chars[mCurrIndex]), 0, 1, (-mTextMeasured.centerX()).toFloat(), (-mTextMeasured.centerY()).toFloat(), mNumberPaint)
            canvas.restore()
        }

        private fun applyTransformation(canvas: Canvas, matrix: Matrix) {
            mModelViewProjectionMatrix.reset()
            mModelViewProjectionMatrix.setConcat(mProjectionMatrix, matrix)
            canvas.concat(mModelViewProjectionMatrix)
        }
    }

    companion object {

        private val LOWER_POSITION = 0
        private val MIDDLE_POSITION = 1
        private val UPPER_POSITION = 2
    }
}
