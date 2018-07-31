package com.example.terced_feliper.canvaspaint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.R.attr.y
import android.R.attr.x
import android.view.MotionEvent
import android.R.attr.bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap






class ArtView : View {

    private var x = 0
    private var y = 0
    private var erasePath: Path = Path()
    private lateinit var bitmap: Bitmap
    private lateinit var bgBitmap: Bitmap
    private lateinit var filterBitmap: Bitmap
    private var paint: Paint = Paint()
    private var eraserPaint: Paint = Paint()
    private var filterCanvas: Canvas = Canvas()
    private var filterPaint: Paint = Paint()

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
        filterBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.filter)

        // aceleração de hardware
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        //eraserPaint.setAlpha(0)
        eraserPaint.setStrokeJoin(Paint.Join.ROUND)
        eraserPaint.setStrokeCap(Paint.Cap.ROUND)
        eraserPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        eraserPaint.setAntiAlias(true)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
        //canvas?.drawBitmap(filterBitmap, 0f, 0f, paint)
        canvas?.drawCircle(x.toFloat(), y.toFloat(), 50f, eraserPaint)

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
        erasePath.addCircle(x.toFloat(), y.toFloat(), 50f, Path.Direction.CW)
        return true
    }

    fun getFinalBitmap(bmp1: Bitmap, bmp2: Bitmap): Bitmap {
        val bmOverlay = Bitmap.createBitmap(bmp1.width, bmp1.height, bmp1.config)
        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(bmp1, Matrix(), null)
        canvas.drawBitmap(bmp2, 0f, 0f, null)
        return bmOverlay
    }
}