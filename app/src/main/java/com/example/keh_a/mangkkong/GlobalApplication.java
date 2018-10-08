package com.example.keh_a.mangkkong;

import android.app.Application;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    public static GlobalApplication getGlobalApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Kakao Sdk 초기화
        KakaoSDK.init(new KaKaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}