package com.example.keh_a.mangkkong;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;
import static com.kakao.util.helper.Utility.getPackageInfo;

public class KaKaLogin extends AppCompatActivity {

    SessionCallback callback;
    Context context;
    String mJsonString;

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
