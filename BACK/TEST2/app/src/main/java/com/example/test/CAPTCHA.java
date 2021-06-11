package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.luozm.captcha.Captcha;

public class CAPTCHA extends AppCompatActivity {
    private Captcha captcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_a_p_t_c_h);
        captcha = findViewById(R.id.captCha);
        captcha.setCaptchaListener(new Captcha.CaptchaListener(){

            @Override
            public String onAccess(long time) {
                Toast.makeText(CAPTCHA.this, "驗證成功", Toast.LENGTH_SHORT).show();
                return "驗證通過";
            }

            @Override
            public String onFailed(int failCount) {
                Toast.makeText(CAPTCHA.this, "驗證失敗次數:" + failCount, Toast.LENGTH_SHORT).show();
                return "驗證失敗";
            }

            @Override
            public String onMaxFailed() {
                Toast.makeText(CAPTCHA.this, "验证超过次数，你的帐号被封锁", Toast.LENGTH_SHORT).show();
                android.os.Process.killProcess(android.os.Process.myPid());
                return "可以走了";
            }
        });
    }


}