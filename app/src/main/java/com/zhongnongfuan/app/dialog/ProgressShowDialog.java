package com.zhongnongfuan.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.zhongnongfuan.app.R;

/**
 * @author qichaoqun
 * @date 2019/1/20
 */
public class ProgressShowDialog extends Dialog {
    public ProgressShowDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog_layout);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.getWindow().setDimAmount(0f);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }
}
