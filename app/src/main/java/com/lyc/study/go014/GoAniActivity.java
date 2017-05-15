package com.lyc.study.go014;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lyc.study.R;

/**
 * Created by lyc on 17/5/15.
 */

public class GoAniActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ani);


        initGift();
    }

    private void initGift(){
        GiftFrameLayout giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        GiftFrameLayout giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);

        GiftControl giftControl = new GiftControl(giftFrameLayout1, giftFrameLayout2);

        findViewById(R.id.btn_gift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GiftModel giftModel = new GiftModel("菊花", "礼物名字", 1, "", "1234", "吕靓茜", "", System.currentTimeMillis());
                giftControl.loadGift(giftModel);
            }
        });
    }
}
