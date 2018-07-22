package bamboomedia.speedantre.FragmentAdmin;


import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bamboomedia.speedantre.R;
import bamboomedia.speedantre.Session.SharedPrefrenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_ProfileAdmin extends Fragment {

    @BindView(R.id.btnLogOut)
    Button btnLogout;
    @BindView(R.id.iv_profile_picture)
    ImageView ivProfilePicture;
    @BindView(R.id.profile_texts)
    LinearLayout profileTexts;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edtJenisSite)
    EditText edtJenisSite;
    @BindView(R.id.edtHariBuka)
    EditText edtHariBuka;
    @BindView(R.id.edtHariTutup)
    EditText edtHariTutup;
    @BindView(R.id.edtJamBuka)
    EditText edtJamBuka;
    @BindView(R.id.edtJamTutup)
    EditText edtJamTutup;
    @BindView(R.id.edtLat)
    EditText edtLat;
    @BindView(R.id.edtLng)
    EditText edtLng;
    @BindView(R.id.tv_pick_status)
    TextView tvPickStatus;
    @BindView(R.id.btnPickLocation)
    Button btnPickLocation;
    @BindView(R.id.profile_gone)
    LinearLayout profileGone;
    @BindView(R.id.wrapper)
    LinearLayout wrapper;
    @BindView(R.id.edtNama)
    EditText edtNama;
    @BindView(R.id.iv_arrow_up)
    ImageView ivArrowUp;

    private SharedPrefrenceManager sharedPrefrenceManager;

    public Fragment_ProfileAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_admin, container, false);
        ButterKnife.bind(this, view);
        sharedPrefrenceManager = new SharedPrefrenceManager(getContext());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefrenceManager.deleteSharedPref();
            }
        });
        changeFont();
        slide();
        return view;
    }

    private void slide() {
        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int first_height = profileTexts.getHeight();
                int widthSpec = View.MeasureSpec.makeMeasureSpec(wrapper.getWidth(), View.MeasureSpec.EXACTLY);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                profileGone.measure(widthSpec, heightSpec);
                int second_height = profileGone.getMeasuredHeight();

                ValueAnimator anim = ValueAnimator.ofInt(first_height, second_height);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Integer value = (Integer) valueAnimator.getAnimatedValue();
                        profileTexts.getLayoutParams().height = value;
                        profileTexts.requestLayout();
                    }
                });
                anim.setDuration(300);
                anim.start();

                btnEdit.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                ivArrowUp.setRotationX(180);
            }
        });
    }

    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.helvetica));

        TextView[] textViews = {tvCompany, tvAdress, tvPickStatus};
        Button[] buttons = {btnSave, btnEdit, btnSave, btnPickLocation, btnLogout};
        EditText[] editTexts = {edtNama, edtAddress, edtHariBuka, edtHariTutup, edtJamBuka, edtJamTutup, edtJenisSite, edtLat, edtLng};
        for (TextView textView : textViews) {
            textView.setTypeface(font);
            for (Button button : buttons) {
                button.setTypeface(font);
                for (EditText editText : editTexts) {
                    editText.setTypeface(font);
                }
            }
        }

    }

}
