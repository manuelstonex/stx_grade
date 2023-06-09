package activity.boot_and_choose;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartActivityOnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent star = new Intent(context, LaunchScreenActivity.class);
            star.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(star);
        }
    }
}
