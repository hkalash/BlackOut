package com.smartsoftwaresolutions.blackout

import android.animation.*
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.core.content.ContextCompat

class SwipeButton : RelativeLayout {
    private var slidingButton: ImageView? = null
    private var initialX = 0f
    private var active = false
    private var initialButtonWidth = 0
    private var centerText: TextView? = null
    var go_black: Go_Black? = null
    private var disabledDrawable: Drawable? = null
    private var enabledDrawable: Drawable? = null

    constructor(context: Context?) : super(context) {
        init(context, null, -1, -1)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, -1, -1)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, -1)
    }

    @TargetApi(21)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        go_black = Go_Black(context)
        val background = RelativeLayout(context)
        val layoutParamsView = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background.background = ContextCompat.getDrawable(context!!, R.drawable.buttonshape)
        }
        addView(background, layoutParamsView)
        //    inal TextView centerText = new TextView(context);
        val centerText = TextView(context)
        this.centerText = centerText
        val layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(CENTER_IN_PARENT, TRUE)
        centerText.text = "SWIPE To Start" //add any text you need
        centerText.setTextColor(Color.WHITE)
        centerText.setPadding(35, 35, 35, 35)
        background.addView(centerText, layoutParams)
        val swipeButton = ImageView(context)
        slidingButton = swipeButton
        disabledDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_start)
        enabledDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_stop)
        slidingButton!!.setImageDrawable(disabledDrawable)
        slidingButton!!.setPadding(40, 40, 40, 40)
        val layoutParamsButton = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParamsButton.addRule(ALIGN_PARENT_LEFT, TRUE)
        layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            swipeButton.background = ContextCompat.getDrawable(context!!, R.drawable.shape_button)
        }
        swipeButton.setImageDrawable(disabledDrawable)
        addView(swipeButton, layoutParamsButton)
        setOnTouchListener(getButtonTouchListener())
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun getButtonTouchListener(): OnTouchListener? {
        return OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->                             //  Toast.makeText(this,"helow from action down",Toast.LENGTH_LONG).show();
                    //   Toast.makeText(getContext(),"in the start",Toast.LENGTH_LONG).show();
                    return@OnTouchListener true
                MotionEvent.ACTION_MOVE -> {
                    //Movement logic here
                    if (initialX == 0f) {
                        initialX = slidingButton!!.getX()
                    }
                    if (event.x > initialX + slidingButton!!.getWidth() / 2 &&
                            event.x + slidingButton!!.getWidth() / 2 < width) {
                        slidingButton!!.setX(event.x - slidingButton!!.getWidth() / 2)
                        centerText!!.setAlpha(1 - 1.3f * (slidingButton!!.getX() + slidingButton!!.getWidth()) / width)
                    }
                    if (event.x + slidingButton!!.getWidth() / 2 > width &&
                            slidingButton!!.getX() + slidingButton!!.getWidth() / 2 < width) {
                        slidingButton!!.setX((width - slidingButton!!.getWidth()).toFloat())
                    }
                    if (event.x < slidingButton!!.getWidth() / 2 &&
                            slidingButton!!.getX() > 0) {
                        slidingButton!!.setX(0f)
                    }
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    //Release logic here
                    //  Toast.makeText(getContext(),"in the release",Toast.LENGTH_LONG).show();
                    if (active) {
                        collapseButton()
                    } else {
                        initialButtonWidth = slidingButton!!.getWidth()
                        if (slidingButton!!.getX() + slidingButton!!.getWidth() > width * 0.85) {
                            expandButton()
                        } else {
                            moveButtonBack()
                        }
                    }
                    return@OnTouchListener true
                }
            }
            false
        }
    }

    private fun expandButton() {
        val go_black1 = Go_Black(context)

        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.getX(), 0f)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton!!.setX(x)
        }
        val widthAnimator = ValueAnimator.ofInt(
                slidingButton!!.getWidth(),
                width)
        widthAnimator.addUpdateListener {
            val params = slidingButton!!.getLayoutParams()
            params.width = widthAnimator.animatedValue as Int
            slidingButton!!.setLayoutParams(params)
        }
        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                active = true
                slidingButton!!.setImageDrawable(enabledDrawable)
            }
        })
        animatorSet.playTogether(positionAnimator, widthAnimator)
        animatorSet.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun collapseButton() {
        go_black!!.Timer!!.cancel()
        val widthAnimator = ValueAnimator.ofInt(
                slidingButton!!.getWidth(),
                initialButtonWidth)
        widthAnimator.addUpdateListener {
            val params = slidingButton!!.getLayoutParams()
            params.width = widthAnimator.animatedValue as Int
            slidingButton!!.setLayoutParams(params)
        }
        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                active = false
                slidingButton!!.setImageDrawable(disabledDrawable)
            }
        })
        val objectAnimator = ObjectAnimator.ofFloat(
                centerText, "alpha", 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, widthAnimator)
        animatorSet.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.getX(), 0f)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton!!.setX(x)
        }
        val objectAnimator = ObjectAnimator.ofFloat(
                centerText, "alpha", 1f)
        positionAnimator.duration = 200
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.start()
    }
}