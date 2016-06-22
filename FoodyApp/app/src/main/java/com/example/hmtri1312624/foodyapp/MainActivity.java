package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.Account;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RVAdapter adapter;
    List<FoodyItemInfo> data;
    RecyclerView rv;
    Button btnNext,btnSearchNext,btnShow;
    EditText editSearch;
    RelativeLayout layoutSearch;
    Context context;
    Activity activity;
    CallbackManager callbackManager;
    LoginButton loginButton;
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        Global.currentAcc = new Account();

        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        loginButton.registerCallback(callbackManager, resCallback);

        context = this;
        activity = this;

        layoutSearch = (RelativeLayout) findViewById(R.id.layout_search);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        editSearch = (EditText)findViewById(R.id.editSearchNext);
        editSearch.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));
        editSearch.setText(Global.CurrentQuery);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    SearchOther();
                    return true;
                }
                return false;
            }
        });

        btnSearchNext = (Button)findViewById(R.id.btnSearchNext);
        btnSearchNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.FONTAWESOME));

        //do search different food
        btnSearchNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchOther();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));

        //do find next place of current food
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setVisibility(View.GONE);
                layoutSearch.setVisibility(View.GONE);
                String Next = editSearch.getText().toString();
                if (!Next.equals(""))
                    Global.CurrentQuery = Next;
                getData(Global.CurrentQuery);
                Global.hideSoftInput(activity);
            }
        });

        btnShow=(Button)findViewById(R.id.btnShowBookMark);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestService rest = new RestService();
                Call<List<FoodyItemInfo>> menu = rest.getService().ShowBookmark(Global.currentAcc.userid);
                menu.enqueue(new Callback<List<FoodyItemInfo>>() {
                    @Override
                    public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                        List<FoodyItemInfo> temp = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {

                    }
                });

            }
        });

        //for facebook login
        sharedpreferences = getSharedPreferences(Global.MyPref, Context.MODE_PRIVATE);

        Global.currentAcc.userid = sharedpreferences.getString("UserID","xxx"); //default ID = xxx

        if(Global.currentAcc.userid.compareTo("xxx") == 0)
        {
            Global.currentAcc = new Account();
        }
        //end for facebook login
        btnNext.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.GONE);
        getData(Global.CurrentQuery);
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
            Global.currentAcc.userid = loginResult.getAccessToken().getUserId();

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("UserID",Global.currentAcc.userid);
            editor.apply();

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        Global.currentAcc.first_name = object.getString("first_name");
                        Global.currentAcc.last_name = object.getString("last_name");
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

        }

        @Override
        public void onError(FacebookException error) {
            return;
        }
    };
    private void getData(String FoodName){

        RestService restService = new RestService();
        Call<List<FoodyItemInfo>> call = restService.getService().GetPlaces(FoodName);

        Global.showPreloader(context, "Loading data...");
        call.enqueue(new Callback<List<FoodyItemInfo>>() {
            @Override
            public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                Global.hidePreloader();
                Global.hideSoftInput(activity);
                data = response.body();
                Global.currentMenuSet = null;
                Global.currentImageList = null;
                if (data == null)
                {
                    MyAlertDialog.ShowDialog("We did not found any places which has your food",activity);
                    btnNext.setVisibility(View.VISIBLE);
                    layoutSearch.setVisibility(View.VISIBLE);
                    return;
                }
                adapter = new RVAdapter(MainActivity.this, data, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d("", "clicked position:" + position);
                        long postId = data.get(position).getID();
                    }
                });
                rv.setAdapter(adapter);
                btnNext.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {
                Global.hidePreloader();
            }
        });
    }

    private void SearchOther()
    {
        Global.CurrentQuery = editSearch.getText().toString();
        btnNext.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.GONE);
        getData(Global.CurrentQuery);
        Global.hideSoftInput(activity);
    }

}
