package bamboomedia.speedantre;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import bamboomedia.speedantre.Cons.Constants;
import bamboomedia.speedantre.FragmentAdmin.Fragment_BookAdmin;
import bamboomedia.speedantre.FragmentAdmin.Fragment_HomeAdmin;
import bamboomedia.speedantre.FragmentAdmin.Fragment_ProfileAdmin;
import bamboomedia.speedantre.FragmentUser.Fragment_BookUser;
import bamboomedia.speedantre.FragmentUser.Fragment_HomeUser;
import bamboomedia.speedantre.FragmentUser.Fragment_ProfileUser;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigationAdmin)
    BottomNavigationViewEx navigationViewAdmin;
    @BindView(R.id.navigationUser)
    BottomNavigationViewEx navigationViewUser;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String
            LOGIN_KEY = Constants.LOGIN_KEY,
            ADMIN_KEY = Constants.ADMIN_KEY,
            USER_KEY = Constants.USER_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String message = bundle.getString(LOGIN_KEY);
            if (message != null) {
                if (message.equals(ADMIN_KEY)) {
                    navigationViewUser.setVisibility(View.GONE);
                    loadFragment(new Fragment_HomeAdmin());
                } else if (message.equals(USER_KEY)) {
                    navigationViewAdmin.setVisibility(View.GONE);
                    loadFragment(new Fragment_HomeUser());
                } else {
                    Log.d(TAG, "onCreate: wrong string from message bundle (SignInActivity)");
                }
            }
        }

        customizeButtomNav();

        navigationViewUser.setOnNavigationItemSelectedListener(this);
        navigationViewAdmin.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home_admin:
                fragment = new Fragment_HomeAdmin();
                break;
            case R.id.navigation_book_admin:
                fragment = new Fragment_BookAdmin();
                break;
            case R.id.navigation_profile_admin:
                fragment = new Fragment_ProfileAdmin();
                break;
            case R.id.navigation_book_user:
                fragment = new Fragment_BookUser();
                break;
            case R.id.navigation_home_user:
                fragment = new Fragment_HomeUser();
                break;
            case R.id.navigation_profile_user:
                fragment = new Fragment_ProfileUser();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FLcontainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void customizeButtomNav(){
        navigationViewUser.setTextVisibility(false);
        navigationViewUser.setIconSize(28,28);
        navigationViewAdmin.setTextVisibility(false);
        navigationViewAdmin.setIconSize(28,28);
    }
}
