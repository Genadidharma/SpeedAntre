package bamboomedia.speedantre.Auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import bamboomedia.speedantre.Cons.Constants;
import bamboomedia.speedantre.MainActivity;
import bamboomedia.speedantre.R;
import bamboomedia.speedantre.Require.Permission;
import bamboomedia.speedantre.Session.SharedPrefrenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btnsignIn)
    Button btnsignIn;
    @BindView(R.id.btnsignUpUser)
    Button btnsignUpUser;
    @BindView(R.id.btnSignUpAdmin)
    Button btnSignUpAdmin;

    private static final String SP_LOGIN_KEY = Constants.IsLooggedIn;
    private static final String USER_KIND = Constants.USER_KIND;
    private static final String LOGIN_KEY = Constants.LOGIN_KEY;
    private static final String ADMIN_KEY = Constants.ADMIN_KEY;
    private static final String USER_KEY = Constants.USER_KEY;
    private static final int LEVEL_USER = Constants.LEVEL_USER;
    private static final int LEVEL_ADMIN = Constants.LEVEL_ADMIN;


    private Permission permission;
    private SharedPrefrenceManager sharedPrefrenceManager;
    private boolean isLoggedIn;
    private int ADMINORUSER;
    private Intent i;

    @Override
    protected void onStart() {
        super.onStart();
        Intent i;
        sharedPrefrenceManager = new SharedPrefrenceManager(this);
        isLoggedIn = sharedPrefrenceManager.getBool(SP_LOGIN_KEY);
        ADMINORUSER = sharedPrefrenceManager.getInt(USER_KIND);
        if (isLoggedIn){
            if (ADMINORUSER == LEVEL_ADMIN){
                i = new Intent(this, MainActivity.class);
                i.putExtra(LOGIN_KEY, ADMIN_KEY);
                startActivity(i);
            }else if (ADMINORUSER == LEVEL_USER){
                i = new Intent(this, MainActivity.class);
                i.putExtra(LOGIN_KEY, USER_KEY);
                startActivity(i);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        transparentStatusBar();

        permission= new Permission(this);
        permission.checkPermissionGPS();
        onBtnSignInClicked();
        onBtnSignUpAdminClicked();
        onBtnSignUpUserClicked();
    }

    private void onBtnSignUpUserClicked() {
        btnSignUpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(HomeActivity.this, SignUpSiteActivity.class);
                startActivity(i);
            }
        });
    }

    private void onBtnSignInClicked() {
        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }

    private void onBtnSignUpAdminClicked() {
        btnsignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(HomeActivity.this, SignUpUserActivity.class);
                startActivity(i);
            }
        });
    }

    private void transparentStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
