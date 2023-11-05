package com.demo.collegeerp.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.collegeerp.R;

public class CustomProgressDialog extends Dialog {
    TextView tv_message;
    private final Context mContext;
    private final String message;

    public CustomProgressDialog(Context mContext, String message) {
        super(mContext);
        this.mContext = mContext;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialoge_layout);

         tv_message=findViewById(R.id.tv_message);

        // Update the text in the TextView with the given message
        tv_message.setText(message);
        // tv_message.setText(message); // You can use this line if you want to set the message provided in the constructor

        setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());

        lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.90);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    public void setMessage(String message) {
        tv_message.setText(message);
    }
}
