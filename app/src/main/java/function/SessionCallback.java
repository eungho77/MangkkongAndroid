package function;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.keh_a.mangkkong.KaKaLogin;
import com.example.keh_a.mangkkong.MainActivity;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

public class SessionCallback extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestMe();
    }

    public void requestMe() {
        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {

                Log.e("SessionCallback :: ", "onSuccess");

                String nickname = userProfile.getNickname();
                String email = userProfile.getEmail();
                String profileImagePath = userProfile.getProfileImagePath();
                String thumnailPath = userProfile.getThumbnailImagePath();
                String UUID = userProfile.getUUID();
                long id = userProfile.getId();

                Log.e("Profile : ", nickname + "");
                Log.e("Profile : ", email + "");
                Log.e("Profile : ", profileImagePath  + "");
                Log.e("Profile : ", thumnailPath + "");
                Log.e("Profile : ", UUID + "");
                Log.e("Profile : ", id + "");

                redirectMinActivity();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }
        });
    }

    private void redirectMinActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void redirectLoginActivity(){
        final Intent intent = new Intent(this, KaKaLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
