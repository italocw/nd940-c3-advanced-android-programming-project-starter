package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private val loadingRect = Rect()
    private var progress = 0
    private val fullRect = RectF()
    private val rectOfArc = RectF()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 44.0f
        // typeface = Typeface.create("", Typeface.BOLD)
    }

    private var valueAnimator = ValueAnimator()

    public var state: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

        if (new != ButtonState.Completed) {
            valueAnimator = ValueAnimator.ofInt(0, 360).setDuration(5000).apply {
                // Add an update listener to update [progress] value.
                addUpdateListener {
                    // Update the current progress to use it [onDraw].
                    progress = it.animatedValue as Int
                    // Redraw the layout to use the new updated value of [progress].
                    invalidate()

                    if (!progressIsNotCompleted() && new == ButtonState.Loading) {
                        state = ButtonState.Completed
                    }
                }

                // Repeat the animation infinitely.
                if (new == ButtonState.Clicked) {
                    repeatCount = ValueAnimator.INFINITE
                }
                repeatMode = ValueAnimator.RESTART
                // Start the animation.
                start()
            }

        }
        if (new == ButtonState.Completed) {
            valueAnimator.currentPlayTime
            valueAnimator.cancel()
            invalidate()

        }

    }


    init {
        isClickable = true
    }

    private var widthSize = 0
    private var heightSize = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = ((Integer.min(width, height)) / 2.0 * 0.8).toFloat()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        loadingBackground(canvas)

        if (state == ButtonState.Loading) {
            drawLoadingInnerBar(canvas)
            drawLoadingCircle(canvas)
        }
        drawLabel(canvas)
        //@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint
    }

    private fun progressIsNotCompleted(): Boolean = progress < 360

    private fun loadingBackground(canvas: Canvas) {
        paint.color = context.getColor(R.color.colorPrimary)
        fullRect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRect(fullRect, paint)
    }

    private fun drawLoadingInnerBar(canvas: Canvas) {
        loadingRect.set(0, 0, width * progress / 360, height)
        paint.color = context.getColor(R.color.colorPrimaryDark)
        canvas.drawRect(loadingRect, paint)
    }

    private fun drawLoadingCircle(canvas: Canvas) {
        paint.color = context.getColor(R.color.colorAccent)
        rectOfArc.set(
            width - height.toFloat() / 1.25f,
            height.toFloat() / 4f,
            width - height.toFloat() / 4f,
            height.toFloat() / 1.25f
        )
        canvas.drawArc(rectOfArc, 270f, progress.toFloat(), true, paint)
    }

    private fun drawLabel(canvas: Canvas) {

        paint.color = context.getColor(R.color.white)

        val label = if (state == ButtonState.Loading) {
            resources.getString(R.string.we_are_loading)
        } else {
            resources.getString(R.string.download)
        }
        canvas.drawText(label, width.toFloat() / 2, (height.toFloat() / 2f) + 44 / 3, paint)


    }

}
