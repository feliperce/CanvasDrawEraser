package com.example.terced_feliper.canvaspaint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.R.attr.y
import android.R.attr.x
import android.view.MotionEvent
import android.R.attr.bitmap




class ArtView : View {

    private var x = 0
    private var y = 0
    private var erasePath: Path = Path()
    private lateinit var bitmap: Bitmap
    private var paint: Paint = Paint()
    private var eraserPaint: Paint = Paint()

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.main)

        // aceleração de hardware
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        //eraserPaint.setAlpha(0)
        eraserPaint.setStrokeJoin(Paint.Join.ROUND)
        eraserPaint.setStrokeCap(Paint.Cap.ROUND)
        eraserPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        eraserPaint.setAntiAlias(true)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
        canvas?.drawCircle(x.toFloat(), y.toFloat(), 30f, eraserPaint)

        canvas?.drawPath(erasePath, eraserPaint);
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                x = event.getX().toInt()
                y = event.getY().toInt()


                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {

                x = event.getX().toInt()
                y = event.getY().toInt()

                invalidate()

            }

            MotionEvent.ACTION_UP ->

                invalidate()
        }
        //erasePath.reset()
        erasePath.addCircle(x.toFloat(), y.toFloat(), 30f, Path.Direction.CW)
        return true
    }
}