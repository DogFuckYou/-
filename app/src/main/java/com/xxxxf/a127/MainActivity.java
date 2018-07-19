package com.xxxxf.a127;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int i=0;
    private ImageView mouse;
    private Handler handler;
    public int[][] position=new int[][]{{225,334},{469,317},{331,199},{340,219},
            {423,193},{450,251},{281,246}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                int index=0;
                while(!Thread.currentThread().isInterrupted()){
                    index=new Random().nextInt(position.length);
                    Message m=handler.obtainMessage();
                    m.arg1=index;//保存地鼠位置的索引值
                    m.what=0x101;
                    handler.sendMessage(m);
                    try {
                        Thread.sleep(new Random().nextInt(500)+500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int index=0;
                if (msg.what == 0x101){
                    index=msg.arg1;//获取位置索引
                    mouse.setX(position[index][0]);//设置X轴位置
                    mouse.setY(position[index][1]);//设置Y轴位置
                    mouse.setVisibility(View.VISIBLE);//设置地鼠显示
                }
                super.handleMessage(msg);
            }
        };
        mouse=(ImageView) findViewById(R.id.imageView1);
        mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE);
                i++;
                Toast.makeText(MainActivity.this,"打到["+i+"]只地鼠！",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
