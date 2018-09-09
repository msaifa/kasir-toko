package com.komputerkit.kasirtoko;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by msaifa on 11/01/2018.
 */

public class ActivityIntro extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance(getString(R.string.inJIntro1),getString(R.string.inDIntro1),R.drawable.intro1,Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.inJIntro2),getString(R.string.inDIntro2),R.drawable.intro2,Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.inJIntro3),getString(R.string.inDIntro3),R.drawable.intro3,Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.inJIntro4),getString(R.string.inDIntro4),R.drawable.intro4,Color.parseColor("#2196F3")));


        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(this,ActivityUtama.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
