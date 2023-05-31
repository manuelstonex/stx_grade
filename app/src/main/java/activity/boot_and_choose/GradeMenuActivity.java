package activity.boot_and_choose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stx_grade.R;

import activity.grade.Grade1D;
import utils.FullscreenActivity;

public class GradeMenuActivity extends AppCompatActivity {

    ImageView powerOff, toGrade;
    boolean flagActivity = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_menu);
        FullscreenActivity.setFullScreen(this);
        findView();
        onClick();
    }

    private void findView(){
        powerOff = findViewById(R.id.powerOff);
        toGrade = findViewById(R.id.toGrade);
    }

    private void onClick(){
        toGrade.setOnClickListener((View v) ->{
            if(!flagActivity) {
                flagActivity = true;
                startActivity(new Intent(this, Grade1D.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
