package com.example.terced_feliper.canvaspaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Drawing extends View {
    Paint[] pDraw = null;
    Bitmap bm = null;

    public Drawing(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Drawing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Drawing(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context ct) {

        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Generate bitmap used for background
        bm = BitmapFactory.decodeResource(ct.getResources(), R.drawable.main);

        // Generate array of paints
        pDraw = new Paint[16];

        for (int i = 0; i<pDraw.length; i++) {
            pDraw[i] = new Paint();
            pDraw[i].setARGB(255, 255, 255, 0);
            pDraw[i].setStrokeWidth(20);
            pDraw[i].setStyle(Paint.Style.FILL);
        }

        // Set all transfer modes
        pDraw[0].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        pDraw[1].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        pDraw[2].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
        pDraw[3].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        pDraw[4].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        pDraw[5].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        pDraw[6].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        pDraw[7].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        pDraw[8].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        pDraw[9].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        pDraw[10].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        pDraw[11].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        pDraw[12].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        pDraw[13].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        pDraw[14].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        pDraw[15].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
    }

    @Override
    public void onDraw(Canvas canv) {
        // Background colour for canvas
        canv.drawColor(Color.argb(255, 255, 0, 255));

        // Draw the bitmap leaving small outside border to see background
        canv.drawBitmap(bm, null, new RectF(15, 15, getMeasuredWidth() - 15, getMeasuredHeight() - 15), null);

        float w, h;
        w = getMeasuredWidth();
        h = getMeasuredHeight();

        // Loop, draws 4 circles per row, in 4 rows on top of bitmap
        // Drawn in the order of pDraw (alphabetical)
        for(int i = 0; i<4; i++) {
            for(int ii = 0; ii<4; ii++) {
                canv.drawCircle((w / 8) * (ii * 2 + 1), (h / 8) * (i * 2 + 1), w / 8 * 0.8f, pDraw[i*4 + ii]);
            }
        }
    }
}