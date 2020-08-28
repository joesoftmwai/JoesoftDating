package com.joesoft.joesoftdating;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joesoft.joesoftdating.models.User;
import com.joesoft.joesoftdating.util.PreferenceKeys;
import com.joesoft.joesoftdating.util.Resources;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private IMainActivity mInterface;
    private ArrayList<User> mUsers = new ArrayList<>();

    private ImageView mStsBackButton;
    private CircleImageView mStsCamera;
    private ImageView mStsBackground;
    private EditText mStsName;
    private Spinner mStsSpinnerGender;
    private Spinner mStsSpinnerInterestedIn;
    private Spinner mStsSpinnerStatus;
    private Button mStsSaveButton;
    private EditText mStsEmail;
    private EditText mStsPhone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mStsBackButton = view.findViewById(R.id.stsBackButton);
        mStsCamera = view.findViewById(R.id.stsCamera);
        mStsBackground = view.findViewById(R.id.stsBackground);
        mStsName = view.findViewById(R.id.stsName);
        mStsSpinnerGender = view.findViewById(R.id.stsSpinnerGender);
        mStsSpinnerInterestedIn = view.findViewById(R.id.stsSpinnerInterestedIn);
        mStsSpinnerStatus = view.findViewById(R.id.stsSpinnerStatus);
        mStsSaveButton = view.findViewById(R.id.settingsBtnSave);
        mStsEmail = view.findViewById(R.id.stsEmail);
        mStsPhone = view.findViewById(R.id.stsPhone);


        mStsBackButton.setOnClickListener(this);
        mStsSaveButton.setOnClickListener(this);
        mStsCamera.setOnClickListener(this);
        mStsSaveButton.setOnClickListener(this);

        settingAdapters();
        setBackground();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mStsPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(Locale.getDefault().getCountry()));
        } else {
            mStsPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    s.replace(0, s.length(), PhoneNumberUtils.formatNumber(s.toString()));
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSavedPreferences();
    }

    private void getSavedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = preferences.getString(PreferenceKeys.NAME, "");
        mStsName.setText(name);

        String email = preferences.getString(PreferenceKeys.EMAIL, "");
        mStsEmail.setText(email);

        String phone = preferences.getString(PreferenceKeys.PHONE, "");
        mStsPhone.setText(phone);

        String gender = preferences.getString(PreferenceKeys.GENDER, "");
        String interestedIn = preferences.getString(PreferenceKeys.INTERESTED_IN, "");
        String status = preferences.getString(PreferenceKeys.RELATIONSHIP_STATUS, "");

    }

    private void settingAdapters() {
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Resources.GENDER);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStsSpinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> interestedInAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Resources.INTERESTED_IN);
        interestedInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStsSpinnerInterestedIn.setAdapter(interestedInAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Resources.STATUS);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStsSpinnerStatus.setAdapter(statusAdapter);
    }


    private void setBackground() {
        Glide.with(getActivity())
                .load(R.drawable.heart_background)
                .into(mStsBackground);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.stsBackButton) {
            mInterface.onBackPressed();
        }
        if (v.getId() == R.id.stsCamera) {

        }
        if (v.getId() == R.id.settingsBtnSave) {
            savePreferences();
        }
    }

    private void savePreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        String name = mStsName.getText().toString();
        String email = mStsEmail.getText().toString();
        String phone = mStsPhone.getText().toString();
        String selectedGender = mStsSpinnerGender.getSelectedItem().toString();
        String selectedInterestedIn = mStsSpinnerInterestedIn.getSelectedItem().toString();
        String selectedStatus = mStsSpinnerStatus.getSelectedItem().toString();

        if (!name.equals("")) {
            editor.putString(PreferenceKeys.NAME, name);
            editor.apply();
        } else {
            Toast.makeText(getActivity(), "Enter your name", Toast.LENGTH_SHORT).show();
        }

        if (!email.equals("")) {
            editor.putString(PreferenceKeys.EMAIL, email);
            editor.apply();
        } else {
            Toast.makeText(getActivity(), "Enter your email address", Toast.LENGTH_SHORT).show();
        }
        if (!phone.equals("")) {
            editor.putString(PreferenceKeys.PHONE, phone);
            editor.apply();
        } else {
            Toast.makeText(getActivity(), "Enter your phone number", Toast.LENGTH_SHORT).show();
        }


        editor.putString(PreferenceKeys.GENDER, selectedGender);
        editor.apply();

        editor.putString(PreferenceKeys.INTERESTED_IN, selectedInterestedIn);
        editor.apply();

        editor.putString(PreferenceKeys.RELATIONSHIP_STATUS, selectedStatus);
        editor.apply();

        mUsers.add( new User(
                Uri.parse("android.resource://com.joesoft.joesoftdating/" + R.drawable.mk).toString(),
                name, selectedGender, selectedInterestedIn, selectedStatus
        ));


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }
}
