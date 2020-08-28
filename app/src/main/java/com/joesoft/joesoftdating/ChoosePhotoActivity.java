package com.joesoft.joesoftdating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChoosePhotoActivity extends AppCompatActivity {
    private static final String TAG = "ChoosePhotoActivity";
    private static final int GALLERY_FRAGMENT = 0;
    private static final int PHOTO_FRAGMENT = 1;
    // fragments
    private  GalleryFragment mGalleryFragment;
    private PhotoFragment mPhotoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
    }
}
