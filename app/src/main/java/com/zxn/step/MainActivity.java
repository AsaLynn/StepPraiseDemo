package com.zxn.step;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by zxn on 2018-11-6 16:44:34.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rb_praise)
    RadioButton rbPraise;
    @BindView(R.id.rb_tread)
    RadioButton rbTread;
    @BindView(R.id.rg_evaluate)
    RadioGroup rgEvaluate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        rgEvaluate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            }
//        });
    }

    //@OnClick({R.id.rb_praise, R.id.rb_tread, R.id.rg_evaluate})
    @OnClick({R.id.btn, R.id.rb_praise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_praise:
                //rbPraise.setChecked(!rbPraise.isChecked());
//                if (rbPraise.isChecked()) {
//                    rgEvaluate.clearCheck();
//                } else {
//                    rgEvaluate.check(view.getId());
//                }
                break;
//            case R.id.rb_tread:
//                break;
            case R.id.btn:
                rgEvaluate.clearCheck();
                break;
        }
    }

//    @OnCheckedChanged
//    public void onViewChecked(View view) {
//
//    }

    //,R.id.rb_tread,
//    @OnCheckedChanged({R.id.rb_praise})
//    void onChecked(boolean checked) {
//        Toast.makeText(this, checked ? "Checked!" : "Unchecked!", Toast.LENGTH_SHORT).show();
//    }

//    @OnCheckedChanged(R.id.rg_evaluate)
//    void onCheckedChanged(RadioGroup group, int checkedId) {
//        Toast.makeText(this, checkedId + "", Toast.LENGTH_SHORT).show();
//    }

//    @OnCheckedChanged({R.id.rb_praise, R.id.rb_tread})
//    void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
//        switch (view.getId()) {
//            case R.id.rb_praise:
//                if (ischanged) {
//                    Toast.makeText(this, "赞了!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "取消赞了!", Toast.LENGTH_SHORT).show();
//                    //rgEvaluate.clearCheck();
//                }
//                break;
//            case R.id.rb_tread:
//                if (ischanged) {
//                    Toast.makeText(this, "踩了!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "取消踩了!", Toast.LENGTH_SHORT).show();
//                    //rgEvaluate.clearCheck();
//                }
//                break;
//            default:
//                break;
//        }
//    }

}
