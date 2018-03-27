package com.lyc.study.go014;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.lyc.study.R;

/**
 * Created by lyc on 17/5/15.
 */

public class GoAniActivity extends Activity {
    private Button btn_sleep_go,btn_mult,btn_keyframe,btn_tween;
    private View iv_target_01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ani);
        initGift();
        initViews();
    }

    private void initViews() {
        btn_sleep_go= (Button) findViewById(R.id.btn_sleep_go);

        btn_sleep_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator  ani_y= ObjectAnimator.ofFloat(iv_target_01,"translationY",0,50);
                ObjectAnimator  ani_x= ObjectAnimator.ofFloat(iv_target_01,"translationX",0,100);
                ani_y.setDuration(1000);
                ani_x.setDuration(2000);
                ValueAnimator pause =ValueAnimator.ofInt(0,100);
                //设置一个空白animator  专门用于停止一段时间
                pause.setDuration(3000);
                AnimatorSet set = new AnimatorSet();
                set.playSequentially(ani_y,pause,ani_x);
                set.setDuration(6000);
                set.start();
            }
        });

        btn_mult= (Button) findViewById(R.id.btn_mult);
        btn_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder vh_y=PropertyValuesHolder.ofFloat("translationY",0f,50f);
                PropertyValuesHolder vh_x=PropertyValuesHolder.ofFloat("translationX",0f,50f);
                PropertyValuesHolder vh_alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f,0f, 1.0F);
                ObjectAnimator animator =ObjectAnimator.ofPropertyValuesHolder(iv_target_01,vh_y,vh_x,vh_alpha);
                animator.setDuration(2000);

                animator.start();
            }
        });


        btn_keyframe= (Button) findViewById(R.id.btn_keyframe);
        btn_keyframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyframe keyframe = Keyframe.ofFloat(0, 0);
                keyframe.setInterpolator(new LinearInterpolator());
                Keyframe keyframe1 = keyframe.ofFloat(0.1f, 30);
                keyframe1.setInterpolator(new LinearInterpolator());
                //两个设置为相同的值就会在这里停一下  停大约  0.8*总时间
                Keyframe keyframe2 = keyframe.ofFloat(0.9f, 30);
                keyframe2.setInterpolator(new LinearInterpolator());

                Keyframe keyframe5 = keyframe.ofFloat(1, 200);
                keyframe5.setInterpolator(new LinearInterpolator());

                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe,
                                keyframe1,keyframe2,keyframe5);
                ObjectAnimator.ofPropertyValuesHolder(iv_target_01,
                        propertyValuesHolder).setDuration(3000).start();
            }
        });

        btn_tween = (Button) findViewById(R.id.btn_tween);
        btn_tween.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation ani = new TranslateAnimation(0, 200, 0, 50);
                AlphaAnimation alphaAnimation= new AlphaAnimation(1,0f);
                AnimationSet set =new AnimationSet(true);
                set.addAnimation(ani);
                set.addAnimation(alphaAnimation);
                set.setDuration(1200);
                set.setFillAfter(true);//不设置的话  会回到原位置


                ScaleAnimation alphaAnimation2= new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f);
                alphaAnimation2.setDuration(600);
                AnimationSet total =new AnimationSet(true);
                total.addAnimation(set);
                total.addAnimation(alphaAnimation2);
                total.start();
                total.setFillAfter(true);

                total.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        AlphaAnimation alphaIn= new AlphaAnimation(0,1f);
                        alphaIn.setDuration(600);
                        alphaIn.setFillAfter(true);
                        iv_target_01.startAnimation(alphaIn);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


                iv_target_01.startAnimation(total);


            }
        });

        iv_target_01= findViewById(R.id.iv_target_01);
    }

    private void initGift(){
        GiftFrameLayout giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        GiftFrameLayout giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);

        final GiftControl giftControl = new GiftControl(giftFrameLayout1, giftFrameLayout2);

        findViewById(R.id.btn_gift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GiftModel giftModel = new GiftModel("菊花", "礼物名字", 1, "", "1234", "吕靓茜", "", System.currentTimeMillis());
                giftControl.loadGift(giftModel);
            }
        });
    }
}
