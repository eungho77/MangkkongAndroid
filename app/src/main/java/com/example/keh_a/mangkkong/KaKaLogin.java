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

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(KaKaLogin.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String data = "id=" + params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if (result == null){

                Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                Log.d("succ", mJsonString);
                showResult();
            }
        }
    }

    private void showResult(){

        String TAG_JSON="RESULT";
        String TAG_NAME = "NAME";
        String TAG_NICKNAME = "NICKNAME";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

//                JSONObject item = jsonArray.getJSONObject(i);
//
//                String name = item.getString(TAG_NAME);
//                String nickname = item.getString(TAG_NICKNAME);

//                CustomerItem personalData = new CustomerItem();
//                personalData.setID(name);
//                personalData.setNickname(nickname);
//
//                adapter.items.clear();
//                adapter.addItem(personalData);
//                adapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
