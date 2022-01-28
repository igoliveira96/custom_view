package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class CircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint().apply {
        color = Color.RED
    }
    private val centre = PointF(50F, 50F)

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(centre.x, centre.y, 50F, paint)
    }

    fun setRandomColor() {
        paint.color =
            getRandomColor()
    }

    private fun getRandomColor(): Int {
        return Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                val touchPoint = PointF(event.x, event.y)

                //Distance between two points
                val distance = sqrt(
                    (centre.x - touchPoint.x).pow(2)
                            + (centre.y - touchPoint.y).pow(2)
                )

                if (distance <= 50) {
                    callOnClick()
                }
            }
        }
        return true
    }

}