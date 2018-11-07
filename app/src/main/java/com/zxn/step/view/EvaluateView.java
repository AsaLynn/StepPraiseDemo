package com.zxn.step.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.zxn.step.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * 评价
 * 1,踩或者赞,Evaluate
 * 2,可以取消点踩或取消点赞
 * 3,踩和赞功能互斥(踩了之后就不能赞,赞了之后就不能踩)
 * 4,区分手动点踩或赞和已经踩和赞的状态展示
 * 5,手动点击评价才响应监听,否则不响应监听
 *
 * <p>
 * Created by zxn on 2018/11/6.
 */
public class EvaluateView extends LinearLayout {

    @BindView(R.id.cb_praise)
    CheckBox cbPraise;
    @BindView(R.id.cb_tread)
    CheckBox cbTread;
    private CompoundButton mCurrentPressedCb;

    //是否为手动点击评价
    private boolean mIsClickEvaluate = true;

    //取消点赞次数
    //取消点踩次数

    //踩和赞之间距离
    int mSpace = 0;
    private int mHorizontalPaddingSpace;
    private int mCheckPadding;
    private float mCheckTextSize;
    private int mCheckTextColor;

    public EvaluateView(Context context) {
        this(context, null);
    }

    public EvaluateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public EvaluateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        LayoutInflater
                .from(getContext())
                .inflate(R.layout.layout_comment_view, this, true);
        ButterKnife.bind(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EvaluateView);

        mCheckPadding = typedArray.getDimensionPixelSize(R.styleable.EvaluateView_checkPadding, mCheckPadding);
        cbPraise.setPadding(mCheckPadding, mCheckPadding, mCheckPadding, mCheckPadding);
        cbTread.setPadding(mCheckPadding, mCheckPadding, mCheckPadding, mCheckPadding);

        mSpace = typedArray.getDimensionPixelSize(R.styleable.EvaluateView_marginSpace, mSpace);
        LinearLayout.LayoutParams params = (LayoutParams) cbTread.getLayoutParams();
        int leftMargin = mSpace - mCheckPadding * 2;
        params.leftMargin = leftMargin < 0 ? mCheckPadding * 2 : leftMargin;
        cbTread.setLayoutParams(params);

        mCheckTextSize = typedArray.getDimension(R.styleable.EvaluateView_checkTextSize, 0);
        if (mCheckTextSize > 0) {
            cbPraise.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCheckTextSize);
            cbTread.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCheckTextSize);
        }

        //ColorStateList colors = a.getColorStateList(R.styleable.TextView_textColor);
        // mTextColor = ColorStateList.valueOf(color);
        //mCheckTextColor = typedArray.getColor(R.styleable.EvaluateView_checkTextColor, 0);
        ColorStateList colorStateList = typedArray.getColorStateList(R.styleable.EvaluateView_checkTextColors);
        if (null != colorStateList) {
            cbPraise.setTextColor(colorStateList);
            cbTread.setTextColor(colorStateList);
        }

        Drawable mActiveDrawables = typedArray.getDrawable(R.styleable.EvaluateView_checkActiveDrawables);
        if (null != mActiveDrawables) {
            //cbPraise.setCompoundDrawables(mCheckDrawables, null, null, null);
            cbPraise.setCompoundDrawablesWithIntrinsicBounds(mActiveDrawables, null, null, null);
        }

        Drawable mPassiveDrawables = typedArray.getDrawable(R.styleable.EvaluateView_checkPassiveDrawables);
        if (null != mPassiveDrawables) {
            //cbPraise.setCompoundDrawables(mCheckDrawables, null, null, null);
            cbTread.setCompoundDrawablesWithIntrinsicBounds(mPassiveDrawables, null, null, null);
        }
        typedArray.recycle();
    }

    @OnCheckedChanged({R.id.cb_praise, R.id.cb_tread})
    void OnCheckedChangeListener(CompoundButton view, boolean isChanged) {
        mCurrentPressedCb = view;
        switch (view.getId()) {
            case R.id.cb_praise:
                cbTread.setEnabled(!isChanged);
                if (mIsClickEvaluate) {
                    mOnEvaluateClickListener.commentClick(true, isChanged);
                }
                break;
            case R.id.cb_tread:
                cbPraise.setEnabled(!isChanged);
                if (mIsClickEvaluate) {
                    mOnEvaluateClickListener.commentClick(false, isChanged);
                }
                break;
        }
    }

    /**
     * 清楚选中状态
     */
    public void clearCheck() {
        mIsClickEvaluate = false;
        cbPraise.setChecked(false);
        cbTread.setChecked(false);
        mIsClickEvaluate = true;
    }

    public interface OnEvaluateClickListener {
        /**
         * 评价回调
         *
         * @param isPraising true:赞,false:踩
         * @param isChecked  true:选中,false:取消
         */
        void commentClick(boolean isPraising, boolean isChecked);
    }

    private OnEvaluateClickListener mOnEvaluateClickListener;

    /**
     * 评价监听
     *
     * @param listener
     */
    public void setOnEvaluateClickListener(OnEvaluateClickListener listener) {
        this.mOnEvaluateClickListener = listener;
    }

    /**
     * 设置是否可点击评价
     *
     * @param enabled true:可以再次点击,false:不可点击评价
     */
    public void setEvaluateEnabled(boolean enabled) {
        cbPraise.setEnabled(enabled);
        cbTread.setEnabled(enabled);
    }

    /**
     * 设置刚选中的选项复原,用于接口请求失败,或者网络错误,不能响应评价监听
     */
    public void setCheckedRestore() {
        mIsClickEvaluate = false;
        mCurrentPressedCb.setChecked(!mCurrentPressedCb.isChecked());
        mIsClickEvaluate = true;
    }


    /**
     * 踩,赞,无评价等状态展示,不响应评价监听
     *
     * @param evaluated One of {@link #EVALUATED_PRAISED}, {@link #EVALUATED_TRODDEN}, or {@link #EVALUATED_NONE}.
     */
    public void showEvaluationResult(@EvaluationState int evaluated) {
        mIsClickEvaluate = false;
        if (evaluated == EVALUATED_PRAISED) {
            cbPraise.setChecked(true);
            cbTread.setChecked(false);
        } else if (evaluated == EVALUATED_TRODDEN) {
            cbPraise.setChecked(false);
            cbTread.setChecked(true);
        } else {
            cbPraise.setChecked(false);
            cbTread.setChecked(false);
        }
        mIsClickEvaluate = true;
    }

    /**
     * 无评价
     * (结合使用方法)Use with {@link #showEvaluationResult}
     */
    public static final int EVALUATED_NONE = 0;
    /**
     * 被赞
     * (结合使用方法)Use with {@link #showEvaluationResult}
     */
    public static final int EVALUATED_PRAISED = 1;
    /**
     * 被踩
     * (结合使用方法)Use with {@link #showEvaluationResult}
     */
    public static final int EVALUATED_TRODDEN = 2;

    @IntDef({EVALUATED_NONE, EVALUATED_PRAISED, EVALUATED_TRODDEN})
    public @interface EvaluationState {
    }

}
