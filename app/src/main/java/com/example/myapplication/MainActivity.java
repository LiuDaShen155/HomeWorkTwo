package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 该程序模拟填充长度为100的数组
    private int[] data = new int[100];
    int hasData = 0;
    // 定义进度对话框的标识
    final int PROGRESS_DIALOG = 0x112;
    // 记录进度对话框的完成百分比
    int progressStatus = 0;
    ProgressDialog pd;
    // 定义一个负责更新的进度的Handler
    Handler handler;

    @Override
    public Dialog onCreateDialog(int id, Bundle status) {
        System.out.println("------create------");
        switch (id) {
            case PROGRESS_DIALOG:
                // 创建进度对话框
                pd = new ProgressDialog(this);
                pd.setMax(100);
                // 设置对话框的标题
                pd.setTitle("任务完成百分比");
                // 设置对话框 显示的内容
                pd.setMessage("耗时任务的完成百分比");
                // 设置对话框不能用“取消”按钮关闭
                pd.setCancelable(false);
                // 设置对话框的进度条风格
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // 设置对话框的进度条是否显示进度
                pd.setIndeterminate(false);
                break;
        }
        return pd;
    }
    // 该方法将在onCreateDialog方法调用之后被回调
    @Override
    public void onPrepareDialog(int id, Dialog dialog) {
        System.out.println("------prepare------");
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case PROGRESS_DIALOG:
                // 对话框进度清零
                pd.incrementProgressBy(-pd.getProgress());
                new Thread() {
                    public void run() {
                        while (progressStatus < 100) {
                            // 获取耗时操作的完成百分比
                            progressStatus = doWork();
                            // 发送消息到Handler，请补全代码
                            Message message=handler.obtainMessage();
                            message.what=0;
                            message.arg1=progressStatus;
                            handler.sendMessage(message);
                        }
                        // 如果任务已经完成
                        if (progressStatus >= 100) {
                            // 关闭对话框
                            pd.dismiss();
                        }
                    }
                }.start();
                break;
        }
    }
    // 模拟一个耗时的操作。
    public int doWork() {
        // 为数组元素赋值
        data[hasData++] = (int) (Math.random() * 100);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasData;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        //匿名内部类
        Button btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Button b=(Button)view;
                String buttonText=b.getText().toString();
                TextView textView=(TextView)findViewById(R.id.textView);
                textView.setText("点击了采用"+buttonText+"的监听器");
            }
        });

        //内部类
        Button btn3=(Button)findViewById(R.id.button4);
        MyButtonlistener myButtonlistener=new MyButtonlistener();
        btn3.setOnClickListener(myButtonlistener);

        //外部类
        Button btn5=(Button)findViewById(R.id.button5);
        MyButtonlistener myButtonlistener1=new MyButtonlistener();
        btn5.setOnClickListener(myButtonlistener1);


        Button btn7=(Button)findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ConfigurationTest.class);
                startActivity(intent);
            }
        });

        Button execBn = (Button) findViewById(R.id.button8);
        execBn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View source) {
                showDialog(PROGRESS_DIALOG);//显示进度对话框
            }
        });
        //Handler消息处理，请补全代码，是多行。
        handler=new Handler(){
            public void handleMessage(Message msg){
                //处理传过的信息
                switch (msg.what){
                    case 0:
                        pd.incrementProgressBy(msg.arg1);//进度每次加1
                        if(msg.arg1>=100){
                            Toast.makeText(MainActivity.this,"下载完成！",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                }

                super.handleMessage(msg);
            }
        };
    }
    //Activity本身||绑定到标签
    @Override
    public void onClick(View v) {
        Button b=(Button)v;
        String buttonText=b.getText().toString();
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText("点击了采用"+buttonText+"的监听器");
    }


    //内部类
    class MyButtonlistener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Button b=(Button)v;
            String buttonText=b.getText().toString();
            TextView textView=(TextView)findViewById(R.id.textView);
            textView.setText("点击了采用"+buttonText+"的监听器");
        }
    }

    @OnClick(R.id.button6)
    public void ButtonClick(View view){
        Button b=(Button)view;
        String buttonText=b.getText().toString();
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText("点击了采用"+buttonText+"的监听器");
    }



}
