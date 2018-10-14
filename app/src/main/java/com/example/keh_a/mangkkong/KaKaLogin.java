package com.example.keh_a.mangkkong;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.kakao.auth.Session;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static com.kakao.util.helper.Utility.getPackageInfo;

public class KaKaLogin extends AppCompatActivity {

    SessionCallback callback;
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

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

    }
}
