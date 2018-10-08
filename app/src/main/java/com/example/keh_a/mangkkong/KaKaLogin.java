package com.example.keh_a.mangkkong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

public class KaKaLoing extends AppCompatActivity {

    LoginButton btn_kakao_login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakaoloing);

        btn_kakao_login = (LoginButton) findViewById(R.id.btnKakaoLogin);
    }

    public void OnClick(View v){
        int id = v.getId();

        if(id == R.id.btnKakaoLogin){
            btn_kakao_login.performClick();
            Session session = Session.getCurrentSession();
            session.addCallback(new SessionCallback());
            session.open(AuthType.KAKAO_LOGIN_ALL, LoginButton.this);
        }
    }
}
