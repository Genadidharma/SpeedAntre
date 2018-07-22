package bamboomedia.speedantre.Auth;

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
import bamboomedia.speedantre.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpUserActivity extends AppCompatActivity implements StringValidate {

    @BindView(R.id.edtNama)
    EditText edtNama;
    @BindView(R.id.edtPass)
    EditText edtPass;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtTlp)
    EditText edtTlp;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_sign_type)
    TextView tvTitleSignType;
    @BindView(R.id.tv_here)
    TextView tvHere;
    @BindView(R.id.tv_signUp)
    TextView tvSignUp;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String URL = Constants.IP + Constants.HOST + Constants.SIGNUPUSER;
    private String nama, pass, email, tlp;
    private static final String TAG = SignUpUserActivity.class.getSimpleName();
    private static final String key_nama = "nama",
            key_pass = "pass",
            key_email = "email",
            key_telp = "telp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);
        ButterKnife.bind(this);
        changeFont();
        requestQueue = Volley.newRequestQueue(this);
        onBtnSignUpClicked();

    }

    private void onBtnSignUpClicked() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmptyString()) {
                    stringRequest = new StringRequest(StringRequest.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "onResponse: " + response);
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
                            data.put(key_email, email);
                            data.put(key_nama, nama);
                            data.put(key_pass, pass);
                            data.put(key_telp, tlp);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void getValueEditText() {
        nama = edtNama.getText().toString();
        tlp = edtTlp.getText().toString();
        pass = edtPass.getText().toString();
        email = edtEmail.getText().toString();
    }

    @Override
    public boolean validateEmptyString() {
        getValueEditText();
        if (nama == null && tlp == null && pass == null && email == null) {
            Toast.makeText(this, R.string.empty_string_validate, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.helvetica));
        edtNama.setTypeface(font);
        edtEmail.setTypeface(font);
        edtPass.setTypeface(font);
        edtTlp.setTypeface(font);
        btnSignUp.setTypeface(font);
        tvHere.setTypeface(font);
        tvSignUp.setTypeface(font);
        tvTitle.setTypeface(font);
        tvTitleSignType.setTypeface(font);

    }
}
