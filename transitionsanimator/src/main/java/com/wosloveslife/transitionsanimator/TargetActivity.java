package com.wosloveslife.transitionsanimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YesingBeijing on 2016/7/21.
 */
public class TargetActivity extends AppCompatActivity {
    private static final String TAG = "TargetActivity";

    @BindView(R.id.shared_transition_image_view)
    ImageView mImageView;

    public static Intent getIntent(Context context, Bitmap bitmap, Uri uri) {
        Intent intent = new Intent(context, TargetActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("bitmap", bitmap);
        intent.putExtra("uri", uri);
        return intent;
    }

    public static Intent getIntent(Context context, int color, Uri uri) {
        Intent intent = new Intent(context, TargetActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("color", color);
        intent.putExtra("uri", uri);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            int type =  intent.getIntExtra("type", -1);

            if (type == 0){
                mImageView.setImageBitmap((Bitmap) getIntent().getParcelableExtra("bitmap"));
            }else {
                getIntent().getIntExtra("color",0);
            }
        }

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.text_detail);
        slide.addTarget(R.id.text_close);
        slide.addTarget(R.id.view_separator);
        getWindow().setEnterTransition(slide);
    }

    @OnClick(R.id.text_close)
    public void onCloseClicked() {
        finishAfterTransition();
    }
}
