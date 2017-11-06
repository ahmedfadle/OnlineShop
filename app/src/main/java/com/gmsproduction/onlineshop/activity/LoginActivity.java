package com.gmsproduction.onlineshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fourhcode.forhutils.FUtilsValidation;
import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.other.Constant;
import com.gmsproduction.onlineshop.other.Session;
import com.gmsproduction.onlineshop.other.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView tv_dont_have_account;
    EditText etEmail,etPassword;
    Button btnLogin;

    RelativeLayout rlltLoading;
    ProgressBar prgsLoading;
    RelativeLayout rlltBody;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
/*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

*/




        init();
        if (getIntent() != null) {
            String email = getIntent().getStringExtra("email");
            String pass = getIntent().getStringExtra("password");
            etEmail.setText(email);
            etPassword.setText(pass);
        }

        setListner();


    }



    public void init()
    {
        tv_dont_have_account= (TextView) findViewById(R.id.tv_dont_have_account);
        etEmail= (EditText) findViewById(R.id.et_email);
        etPassword= (EditText) findViewById(R.id.et_password);

        btnLogin= (Button) findViewById(R.id.btn_login);



        rlltBody= (RelativeLayout) findViewById(R.id.rllt_body);
        rlltLoading= (RelativeLayout) findViewById(R.id.rllt_loading);


    }


    public void setListner(){



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FUtilsValidation.isEmpty(etEmail, getString(R.string.enter_email))
                        && FUtilsValidation.isValidEmail(etEmail, getString(R.string.enter_valid_email))
                        && !FUtilsValidation.isEmpty(etPassword, getString(R.string.enter_password))
                        ) {
                    setLoadingMode();


                    String email =etEmail.getText().toString();
                    String password =etPassword.getText().toString();

                    userLogin(email,password);





                }
            }
        });






        tv_dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                // override default transation of activity

                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);

               // startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }


    public void userLogin(final String email, final String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("login",response);
                        setNormalMode();

                        if (response.equals("[]"))
                        {
                            Toast.makeText(LoginActivity.this, "Try Again and check your EMAIL and Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                JSONArray read=new JSONArray(response);
                                JSONObject user =read.getJSONObject(0);

                                String id=user.get("id").toString();
                                String username=user.get("username").toString();
                                String email=user.get("email").toString();

                                String password=user.get("password").toString();

                                Toast.makeText(LoginActivity.this, "Welcome "+username, Toast.LENGTH_SHORT).show();
                                User userLogin=new User();
                                userLogin.setEmail(email);
                                userLogin.setUsername(username);
                                userLogin.setPassword(password);
                                userLogin.setId(id);




                          /*      sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                                // String played=sharedPreferences.getString("id"+idList.get(Constant.postion),"");
                                editor=sharedPreferences.edit();
                                editor.putString("id",id);
                                editor.commit();*/



                                Session.getInstance().loginUser(userLogin);


                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                // override default transation of activity

                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                     /*   try {
                            JSONArray read=new JSONArray(response);
                            JSONObject tracks=read.getJSONObject(0);
                            if (tracks.get)

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                       // setNormalMode();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // set loading layout visible and hide body layout
    private void setLoadingMode() {
        rlltLoading.setVisibility(View.VISIBLE);
        rlltBody.setVisibility(View.GONE);
    }

    // set body layout visible and hide loading layout
    private void setNormalMode() {
        rlltLoading.setVisibility(View.GONE);
        rlltBody.setVisibility(View.VISIBLE);
    }

}
