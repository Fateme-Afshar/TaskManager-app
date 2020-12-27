package com.example.taskmaneger.view.anim;

import android.animation.ObjectAnimator;
import android.view.View;

public class ButtonAnimation {

    public static void upAnim(View view){
        ObjectAnimator animator=ObjectAnimator.
                ofFloat(view,"y",view.getTop(),2).
                setDuration(1000);
        animator.start();
    }
}
