package bamboomedia.speedantre.Auth;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import bamboomedia.speedantre.Cons.Constants;
import bamboomedia.speedantre.Interface.StringValidate;
import bamboomedia.speedantre.MainActivity;
import bamboomedia.speedantre.R;
import bamboomedia.speedantre.Session.SharedPrefrenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements StringValidate {

    @BindView(R.id.edtJenis)
    EditText edtJenis;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPass)
    EditText edtPass;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    private static final String TAG = SignInActivity.class.getSimpleName();
    private static final String URL = Constants.IP + Constants.HOST + Constants.SIGNIN;
    private static final String ADMIN_KEY = Constants.ADMIN_KEY;
    private static final String USER_KEY = Constants.USER_KEY;
    private static final String LOGIN_KEY = Constants.LOGIN_KEY;
    private static final String SP_LOGIN_KEY = Constants.IsLooggedIn;
    private static final String USER_KIND = Constants.USER_KIND;
    private static final int LEVEL_USER = Constants.LEVEL_USER;
    private static final int LEVEL_ADMIN = Constants.LEVEL_ADMIN;
    private static final String
            key_email = "email",
            key_jenis = "jenis",
            key_pass = "pass";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_here)
    TextView tvHere;
    @BindView(R.id.tv_signUp)
    TextView tvSignUp;
    private String email, pass, jenis;
    private RequestQueue requestQueue;

    private SharedPrefrenceManager sharedPrefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        changeFont();
        requestQueue = Volley.newRequestQueue(this);
        sharedPrefrenceManager = new SharedPrefrenceManager(this);

        onBtnSignInClicked();
    }

    private void onBtnSignInClicked() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean stringValidate = validateEmptyString();
                if (stringValidate) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent i;
                            Log.i(TAG, "onResponse: " + response);
                            if (response.equals(ADMIN_KEY)) {
                                sharedPrefrenceManager.saveBool(SP_LOGIN_KEY, true);
                                sharedPrefrenceManager.saveInt(USER_KIND, LEVEL_ADMIN);
                                i = new Intent(SignInActivity.this, MainActivity.class);
                                i.putExtra(LOGIN_KEY, ADMIN_KEY);
                                startActivity(i);
                            } else if (response.equals(USER_KEY)) {
                                sharedPrefrenceManager.saveBool(SP_LOGIN_KEY, true);
                                sharedPrefrenceManager.saveInt(USER_KIND, LEVEL_USER);
                                i = new Intent(SignInActivity.this, MainActivity.class);
                                i.putExtra(LOGIN_KEY, USER_KEY);
                                startActivity(i);
                            } else {
                                Log.d(TAG, "onResponse: Wrong string from echo signin.php");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "onErrorResponse: ", error);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();
                            data.put(key_pass, pass);
                            data.put(key_email, email);
                            data.put(key_jenis, jenis);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void getValueEditText() {
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        jenis = edtJenis.getText().toString();
    }

    @Override
    public boolean validateEmptyString() {
        getValueEditText();
        if (email == null && pass == null && jenis == null) {
            Toast.makeText(this, R.string.empty_string_validate, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.helvetica));
        tvTitle.setTypeface(font);
        edtEmail.setTypeface(font);
        edtPass.setTypeface(font);
        btnSignIn.setTypeface(font);
        tvSignUp.setTypeface(font);
        tvHere.setTypeface(font);

    }

}
