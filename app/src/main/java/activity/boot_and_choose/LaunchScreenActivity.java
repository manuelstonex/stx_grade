package activity.boot_and_choose;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.stx_grade.R;
import java.io.File;
import java.util.Locale;
import utils.FullscreenActivity;


@SuppressLint("CustomSplashScreen")
public class LaunchScreenActivity extends AppCompatActivity {
    private ProgressBar pgBar;
    private int progress = 0;
    String[] PERMISSIONS;
    int PERMISSION_ALL = 1;
    CountDownTimer count;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        FullscreenActivity.setFullScreen(this);
        pgBar = findViewById(R.id.progressBar);




        PERMISSIONS = new String[]{Manifest.permission.BLUETOOTH,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.BLUETOOTH_PRIVILEGED,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS};

        createSystemFolders();

        askPermission();

        count = new CountDownTimer(3000, 40) {
            @Override
            public void onTick(long l) {
                progress++;
                pgBar.setProgress((int) progress++);
            }

            @Override
            public void onFinish() {
                goMain();

            }
        };
    }

    private void createSystemFolders() {
        String path = Environment.getExternalStorageDirectory().toString() + "/Stx_grade";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public void onBackPressed() {}

    protected void goMain() {
        setLocale("en");

        Intent intent;
        intent = new Intent(this, GradeMenuActivity.class);
        startActivity(intent);
    }


    public void askPermission() {
        requestPermission();
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS, PERMISSION_ALL);
                return;
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                count.start();
            } else {
                try {
                    count.wait();
                } catch (InterruptedException ignored) {
                }
                Toast.makeText(this, "Until you grant the permission, we cannot proceed further", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent a = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(a);
        }
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getBaseContext().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
