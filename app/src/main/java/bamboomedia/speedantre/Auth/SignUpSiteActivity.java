package bamboomedia.speedantre.Auth;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import bamboomedia.speedantre.Cons.Constants;
import bamboomedia.speedantre.Interface.StringValidate;
import bamboomedia.speedantre.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpSiteActivity extends AppCompatActivity implements StringValidate {

    @BindView(R.id.edtJamBuka)
    EditText edtJamBuka;
    @BindView(R.id.edtJamTutup)
    EditText edtJamTutup;
    @BindView(R.id.edtNama)
    EditText edtNama;
    @BindView(R.id.edtJenisSite)
    EditText edtJenisSite;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPass)
    EditText edtPass;
    @BindView(R.id.edtLat)
    EditText edtLat;
    @BindView(R.id.edtLng)
    EditText edtLng;
    @BindView(R.id.btnPickLocation)
    Button btnPickLocation;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_sign_type)
    TextView tvTitleSignType;
    @BindView(R.id.edtRetypePass)
    EditText edtRetypePass;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.edtHariBuka)
    EditText edtHariBuka;
    @BindView(R.id.edtHariTutup)
    EditText edtHariTutup;
    @BindView(R.id.tv_here)
    TextView tvHere;
    @BindView(R.id.tv_signIn)
    TextView tvSignIn;
    @BindView(R.id.tv_pick_status)
    TextView tvPickStatus;

    private String nama, jenis, email, pass, jamBuka, jamTutup, lat, lng;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = SignUpSiteActivity.class.getSimpleName();
    private static final String URL = Constants.IP + Constants.HOST + Constants.SIGNUPSITE;
    private static final String key_nama = "nama",
            key_jenis = "jenis",
            key_email = "email",
            key_pass = "pass",
            key_lng = "lng",
            key_lat = "lat",
            key_jamBuka = "jamBuka",
            key_jamTutup = "jamTutup";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_site);
        ButterKnife.bind(this);
        changeFont();
        requestQueue = Volley.newRequestQueue(this);
        onBtnSignUpClicked();
        onEdtJamBukaClicked();
        onEdtJamTutupClicked();
        onBtnPickLocationClicked();
    }

    private void onBtnSignUpClicked() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmptyString()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                            data.put(key_pass, pass);
                            data.put(key_nama, nama);
                            data.put(key_jenis, jenis);
                            data.put(key_jamBuka, jamBuka);
                            data.put(key_jamTutup, jamTutup);
                            data.put(key_lat, lat);
                            data.put(key_lng, lng);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void onEdtJamBukaClicked() {
        edtJamBuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentTime(edtJamBuka);
            }
        });
    }

    private void onEdtJamTutupClicked() {
        edtJamTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentTime(edtJamTutup);
            }
        });
    }

    private void getCurrentTime(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                editText.setText(time);
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    private void getValueFromEditText() {
        nama = edtNama.getText().toString();
        email = edtEmail.getText().toString();
        jenis = edtJenisSite.getText().toString();
        pass = edtPass.getText().toString();
        jamBuka = edtJamBuka.getText().toString();
        jamTutup = edtJamTutup.getText().toString();
        lat = edtLat.getText().toString();
        lng = edtLng.getText().toString();
    }

    private void onBtnPickLocationClicked() {
        btnPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(SignUpSiteActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            edtLat.setText(String.valueOf(place.getLatLng().latitude));
            edtLng.setText(String.valueOf(place.getLatLng().longitude));
        }
    }

    @Override
    public boolean validateEmptyString() {
        getValueFromEditText();
        if (nama == null && email == null && jenis == null && pass == null && jamBuka == null &&
                jamTutup == null && lat == null && lng == null) {
            Toast.makeText(this, R.string.empty_string_validate, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.helvetica));
        tvTitle.setTypeface(font);
        tvTitleSignType.setTypeface(font);
        edtNama.setTypeface(font);
        edtPass.setTypeface(font);
        edtRetypePass.setTypeface(font);
        address.setTypeface(font);
        edtJenisSite.setTypeface(font);
        edtJamBuka.setTypeface(font);
        edtJamTutup.setTypeface(font);
        edtHariBuka.setTypeface(font);
        edtHariTutup.setTypeface(font);
        edtEmail.setTypeface(font);
        edtPass.setTypeface(font);
        tvPickStatus.setTypeface(font);
        btnPickLocation.setTypeface(font);
        tvSignIn.setTypeface(font);
        tvHere.setTypeface(font);

    }

}
