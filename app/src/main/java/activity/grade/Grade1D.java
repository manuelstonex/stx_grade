package activity.grade;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.stx_grade.R;

import draw_class.Draw1D;
import draw_class.HeightLevelBar;
import utils.FullscreenActivity;

public class Grade1D extends AppCompatActivity {

    ConstraintLayout panel1D, heightBarLeft, heightBarRight;
    Draw1D draw1D;
    HeightLevelBar height_left, height_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_1d);
        FullscreenActivity.setFullScreen(this);
        findView();
        init();
    }

    private void findView(){
        panel1D = findViewById(R.id.panel1D);
        heightBarLeft = findViewById(R.id.heightBarLeft);
        heightBarRight = findViewById(R.id.heightRightBar);
    }

    private void init(){
        draw1D = new Draw1D(this);
        height_left = new HeightLevelBar(this);
        height_right = new HeightLevelBar(this);


        panel1D.addView(draw1D);

        heightBarLeft.addView(height_left);
        heightBarRight.addView(height_right);



    }
}
