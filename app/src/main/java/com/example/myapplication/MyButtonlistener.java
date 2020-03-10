package com.example.myapplication;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyButtonlistener extends Activity implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        Button b=(Button)v;
        String buttonText=b.getText().toString();
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText("点击了采用"+buttonText+"的监听器");
    }
}
