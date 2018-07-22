package bamboomedia.speedantre.Require;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import bamboomedia.speedantre.R;

public class Permission {
    private Context context;
    private int currentAPIVersion = Build.VERSION.SDK_INT;
    private static final int LOCATION_REQUEST_CODE = 1340;

    public Permission(Context context) {
        this.context = context;
    }

    public boolean checkPermissionGPS() {
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showDialogRequest(R.drawable.ic_location,
                            R.string.gps_permission_string, Manifest.permission.ACCESS_FINE_LOCATION);
                }else {
                    ActivityCompat.requestPermissions((Activity)context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                }
                return false;
            }else{
                return true;
            }
        }
        return true;
    }

    private void showDialogRequest(int icon, int message, final String permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon);
        builder.setMessage(message);
        builder.setNegativeButton(R.string.reject_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(R.string.allow_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, LOCATION_REQUEST_CODE);
            }
        });

        builder.show();
    }

}
