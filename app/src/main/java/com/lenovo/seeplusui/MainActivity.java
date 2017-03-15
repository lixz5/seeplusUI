package com.lenovo.seeplusui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CheckBox languageCheckBox, lightingCheckBox;
    private TextView enText, cnText,resultText;
    private RadioGroup switchRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
        //在窗口变换的时候直接给resultText赋值
        /*resultText.setTranslationX(100);
        resultText.setTranslationY(200);*/
    }
    //不用
    private void moveTarget(final float pointX,final float pointY){
        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
        animation.setDuration(0);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                resultText.setTranslationX(pointX);
                resultText.setTranslationY(pointY);
            }
        });
        animation.start();
    }

    //Event monitoring method
    private void setListener() {
        languageCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    enText.setText(R.string.cn);
                    cnText.setText(R.string.en);
                    cnToen();
                } else {
                    enText.setText(R.string.en);
                    cnText.setText(R.string.cn);
                    enTocn();
                }
            }
        });
        lightingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    OpenLightOn();
                } else {
                    CloseLightOff();
                }
            }
        });

        switchRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.qrButton:
                        switchBtn(StyleBtn.QR);
                        break;
                    case R.id.translateButton:
                        switchBtn(StyleBtn.TRANSLATE);
                        break;
                    case R.id.imageButton:
                        switchBtn(StyleBtn.IMAGE);
                        break;
                }
            }
        });
    }

    public enum StyleBtn {
        QR, TRANSLATE, IMAGE;
    }
    //switch button change state
    private void switchBtn(StyleBtn style) {
        switch (style) {
            case QR:
                Toast.makeText(MainActivity.this, "rdoBtn_qr", Toast.LENGTH_LONG).show();
                break;
            case TRANSLATE:
                Toast.makeText(MainActivity.this, "rdoBtn_translate", Toast.LENGTH_LONG).show();
                break;
            case IMAGE:
                Toast.makeText(MainActivity.this, "rdoBtn_image", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initView() {
        languageCheckBox = (CheckBox) findViewById(R.id.languageCheckBox);
        lightingCheckBox = (CheckBox) findViewById(R.id.lightingCheckBox);
        enText = (TextView) findViewById(R.id.enText);
        cnText = (TextView) findViewById(R.id.cnText);
        resultText= (TextView) findViewById(R.id.resultText);
        switchRadioGroup = (RadioGroup) findViewById(R.id.switchRadioGroup);
    }

    private Camera m_Camera = null;

    //Open the camera flash to turn off the lights
    private void OpenLightOn() {
        if (null == m_Camera) {
            m_Camera = Camera.open();
        }

        Camera.Parameters parameters = m_Camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        m_Camera.setParameters(parameters);
        m_Camera.autoFocus(new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
            }
        });
        m_Camera.startPreview();
        Toast.makeText(this, "Open the camera flash to turn off the lights", Toast.LENGTH_LONG).show();
    }

    //Close the camera flash to turn off the lights
    private void CloseLightOff() {
        if (m_Camera != null) {
            m_Camera.stopPreview();
            m_Camera.release();
            m_Camera = null;
        }
        Toast.makeText(this, "Close the camera flash to turn off the lights", Toast.LENGTH_LONG).show();
    }

    //Chinese-English translation
    private void cnToen() {
        Toast.makeText(this, "cnToen", Toast.LENGTH_LONG).show();
    }

    //English-Chinese translation
    private void enTocn() {
        Toast.makeText(this, "enTocn", Toast.LENGTH_LONG).show();
    }
}
