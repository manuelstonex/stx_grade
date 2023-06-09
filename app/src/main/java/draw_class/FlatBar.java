package draw_class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;

import java.util.ArrayList;

public class FlatBar extends View {


    Paint paint;

    ArrayList<PointF> slope1 = new ArrayList<>();
    ArrayList<PointF> laser1 = new ArrayList<>();
    ArrayList<PointF> laser2 = new ArrayList<>();

    float ax, ay;

    float bx, by;

    float mabx, maby;

    float cx, cy;

    float dx, dy;

    float mcdx, mcdy;

    public FlatBar(Context context) {
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

        float pivotX = getWidth() / 2f;
        float pivotY = getHeight() * 0.65f;



        float withBlade = 3.80f;
        float heightBlade = 1.6f;

        float scala = 20;

        ax = pivotX - ((withBlade / 2f) * scala);
        ay = pivotY - ((heightBlade / 2f) * scala);

        bx = pivotX + ((withBlade / 2f) * scala);
        by = pivotY - ((heightBlade / 2f) * scala);

        mabx = (ax + bx) / 2f;
        maby = (ay + by) / 2f;

        cx = pivotX - ((withBlade / 2f) * scala);
        cy = pivotY + ((heightBlade / 2f) * scala);

        dx = pivotX + ((withBlade / 2f) * scala);
        dy = pivotY + ((heightBlade / 2f) * scala);

        mcdx = (cx + dx) / 2f;
        mcdy = (cy + dy) / 2f;

        paint.setColor(Color.YELLOW);
        path.moveTo(ax, ay);
        path.lineTo(mabx, maby);
        path.lineTo(bx, by);
        path.lineTo(dx, dy);
        path.lineTo(mcdx, mcdy);
        path.lineTo(cx, cy);
        path.close();

        canvas.drawPath(path, paint);
        path.reset();

        float widthSlope = withBlade * 0.05f * scala;

        float heightSlope = withBlade * 0.15f * scala * 0.35f;

        float widthBaseLaser = withBlade * 0.05f * scala;

        float widthStickLaser = withBlade * 0.025f * scala;

        float widthLaser = withBlade * 0.025f * scala;

        float heightLaser = withBlade * 0.6f * scala;

        float heightStick = withBlade * 0.6f * scala * 0.75f;

        float heightBase = withBlade * 0.6f * scala * 0.3f;

        float pivotSlopeXLeft = ax + withBlade * 0.15f * scala;

        float pivotSlopeXRight = bx - withBlade * 0.15f * scala;

        int bladeConfig = 3;


        if(!slope1.isEmpty()){
            paint.setColor(Color.GRAY);
            path.moveTo(slope1.get(0).x, slope1.get(0).y);
            for (int i = 1; i < slope1.size(); i++) {
                path.lineTo(slope1.get(i).x, slope1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        if(!laser1.isEmpty()){
            paint.setColor(Color.YELLOW);
            path.moveTo(laser1.get(0).x, laser1.get(0).y);
            for (int i = 1; i < 4; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.GRAY);
            path.moveTo(laser1.get(4).x, laser1.get(4).y);
            for (int i = 5; i < 8; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.RED);
            path.moveTo(laser1.get(8).x, laser1.get(8).y);
            for (int i = 9; i < 12; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        if(!laser2.isEmpty()){
            paint.setColor(Color.YELLOW);
            path.moveTo(laser2.get(0).x, laser2.get(0).y);
            for (int i = 1; i < 4; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.GRAY);
            path.moveTo(laser2.get(4).x, laser2.get(4).y);
            for (int i = 5; i < 8; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.RED);
            path.moveTo(laser2.get(8).x, laser2.get(8).y);
            for (int i = 9; i < 12; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        canvas.drawLine(cx - (withBlade * 0.1f * scala), cy, dx + (withBlade * 0.1f * scala), dy, paint);


        pivotX = getWidth() * 0.20f;
        pivotY = getHeight() * 0.65f;

        ax = pivotX - ((withBlade / 2f) * scala);
        ay = pivotY - ((heightBlade / 2f) * scala);

        bx = pivotX + ((withBlade / 2f) * scala);
        by = pivotY - ((heightBlade / 2f) * scala);

        mabx = (ax + bx) / 2f;
        maby = (ay + by) / 2f;

        cx = pivotX - ((withBlade / 2f) * scala);
        cy = pivotY + ((heightBlade / 2f) * scala);

        dx = pivotX + ((withBlade / 2f) * scala);
        dy = pivotY + ((heightBlade / 2f) * scala);

        mcdx = (cx + dx) / 2f;
        mcdy = (cy + dy) / 2f;



        paint.setColor(Color.YELLOW);
        path.moveTo(ax, ay);
        path.lineTo(mabx, maby);
        path.lineTo(bx, by);
        path.lineTo(dx, dy);
        path.lineTo(mcdx, mcdy);
        path.lineTo(cx, cy);
        path.close();

        canvas.drawPath(path, paint);
        path.reset();



        slope1.clear();
        laser1.clear();
        laser2.clear();


        switch (bladeConfig){
            case 0:
                slope1.add(new PointF(pivotSlopeXLeft - widthSlope, ay - heightSlope));
                slope1.add(new PointF(pivotSlopeXLeft + widthSlope, ay - heightSlope));
                slope1.add(new PointF(pivotSlopeXLeft + widthSlope, ay));
                slope1.add(new PointF(pivotSlopeXLeft - widthSlope, ay));
                break;
            case 1:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));

                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by));
                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by));

                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightBase));

                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightStick));
                break;
            case 2:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));
                break;
            case 3:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));

                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by));
                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by));
                break;
            case 4:
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay));
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay));

                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightBase));

                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightStick));

                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by));
                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by));

                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightBase));

                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightStick));
                break;
            case 5:
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay));
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay));

                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightBase));

                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightStick));

                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by));
                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by));
                break;
        }

        if(!slope1.isEmpty()){
            paint.setColor(Color.GRAY);
            path.moveTo(slope1.get(0).x, slope1.get(0).y);
            for (int i = 1; i < slope1.size(); i++) {
                path.lineTo(slope1.get(i).x, slope1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        if(!laser1.isEmpty()){
            paint.setColor(Color.YELLOW);
            path.moveTo(laser1.get(0).x, laser1.get(0).y);
            for (int i = 1; i < 4; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.GRAY);
            path.moveTo(laser1.get(4).x, laser1.get(4).y);
            for (int i = 5; i < 8; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.RED);
            path.moveTo(laser1.get(8).x, laser1.get(8).y);
            for (int i = 9; i < 12; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        if(!laser2.isEmpty()){
            paint.setColor(Color.YELLOW);
            path.moveTo(laser2.get(0).x, laser2.get(0).y);
            for (int i = 1; i < 4; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.GRAY);
            path.moveTo(laser2.get(4).x, laser2.get(4).y);
            for (int i = 5; i < 8; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.RED);
            path.moveTo(laser2.get(8).x, laser2.get(8).y);
            for (int i = 9; i < 12; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        pivotX = getWidth() * 0.80f;
        pivotY = getHeight() * 0.65f;

        ax = pivotX - ((withBlade / 2f) * scala);
        ay = pivotY - ((heightBlade / 2f) * scala);

        bx = pivotX + ((withBlade / 2f) * scala);
        by = pivotY - ((heightBlade / 2f) * scala);

        mabx = (ax + bx) / 2f;
        maby = (ay + by) / 2f;

        cx = pivotX - ((withBlade / 2f) * scala);
        cy = pivotY + ((heightBlade / 2f) * scala);

        dx = pivotX + ((withBlade / 2f) * scala);
        dy = pivotY + ((heightBlade / 2f) * scala);

        mcdx = (cx + dx) / 2f;
        mcdy = (cy + dy) / 2f;



        paint.setColor(Color.YELLOW);
        path.moveTo(ax, ay);
        path.lineTo(mabx, maby);
        path.lineTo(bx, by);
        path.lineTo(dx, dy);
        path.lineTo(mcdx, mcdy);
        path.lineTo(cx, cy);
        path.close();

        canvas.drawPath(path, paint);
        path.reset();



        slope1.clear();
        laser1.clear();
        laser2.clear();


        switch (bladeConfig){
            case 0:
                slope1.add(new PointF(pivotSlopeXLeft - widthSlope, ay - heightSlope));
                slope1.add(new PointF(pivotSlopeXLeft + widthSlope, ay - heightSlope));
                slope1.add(new PointF(pivotSlopeXLeft + widthSlope, ay));
                slope1.add(new PointF(pivotSlopeXLeft - widthSlope, ay));
                break;
            case 1:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));

                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by));
                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by));

                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightBase));

                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightStick));
                break;
            case 2:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));
                break;
            case 3:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));

                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by));
                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by));
                break;
            case 4:
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay));
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay));

                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightBase));

                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightStick));

                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by));
                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by));

                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightBase));

                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightStick));
                break;
            case 5:
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay));
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay));

                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightBase));

                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightStick));

                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by));
                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by));
                break;
        }

        if(!slope1.isEmpty()){
            paint.setColor(Color.GRAY);
            path.moveTo(slope1.get(0).x, slope1.get(0).y);
            for (int i = 1; i < slope1.size(); i++) {
                path.lineTo(slope1.get(i).x, slope1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        if(!laser1.isEmpty()){
            paint.setColor(Color.YELLOW);
            path.moveTo(laser1.get(0).x, laser1.get(0).y);
            for (int i = 1; i < 4; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.GRAY);
            path.moveTo(laser1.get(4).x, laser1.get(4).y);
            for (int i = 5; i < 8; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.RED);
            path.moveTo(laser1.get(8).x, laser1.get(8).y);
            for (int i = 9; i < 12; i++) {
                path.lineTo(laser1.get(i).x, laser1.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }

        if(!laser2.isEmpty()){
            paint.setColor(Color.YELLOW);
            path.moveTo(laser2.get(0).x, laser2.get(0).y);
            for (int i = 1; i < 4; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.GRAY);
            path.moveTo(laser2.get(4).x, laser2.get(4).y);
            for (int i = 5; i < 8; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();

            paint.setColor(Color.RED);
            path.moveTo(laser2.get(8).x, laser2.get(8).y);
            for (int i = 9; i < 12; i++) {
                path.lineTo(laser2.get(i).x, laser2.get(i).y);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
        }
    }

    private void configMyBlade(int bladeConfig){
        switch (bladeConfig){
            case 0:
                slope1.add(new PointF(pivotSlopeXLeft - widthSlope, ay - heightSlope));
                slope1.add(new PointF(pivotSlopeXLeft + widthSlope, ay - heightSlope));
                slope1.add(new PointF(pivotSlopeXLeft + widthSlope, ay));
                slope1.add(new PointF(pivotSlopeXLeft - widthSlope, ay));
                break;
            case 1:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));

                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by));
                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by));

                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightBase));

                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightStick));
                break;
            case 2:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));
                break;
            case 3:
                laser1.add(new PointF(mabx - widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby - heightBase));
                laser1.add(new PointF(mabx + widthBaseLaser, maby));
                laser1.add(new PointF(mabx - widthBaseLaser, maby));

                laser1.add(new PointF(mabx - widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightStick));
                laser1.add(new PointF(mabx + widthStickLaser, maby - heightBase));
                laser1.add(new PointF(mabx - widthStickLaser, maby - heightBase));

                laser1.add(new PointF(mabx - widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightLaser));
                laser1.add(new PointF(mabx + widthLaser, maby - heightStick));
                laser1.add(new PointF(mabx - widthLaser, maby - heightStick));

                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by));
                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by));
                break;
            case 4:
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay));
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay));

                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightBase));

                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightStick));

                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight + widthBaseLaser, by));
                laser2.add(new PointF(pivotSlopeXRight - widthBaseLaser, by));

                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight + widthStickLaser, by - heightBase));
                laser2.add(new PointF(pivotSlopeXRight - widthStickLaser, by - heightBase));

                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightLaser));
                laser2.add(new PointF(pivotSlopeXRight + widthLaser, by - heightStick));
                laser2.add(new PointF(pivotSlopeXRight - widthLaser, by - heightStick));
                break;
            case 5:
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft + widthBaseLaser, ay));
                laser1.add(new PointF(pivotSlopeXLeft - widthBaseLaser, ay));

                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft + widthStickLaser, ay - heightBase));
                laser1.add(new PointF(pivotSlopeXLeft - widthStickLaser, ay - heightBase));

                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightLaser));
                laser1.add(new PointF(pivotSlopeXLeft + widthLaser, ay - heightStick));
                laser1.add(new PointF(pivotSlopeXLeft - widthLaser, ay - heightStick));

                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by - heightSlope));
                slope1.add(new PointF(pivotSlopeXRight + widthSlope, by));
                slope1.add(new PointF(pivotSlopeXRight - widthSlope, by));
                break;
        }
    }

}
