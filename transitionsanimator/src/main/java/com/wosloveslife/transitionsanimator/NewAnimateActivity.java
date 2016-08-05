package com.wosloveslife.transitionsanimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YesingBeijing on 2016/8/4.
 */
public class NewAnimateActivity extends AppCompatActivity {


    @BindView(R.id.btn_animate_fade_slide_explode)
    Button mBtnAnimateFadeSlideExplode;
    @BindView(R.id.btn_transition_share_element)
    Button mBtnTransitionShareElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        /* Activity/Fragment转场必须的特性 */
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_new_animate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_animate_fade_slide_explode, R.id.btn_transition_share_element})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_animate_fade_slide_explode:
                enterActivity(NormalActivity.class);
                break;
            case R.id.btn_transition_share_element:
                enterActivity(TransitionActivity.class);
                break;
        }
    }

    public void enterActivity(Class targetActivity) {
        Intent i = new Intent(this, targetActivity);
        startActivity(i);
    }
}
