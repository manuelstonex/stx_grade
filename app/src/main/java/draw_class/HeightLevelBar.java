package draw_class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

public class HeightLevelBar extends View {

    Paint paint;
    public int level = 1;

    public HeightLevelBar(Context context) {
        super(context);
        paint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        Path path = new Path();

        float halfWidth = getWidth() / 2f;
        float halfHeight = getHeight() / 2f;

        float tl1_x, tl1_y;
        float tl2_x, tl2_y;
        float tl3_x, tl3_y;

        float r1_x, r1_y;
        float r2_x, r2_y;
        float r3_x, r3_y;
        float r4_x, r4_y;

        float tr1_x, tr1_y;
        float tr2_x, tr2_y;
        float tr3_x, tr3_y;


        tl1_x = getWidth() * 0.1f;
        tl1_y = getHeight() * 0.05f;
        tl2_x = getWidth() * 0.9f;
        tl2_y = getHeight() * 0.05f;
        tl3_x = halfWidth;
        tl3_y = getHeight() * 0.38f;

        if (level == 1){
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.RED);
        }
        else{
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
        }

        path.moveTo(tl1_x, tl1_y);
        path.lineTo(tl2_x, tl2_y);
        path.lineTo(tl3_x, tl3_y);
        path.close();
        canvas.drawPath(path, paint);
        path.reset();


        if (level == 0){
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.GREEN);
        }
        else{
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
        }
        r1_x = getWidth() * 0.1f;
        r1_y =  getHeight() * 0.42f;
        r2_x = getWidth() * 0.9f;
        r2_y =  getHeight() * 0.42f;
        r3_x = getWidth() * 0.9f;
        r3_y =  getHeight() * 0.58f;
        r4_x = getWidth() * 0.1f;
        r4_y =  getHeight() * 0.58f;
        canvas.drawRoundRect(new RectF(r1_x, r1_y, r3_x, r3_y), 5, 5, paint);

        tr1_x = getWidth() * 0.1f;
        tr1_y = getHeight() * 0.95f;
        tr2_x = getWidth() * 0.9f;
        tr2_y = getHeight() * 0.95f;
        tr3_x = halfWidth;
        tr3_y = getHeight() * 0.62f;

        if (level == -1){
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.BLUE);
        }
        else{
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
        }
        path.moveTo(tr1_x, tr1_y);
        path.lineTo(tr2_x, tr2_y);
        path.lineTo(tr3_x, tr3_y);
        path.close();
        canvas.drawPath(path, paint);
        path.reset();
    }
}
