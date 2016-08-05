package com.wosloveslife.transitionsanimator;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.id_btn_fade)
    Button mIdBtnFade;
    @BindView(R.id.id_btn_slide)
    Button mIdBtnSlide;
    @BindView(R.id.id_btn_explode)
    Button mIdBtnExplode;
    @BindView(R.id.id_btn_red_box)
    Button mIdBtnRedBox;
    @BindView(R.id.id_btn_blue_box)
    Button mIdBtnBlueBox;
    @BindView(R.id.id_btn_green_box)
    Button mIdBtnGreenBox;
    @BindView(R.id.id_btn_purple_box)
    Button mIdBtnPurpleBox;
    @BindView(R.id.id_rl_root_view)
    RelativeLayout mIdRlRootView;
    @BindView(R.id.floating_reveal_red)
    Button mFloatingRevealRed;
    @BindView(R.id.floating_reveal_blue)
    Button mFloatingRevealBlue;
    @BindView(R.id.floating_reveal_green)
    Button mFloatingRevealGreen;
    @BindView(R.id.floating_reveal_purple)
    Button mFloatingRevealPurple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Activity/Fragment转场必须的特性 */
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.id_btn_fade, R.id.id_btn_slide, R.id.id_btn_explode, R.id.floating_reveal_red, R.id.floating_reveal_blue, R.id.floating_reveal_green, R.id.floating_reveal_purple})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_fade:
                TransitionManager.beginDelayedTransition(mIdRlRootView, new Fade());
                toggleVisibility(mIdBtnRedBox, mIdBtnBlueBox, mIdBtnGreenBox, mIdBtnPurpleBox);
                break;
            case R.id.id_btn_slide:
                TransitionManager.beginDelayedTransition(mIdRlRootView, new Slide());
                toggleVisibility(mIdBtnRedBox, mIdBtnBlueBox, mIdBtnGreenBox, mIdBtnPurpleBox);
                break;
            case R.id.id_btn_explode:
                TransitionManager.beginDelayedTransition(mIdRlRootView, new Explode());
                toggleVisibility(mIdBtnRedBox, mIdBtnBlueBox, mIdBtnGreenBox, mIdBtnPurpleBox);
                break;
            case R.id.floating_reveal_red:
                toAnimateReveal(mFloatingRevealRed, R.color.placeHolder2);
                break;
            case R.id.floating_reveal_blue:
                toAnimateReveal(mFloatingRevealBlue, R.color.placeHolder10);
                break;
            case R.id.floating_reveal_green:
                toAnimateReveal(mFloatingRevealGreen, R.color.placeHolder14);
                break;
            case R.id.floating_reveal_purple:
                toAnimateReveal(mFloatingRevealPurple, R.color.placeHolder1);
                break;
        }
    }

    /** 调用TransitionManager后调用该方法,系统会自动根据View的Visibility状态而决定动画的应用 */
    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private void toAnimateReveal(View v, @ColorRes int color) {
        int left = v.getLeft();
        int top = v.getTop();
        int x = left + ((v.getRight() - left) / 2);
        int y = top + ((v.getBottom() - top) / 2);
        Log.w(TAG, "onClick: x = " + x + "; y = " + y);
        animateRevealColorFromCoordinates(mIdRlRootView, color, x, y);
    }

    /**
     * 根据坐标点做转场动画
     *
     * @param viewRoot 包含主要空间的rootView
     * @param color    变化的颜色
     * @param x        坐标x
     * @param y        坐标y
     * @return
     */
    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        /* 影响的范围 */
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(400);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }
}
