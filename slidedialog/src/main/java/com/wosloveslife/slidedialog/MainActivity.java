package com.wosloveslife.slidedialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SlideDialogLayout mSlideDialogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideDialogLayout = (SlideDialogLayout) findViewById(R.id.id_sdl_spread);
        mSlideDialogLayout.setEdgeTrackingEnabled(true);
        mSlideDialogLayout.addOnFormChangeListener(new SlideDialogLayout.OnFormChangeListener() {
            @Override
            public void onSlide(int form) {
                if (form == SlideDialogLayout.FORM_FOLD) {
                }
            }
        });

        Button changeForm = (Button) findViewById(R.id.id_btn_change);
        changeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int spreadState = SlideDialogLayout.FORM_PART;
                switch (mSlideDialogLayout.getSpreadState()) {
                    case SlideDialogLayout.FORM_PART:
                        spreadState = SlideDialogLayout.FORM_COMP;
                        break;
                    case SlideDialogLayout.FORM_COMP:
                        spreadState = SlideDialogLayout.FORM_FOLD;
                        break;
                    case SlideDialogLayout.FORM_FOLD:
                        spreadState = SlideDialogLayout.FORM_PART;
                        break;
                }
                mSlideDialogLayout.controlSpread(spreadState);
            }
        });

        CheckBox edgeTouch = (CheckBox) findViewById(R.id.id_cb_edge_enable);
        edgeTouch.setChecked(mSlideDialogLayout.getEdgeTrackingEnabled());
        edgeTouch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSlideDialogLayout.setEdgeTrackingEnabled(isChecked);
            }
        });

        CheckBox mAutoDismiss = (CheckBox) findViewById(R.id.id_cb_auto_dismiss);
        mAutoDismiss.setChecked(mSlideDialogLayout.getAutoDismissEnable());
        mAutoDismiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSlideDialogLayout.setAutoDismissEnable(isChecked);
            }
        });

        Button button = (Button) findViewById(R.id.id_btn_toast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
