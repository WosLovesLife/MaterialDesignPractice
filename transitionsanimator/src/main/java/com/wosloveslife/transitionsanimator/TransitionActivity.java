package com.wosloveslife.transitionsanimator;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YesingBeijing on 2016/8/4.
 */
public class TransitionActivity extends AppCompatActivity {
    private static final String TAG = "TransitionActivity";

    @ColorInt
    int[] PLACE_HOLDER = new int[]{
            R.color.placeHolder1, R.color.placeHolder2, R.color.placeHolder3
            , R.color.placeHolder4, R.color.placeHolder5, R.color.placeHolder6
            , R.color.placeHolder7, R.color.placeHolder8, R.color.placeHolder9
            , R.color.placeHolder10, R.color.placeHolder11, R.color.placeHolder12
            , R.color.placeHolder13, R.color.placeHolder14};

    @BindView(R.id.transition_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter();
        mRecyclerView.setAdapter(adapter);
        List<String> pictureUriStrings = Arrays.asList(Images.imageThumbUrls);
        adapter.setData(pictureUriStrings);
    }

    class Adapter extends BaseRecyclerViewAdapter<String> {
        @Override
        protected BaseRecyclerViewHolder<String> onCreateItemViewHolder(ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_item, parent, false);

            return new Holder(view);
        }
    }

    class Holder extends BaseRecyclerViewHolder<String>{

        private ImageView mImageView;

        public Holder(View itemView) {
            super(itemView);
        }

        @Override
        public void onCreateView(View view) {
            mImageView = (ImageView) itemView.findViewById(R.id.iv_list_item);
        }

        @Override
        public void onBind(final String data) {
                    /* 根据uri进行网络请求 */
            Glide.with(getContext())
                    .load(data)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(PLACE_HOLDER[new Random().nextInt(PLACE_HOLDER.length)])  // PLACE_HOLDER[new Random().nextInt(PLACE_HOLDER.length)]
                    .into(mImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            /* 做跳转 跳转到详情页面 做转场动画 */
                    Pair sharedImageView = new Pair<View, String>(mImageView, ViewCompat.getTransitionName(mImageView));
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(TransitionActivity.this, sharedImageView);

                    Intent intent;
                    try {
                        intent = TargetActivity.getIntent(TransitionActivity.this, ((BitmapDrawable) mImageView.getDrawable()).getBitmap(), Uri.parse(data));
                        Log.w(TAG, "onClick: bitmap" );
                    } catch (ClassCastException e) {
                        intent = TargetActivity.getIntent(TransitionActivity.this, ((ColorDrawable) mImageView.getDrawable()).getColor(), Uri.parse(data));
                        Log.w(TAG, "onClick: color" );
                    }

                    startActivity(intent, activityOptionsCompat.toBundle());

//                    TransitionManager.beginDelayedTransition(mRecyclerView, new Fade());
//                    toggleVisibility(mRecyclerView);
                }
            });
        }
    };

    /** 调用TransitionManager后调用该方法,系统会自动根据View的Visibility状态而决定动画的应用 */
    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
