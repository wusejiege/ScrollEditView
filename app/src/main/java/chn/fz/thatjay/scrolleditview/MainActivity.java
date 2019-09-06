package chn.fz.thatjay.scrolleditview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import chn.fz.thatjay.scrolleditview.view.ScrollMulrowsEditText;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.edittext1)
    ScrollMulrowsEditText edittext1;

    @BindView(R.id.text1)
    TextView text1;

    @BindView(R.id.edittext2)
    ScrollMulrowsEditText edittext2;

    @BindView(R.id.text2)
    TextView text2;

    @BindView(R.id.edittext3)
    ScrollMulrowsEditText edittext3;

    @BindView(R.id.text3)
    TextView text3;

    @BindView(R.id.edittext4)
    ScrollMulrowsEditText edittext4;

    @BindView(R.id.text4)
    TextView text4;

    @BindView(R.id.edittext5)
    ScrollMulrowsEditText edittext5;

    @BindView(R.id.text5)
    TextView text5;

    private final int MAX_TEXT_SIZE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initStatusBar(edittext1, edittext2, edittext3, edittext4, edittext5);
        addTextChangedListener(edittext1, text1);
        addTextChangedListener(edittext2, text2);
        addTextChangedListener(edittext3, text3);
        addTextChangedListener(edittext4, text4);
        addTextChangedListener(edittext5, text5);
        text1.setText(edittext1.length() + "/" + MAX_TEXT_SIZE);
        text2.setText(edittext2.length() + "/" + MAX_TEXT_SIZE);
        text3.setText(edittext3.length() + "/" + MAX_TEXT_SIZE);
        text4.setText(edittext4.length() + "/" + MAX_TEXT_SIZE);
        text5.setText(edittext5.length() + "/" + MAX_TEXT_SIZE);

    }

    private void addTextChangedListener(EditText editText, final TextView numTv){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                numTv.setText(s.length() + "/" + MAX_TEXT_SIZE);
            }
        });
    }

    public void initStatusBar(final EditText... ets){
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color1)
                .keyboardEnable(true)
                .setOnKeyboardListener(new OnKeyboardListener() {
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        if (!isPopup) {
                            for (EditText et:ets
                            ) {
                                et.clearFocus();
                            }
                        }
                    }
                })
                .init();
    }
}
