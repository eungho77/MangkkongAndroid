package com.example.keh_a.mangkkong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import function.SessionCallback;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class KaKaLogin extends AppCompatActivity {

    KaKaoSession callback;
    Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakaologin);
        context = getApplicationContext();

        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        try {
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("aaaaa", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (NoSuchAlgorithmException e) {
            Log.w("KAKAOTALK", "Unable to get MessageDigest. signature=" + e);
        }

        callback = new KaKaoSession();
        Session.getCurrentSession().addCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    public class KaKaoSession implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            StartMain();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null){
                Logger.d(exception);
            }
            setContentView(R.layout.kakaologin);
        }
    }

    public void  StartMain(){
        Intent main = new Intent(this, SessionCallback.class);
        main.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(main);
        finish();
    }
}
