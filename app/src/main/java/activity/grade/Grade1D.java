package activity.grade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.stx_grade.R;
import java.util.Random;
import draw_class.Draw1D;
import draw_class.FlatBar;
import draw_class.HeightLevelBar;
import utils.FullscreenActivity;

public class Grade1D extends AppCompatActivity {

    ConstraintLayout panel1D, heightBarLeft, heightBarRight;
    FrameLayout flatBar;
    Draw1D draw1D;
    HeightLevelBar height_left, height_right;
    FlatBar flat;
    Handler handler;
    boolean mRunning = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_1d);
        FullscreenActivity.setFullScreen(this);
        findView();
        init();
        updateUI();
    }

    private void findView(){
        panel1D = findViewById(R.id.panel1D);
        heightBarLeft = findViewById(R.id.heightBarLeft);
        heightBarRight = findViewById(R.id.heightRightBar);
        flatBar = findViewById(R.id.deltaCenter);
    }

    private void init(){
        draw1D = new Draw1D(this);
        height_left = new HeightLevelBar(this);
        height_right = new HeightLevelBar(this);
        flat = new FlatBar(this);


        panel1D.addView(draw1D);

        heightBarLeft.addView(height_left);
        heightBarRight.addView(height_right);

        flatBar.addView(flat);
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        handler = new Handler();
        new Thread(() -> {
            while (mRunning) {

                handler.post(() -> {
                    int randomNumber = new Random().nextInt(3) - 1;

                    height_left.level = randomNumber;
                    height_right.level = randomNumber;

                    draw1D.invalidate();
                    height_left.invalidate();
                    height_right.invalidate();
                });

                try {
                    Thread.sleep(2500);
                }
                catch (Exception ignored) {}
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRunning = false;
    }
}
