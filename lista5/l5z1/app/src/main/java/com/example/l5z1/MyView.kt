package com.example.l5z1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class MyView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {
    private val progressCirclePaint: Paint = Paint()
    init {
        progressCirclePaint.color = Color.RED
        progressCirclePaint.style = Paint.Style.STROKE
        //progressCirclePaint.strokeWidth = 20f
        progressCirclePaint.strokeCap = Paint.Cap.ROUND
        progressCirclePaint.isAntiAlias = true
    }
    var center = Point()
    var myradius = 0f
    var mSweepAngle = 0f
    var mStartAngle = 270f
    var path = Path()
    var mRectF = RectF()


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        center.x = w / 2
        center.y = h / 2

        var diameter = (w.coerceAtMost(h)).toFloat()
        progressCirclePaint.strokeWidth = diameter/10
        myradius = diameter/2 - progressCirclePaint.strokeWidth// it will collide with edges of our
        mRectF.set(                                            // view without this -
            center.x - myradius,
            center.y - myradius,
            center.x + myradius,
            center.y + myradius
        )
    }
    override fun onDraw(canvas: Canvas?) {

        path.reset()
        path.arcTo(mRectF, mStartAngle, mSweepAngle * 360f)

        if(mSweepAngle*360f==360f) {
            path.addCircle(center.x.toFloat(), center.y.toFloat(), myradius, Path.Direction.CCW)
        }

        canvas?.drawPath(path, progressCirclePaint)
        return super.onDraw(canvas)
    }

    fun move() {

        this.mSweepAngle += 0.01f
        invalidate()
    }
}