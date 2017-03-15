package com.zaxcler.rxbusdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.zaxcler.rxbuslib.RxBus;
import com.zaxcler.rxbuslib.RxMessage;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zaxcler on 2017/3/15.
 */

public class DemoDialog extends AlertDialog implements DialogInterface.OnDismissListener{
    public DemoDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }


    Subscription subscription;

    protected DemoDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected DemoDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private void initView(final Context context) {
        Button textView = new Button(context);

        setCustomTitle(textView);
        subscription = RxBus.getInstance().toObserverable(RxMessage.class).subscribe(new Action1<RxMessage>() {
            @Override
            public void call(RxMessage rxMessage) {
                Toast.makeText(context,"毁掉",Toast.LENGTH_SHORT).show();
            }
        });
        setOnDismissListener(this);
    }



    @Override
    public void show() {
        super.show();
        double width = 300;
        WindowManager.LayoutParams wml = getWindow().getAttributes();
        wml.width = (int) width;
        getWindow().setAttributes(wml);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
       if (!subscription.isUnsubscribed()){
           subscription.unsubscribe();
       }
    }
}
