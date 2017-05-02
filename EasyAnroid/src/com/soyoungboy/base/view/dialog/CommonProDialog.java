package com.soyoungboy.base.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import com.soyoungboy.base.R;
import com.soyoungboy.base.view.LoadingView;

/**
 * @author soyoungboy
 * @ClassName: CommonProDialog
 * @Description: 等待提示对话框
 * @date 2014-6-17 下午5:47:10
 */
public class CommonProDialog extends Dialog {
    protected LoadingView view;
    protected Context context;
    private String msg;


    public CommonProDialog(Context context) {
        super(context, R.style.commonDialog);
        this.context = context;
        msg = "正在加载...";
    }


    public CommonProDialog(Context context, String msg) {
        super(context, R.style.commonDialog);
        this.context = context;
        this.msg = msg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = new LoadingView(context);
        setContentView(view);
        view.getLocalTextView().setText(msg);
    }


    @Override
    public void show() {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            super.show();
        }
    }
}
