package com.mmschooledu.MyanmarSpelling.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

public class GradientTextView extends androidx.appcompat.widget.AppCompatTextView {

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w > 0 && h > 0) { // ตรวจสอบว่าขนาดของ View ถูกวัดแล้ว
            Paint paint = getPaint();
            LinearGradient shader = new LinearGradient(
                    0, 0, 0, h, // ไล่สีจากบนลงล่าง
                    new int[]{
                            Color.parseColor("#FFFFFF"),
                            Color.parseColor("#FFFDFF"),
                            Color.parseColor("#D2A64F"),
                            Color.parseColor("#D6BB25"),
                            Color.parseColor("#FFEE00")
                    },
                    null,
                    Shader.TileMode.CLAMP
            );
            paint.setShader(shader);
            invalidate();
        }
    }
}
