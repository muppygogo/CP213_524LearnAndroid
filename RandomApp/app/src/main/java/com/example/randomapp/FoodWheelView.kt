package com.example.randomapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

class FoodWheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var items: List<String> = listOf("ไม่มีเมนู")
    private var isDarkMode: Boolean = false

    private var wheelColors: List<Int> = lightColors()

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val separatorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 4f
    }

    private val centerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFF5F8")
        style = Paint.Style.FILL
    }

    private val centerDotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#E8A8B8")
        style = Paint.Style.FILL
    }

    private val rectF = RectF()

    init {
        applyTheme(false)
    }

    fun setItems(newItems: List<String>) {
        items = if (newItems.isEmpty()) listOf("ไม่มีเมนู") else newItems
        invalidate()
    }

    fun setDarkMode(enabled: Boolean) {
        isDarkMode = enabled
        applyTheme(enabled)
        invalidate()
    }

    private fun applyTheme(darkMode: Boolean) {
        wheelColors = if (darkMode) darkColors() else lightColors()

        separatorPaint.color = if (darkMode) {
            Color.parseColor("#2B2530")
        } else {
            Color.WHITE
        }

        centerPaint.color = if (darkMode) {
            Color.parseColor("#3A3342")
        } else {
            Color.parseColor("#FFF5F8")
        }

        centerDotPaint.color = if (darkMode) {
            Color.parseColor("#C17A96")
        } else {
            Color.parseColor("#E8A8B8")
        }
    }

    private fun lightColors(): List<Int> {
        return listOf(
            Color.parseColor("#E7B0C1"),
            Color.parseColor("#A8C9DB"),
            Color.parseColor("#B9ABD7"),
            Color.parseColor("#E9CC9C"),
            Color.parseColor("#E688A2"),
            Color.parseColor("#AED6C8")
        )
    }

    private fun darkColors(): List<Int> {
        return listOf(
            Color.parseColor("#9D5C73"),
            Color.parseColor("#5C7F96"),
            Color.parseColor("#756694"),
            Color.parseColor("#9D7D4A"),
            Color.parseColor("#A1516C"),
            Color.parseColor("#5E8C7C")
        )
    }

    fun spinToIndex(index: Int, onEnd: () -> Unit) {
        if (items.isEmpty() || index !in items.indices) return

        val sweep = 360f / items.size
        val selectedCenterAngle = index * sweep + sweep / 2f
        val targetTopAngle = 270f
        val extraSpin = 360f * (5 + Random.nextInt(3))
        val finalRotation =
            rotation + extraSpin + (targetTopAngle - selectedCenterAngle - (rotation % 360f))

        ObjectAnimator.ofFloat(this, View.ROTATION, rotation, finalRotation).apply {
            duration = 2600L
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onEnd()
                }
            })
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val safeItems = if (items.isEmpty()) listOf("ไม่มีเมนู") else items
        if (width <= 0 || height <= 0) return

        val size = min(width, height).toFloat()
        if (size <= 0f) return

        val padding = 10f
        val left = (width - size) / 2f + padding
        val top = (height - size) / 2f + padding
        val right = left + size - padding * 2
        val bottom = top + size - padding * 2

        rectF.set(left, top, right, bottom)

        val cx = rectF.centerX()
        val cy = rectF.centerY()
        val radius = rectF.width() / 2f
        if (radius <= 0f) return

        val sweep = 360f / safeItems.size

        for (i in safeItems.indices) {
            arcPaint.color = wheelColors[i % wheelColors.size]

            val startAngle = i * sweep - 90f
            canvas.drawArc(rectF, startAngle, sweep, true, arcPaint)

            val lineAngle = Math.toRadians(startAngle.toDouble())
            val x = (cx + cos(lineAngle) * radius).toFloat()
            val y = (cy + sin(lineAngle) * radius).toFloat()
            canvas.drawLine(cx, cy, x, y, separatorPaint)
        }

        val centerRadius = radius * 0.18f
        canvas.drawCircle(cx, cy, centerRadius, centerPaint)
        canvas.drawCircle(cx, cy, centerRadius * 0.25f, centerDotPaint)
    }
}