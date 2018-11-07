package com.zxn.step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zxn.step.view.EvaluateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zxn on 2018-11-6 19:51:57.
 */
public class UnCheckActivity extends AppCompatActivity {

    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.cv_comment)
    EvaluateView cvComment;
    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    private String mParam1;
    private String TAG = "UnCheckActivity";

    public static void jumpTo(Context context, String param1) {
        Intent intent = new Intent(context, UnCheckActivity.class);
        intent.putExtra(ARG_PARAM1, param1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_check);
        ButterKnife.bind(this);
        mParam1 = getIntent().getStringExtra(ARG_PARAM1);

        cvComment.setOnEvaluateClickListener(new EvaluateView.OnEvaluateClickListener() {
            @Override
            public void commentClick(boolean isPraising, boolean isChecked) {
//                String str;
//                if (isPraising) {
//                    str = isChecked ? "" : "";
//                } else {
//                    str = isChecked ? "" : "";
//                }
                String str
                        = isPraising
                        ? (isChecked ? "被赞了啊" : "取消赞了")
                        : (isChecked ? "被踩了啊" : "取消踩了");
                Log.i(TAG, "==>commentClick: " + str);
            }
        });
    }


    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn0:
                cvComment.clearCheck();
                break;
            case R.id.btn1:
                cvComment.showEvaluationResult(EvaluateView.EVALUATED_PRAISED);
                break;
            case R.id.btn2:
                cvComment.showEvaluationResult(EvaluateView.EVALUATED_TRODDEN);
                break;
            case R.id.btn3:
                cvComment.showEvaluationResult(EvaluateView.EVALUATED_NONE);
                break;
            case R.id.btn4:
                cvComment.setEvaluateEnabled(false);
                break;
        }
    }
}
