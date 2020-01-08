package com.laoxu.a1707bcartdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.laoxu.a1707bcartdemo.R;

import org.greenrobot.eventbus.EventBus;

public class NumLayout extends LinearLayout {
    private TextView jiaTv, jianTv, numTv;
    private int num = 1;

    public NumLayout(Context context) {
        super(context);
        initView();
    }

    public NumLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NumLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = View.inflate(getContext(), R.layout.num_layout, null);


        addView(view);//添加子控件到当前容器

        jianTv = view.findViewById(R.id.jian);
        jiaTv = view.findViewById(R.id.jia);
        numTv = view.findViewById(R.id.num);

        jianTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                if (num < 1) {
                    Toast.makeText(getContext(), "数量不能小于1", Toast.LENGTH_SHORT).show();
                    num = 1;
                }
                numTv.setText(num + "");
                numCallback.numClick(num);
            }

        });

        jiaTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                numTv.setText(num + "");
                numCallback.numClick(num);
            }
        });
    }
    private NumCallback numCallback;

    public void setNumCallback(NumCallback numCallback) {
        this.numCallback = numCallback;
    }

    public interface NumCallback{
        void numClick(int num);
    }
}
