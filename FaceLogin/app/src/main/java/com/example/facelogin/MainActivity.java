package com.example.facelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        loginButton.registerCallback(callbackManager, resCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private FacebookCallback<LoginResult> resCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Global.currentAcc = new Account();
                    try {
                        Global.currentAcc.first_name = object.getString("first_name");
                        Global.currentAcc.last_name = object.getString("last_name");

                        info.setText("Username: " + Global.currentAcc.first_name + " " + Global.currentAcc.last_name);
                    } catch (JSONException e) {
                        Log.e("Error:", e.toString());
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,last_name");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            info.setText("Login attempt canceled.");
        }

        @Override
        public void onError(FacebookException error) {
            info.setText("Login attempt failed." + error.getCause().toString());
            return;
        }
    };
}
