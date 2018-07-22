package bamboomedia.speedantre.FragmentUser;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import bamboomedia.speedantre.Auth.HomeActivity;
import bamboomedia.speedantre.Listener.OnSwipeTouchListener;
import bamboomedia.speedantre.R;
import bamboomedia.speedantre.Session.SharedPrefrenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_ProfileUser extends Fragment {

    private static final String TAG = "Tag";
    @BindView(R.id.btnLogOut)
    Button btnLogOut;
    @BindView(R.id.iv_profile_picture)
    ImageView ivProfilePicture;
    @BindView(R.id.profile_texts)
    LinearLayout profileTexts;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.edtNama)
    EditText edtNama;
    @BindView(R.id.edtTlp)
    EditText edtTlp;
    @BindView(R.id.profile_gone)
    LinearLayout profileGone;
    @BindView(R.id.wrapper)
    LinearLayout wrapper;
    @BindView(R.id.iv_arrow_up)
    ImageView ivArrowUp;

    private SharedPrefrenceManager sharedPrefrenceManager;
    private OnSwipeTouchListener swipeTouchListener;

    public Fragment_ProfileUser() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        ButterKnife.bind(this, view);
        sharedPrefrenceManager = new SharedPrefrenceManager(getContext());
        changeFont();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefrenceManager.deleteSharedPref();
                Intent i = new Intent(getContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide();
            }
        });
        swipeTouchListener = new OnSwipeTouchListener(getContext());

        profileTexts.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeTop() {
                slide();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(300)
                        .width(R.dimen.thirtytwo)
                        .height(R.dimen.thirtytwo)
                        .color(Color.GREEN)
                        .icon(R.drawable.ic_done_white_24dp);
                btnSave.morph(circle);*/
            }
        });

        return view;
    }

    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.helvetica));
        TextView[] textViews = {tvName, tvPhone, tvEmail};
        Button[] buttons = {btnLogOut, btnEdit, btnSave};
        EditText[] editTexts = {edtNama, edtTlp};
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

    private void slide() {
        int first_height = profileTexts.getHeight();
        int widthSpec = View.MeasureSpec.makeMeasureSpec(wrapper.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        profileGone.measure(widthSpec, heightSpec);
        int second_height = profileGone.getMeasuredHeight();
        Log.e(TAG, "onClick:" + first_height + " & " + second_height);

        ValueAnimator anim = ValueAnimator.ofInt(first_height, second_height);
        Log.e(TAG, "onClick: " + first_height + " & " + second_height);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                profileTexts.getLayoutParams().height = (Integer) valueAnimator.getAnimatedValue();
                profileTexts.requestLayout();
            }
        });
        anim.setDuration(300);
        anim.start();

        btnEdit.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
        ivArrowUp.setRotationX(180);
    }

}
