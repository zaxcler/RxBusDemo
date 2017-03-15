package com.zaxcler.rxbusdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zaxcler.rxbuslib.RxBus;
import com.zaxcler.rxbuslib.RxMessage;

public class MainActivity extends AppCompatActivity {
    DemoDialog demoDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoDialog = new DemoDialog(MainActivity.this);
                demoDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RxBus.getInstance().post(new RxMessage());
                    }
                }, 3000);

            }
        });

    }
}
