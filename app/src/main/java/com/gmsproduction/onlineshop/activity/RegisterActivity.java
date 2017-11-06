package com.gmsproduction.onlineshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmed on 8/23/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText et_username,et_email,et_password,et_phone_number,et_repeat_password;
    Button btn_register;

    TextView tv_already_have_account;
    RelativeLayout rlltLoading;
    ProgressBar prgsLoading;
    RelativeLayout rlltBody;


    String username,email,password,repeated_password,phone_number;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setListner();
    }



    public void init()
    {

        et_email= (EditText) findViewById(R.id.et_email);
        et_password= (EditText) findViewById(R.id.et_password);
        et_repeat_password= (EditText) findViewById(R.id.et_repeat_password);
        et_phone_number= (EditText) findViewById(R.id.et_phone);
        et_username= (EditText) findViewById(R.id.et_username);

        tv_already_have_account= (TextView) findViewById(R.id.tv_already_have_account);
        btn_register= (Button) findViewById(R.id.btn_signup);


        rlltBody= (RelativeLayout) findViewById(R.id.rllt_body);
        rlltLoading= (RelativeLayout) findViewById(R.id.rllt_loading);


    }


    public void setListner()
    {


        tv_already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!FUtilsValidation.isEmpty(et_username, getString(R.string.enter_username))
                        && !FUtilsValidation.isEmpty(et_email, getString(R.string.enter_email))
                        && FUtilsValidation.isValidEmail(et_email, getString(R.string.enter_valid_email))
                        && !FUtilsValidation.isEmpty(et_password, getString(R.string.enter_password))
                        && !FUtilsValidation.isEmpty(et_repeat_password, getString(R.string.enter_password_again))
                        && FUtilsValidation.isPasswordEqual(et_password, et_repeat_password, getString(R.string.password_isnt_equal))
                        )
                {
                    setLoadingMode();



                    username = et_username.getText().toString();
                    email = et_email.getText().toString();
                    password = et_password.getText().toString();
                    repeated_password = et_repeat_password.getText().toString();
                    phone_number = et_phone_number.getText().toString();

                    userRegister(username,email,password,phone_number);

                }


            }
        });



    }





    public void userRegister(final String name, final String email, final String password, final String phone)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("fadle",response);

                        if (response.equals("[1]"))
                        {
                            Toast.makeText(RegisterActivity.this, "This Email is Already Exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Successfull Registeration", Toast.LENGTH_SHORT).show();


                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            intent.putExtra("email",et_email.getText().toString());
                            intent.putExtra("password",et_password.getText().toString());
                            startActivity(intent);


                        }

                     /*   try {
                            JSONArray read=new JSONArray(response);
                            JSONObject tracks=read.getJSONObject(0);
                            if (tracks.get)

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        setNormalMode();


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
                params.put("username",name);
                params.put("email",email);
                params.put("password",password);
                params.put("phone_number",phone);
               /* params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);*/
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
