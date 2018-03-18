package com.lyc.study.go020;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.lyc.study.R;

/**
 * Created by sunfusheng on 16/5/31.
 */
public class MarqueeTextView extends ViewFlipper {


    private int textSize = 14;
    private int textColor = 0xff000000;
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    private long duration = 250L;

    @AnimRes
    private int inAnimResId = R.anim.anim_bottom_in;
    @AnimRes
    private int outAnimResId = R.anim.anim_top_out;

    private TextView[] items = new TextView[2];

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);


        inAnimResId = R.anim.anim_bottom_in;
        outAnimResId = R.anim.anim_top_out;
        items[0] = createTextView(0);
        items[1] = createTextView(1);

        setInAndOutAnimation(inAnimResId, outAnimResId);
        typedArray.recycle();
        addView(items[0]);
        addView(items[1]);

        setAutoStart(false);
    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(duration);
        setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(duration);
        setOutAnimation(outAnim);
    }

    public void setNextItem(String next) {
        if (getCurrentView() instanceof TextView) {
            int index = (int) ((TextView) getCurrentView()).getTag();
            items[1 - index].setText(next);
            showNext();
        }
    }

    public void setCurrentItem(String currentText) {
        if (getCurrentView() instanceof TextView) {
            ((TextView) getCurrentView()).setText(currentText);
        }
    }

    private TextView createTextView(int position) {
        TextView textView = new TextView(getContext());
        textView.setGravity(gravity);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setSingleLine(true);
        textView.setTag(position);
        return textView;
    }
}
