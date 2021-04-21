package com.sample.mvp_rxjava_architecture.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import com.sample.mvp_rxjava_architecture.R;
import java.util.List;

@SuppressLint({"UseCompatLoadingForDrawables","DrawAllocation"})
public class CustomKeyBoardView extends KeyboardView {

    public CustomKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            List<Keyboard.Key> keys = getKeyboard().getKeys();
            for (Keyboard.Key key : keys) {
                if (key.codes[0] == -100 || key.codes[0] == -1000 || key.codes[0] == -10000) {
                    setDrawable(canvas, key, R.drawable.bg_keyboard_count);
                } else if (key.codes[0] == -999) {
                    setDrawable(canvas, key, R.drawable.bg_keyboard_delete);
                } else {
                    setDrawable(canvas, key, R.drawable.bg_keyboard_number);
                }
                Paint paint = new Paint();
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(36);
                paint.setColor(Color.BLACK);
                if (key.label != null) {
                    canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                            key.y + (key.height / 2) + 14, paint);
                } else {
                    key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    key.icon.draw(canvas);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDrawable(Canvas canvas, Keyboard.Key key, int res) {
        Drawable drawable = getResources().getDrawable(res);
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        drawable.draw(canvas);
    }

}
