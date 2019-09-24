package com.example.myapplicationtechapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button but_login;
    TextView tv_donthaveAcc;
    EditText et_email;
    EditText et_password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        but_login = findViewById(R.id.but_login);
        tv_donthaveAcc = findViewById(R.id.tv_donthaveAcc);
       et_email = findViewById(R.id.et_email);
       et_password = findViewById(R.id.et_password);

        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (et_email.getText().toString().length()==0){
                    Validations.MyAlertBox(LoginActivity.this,"Please Enter your Email");
                   et_email.requestFocus();
               }else if (et_password.getText().toString().length()==0){
                   Validations.MyAlertBox(LoginActivity.this,"Please Enter your Password");
                   et_password.requestFocus();
               }else {
                    if (Validations.isConnectedToInternet(LoginActivity.this)){
                        new  LoginActivity.userLogin(et_email.getText().toString(),et_password.getText().toString()).execute();
                    }else {
                        Validations.MyAlertBox(LoginActivity.this,"Connection Lost");
                    }
               }
            }
        });

        tv_donthaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });
    }

    private class userLogin extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String email,password,title_id,pageName,pageTitle,pageImage,pageDescription;

        public userLogin(final String email,final String password) {
            this.email = email;
            this.password = password;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            json = JSONParser.makeServiceCall("http://96.125.162.228/Technology/app-user-login",2,nameValuePairs);
            //json = JSONParser.makeServiceCall("http://96.125.162.228/Technology/app-user-login",2,nameValuePairs);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //    Toast.makeText(getBaseContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            try {
                JSONObject result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equalsIgnoreCase("success")) {
                    JSONObject data = new JSONObject(result.getString("data"));
                     //Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_SHORT).show();

//                    title_id = data.getString("id");
//                    pageName = data.getString("pageName").toString();
//                    pageTitle = data.getString("pageTitle").toString();
//                    pageImage = data.getString("pageImage").toString();
//                    pageDescription = data.getString("pageDescription").toString();







//                    Log.d("user_id===",user_id);
//                    Log.d("username===",username);
//                    Log.d("password===",password);
//                    Log.d("email===",email);
//                    Log.d("dob===",dob);
//                    Log.d("musicinterest===",musicinterest);
//





                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Login Successfully");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
                } else if (result.getString("status").toString().equals("failed")) {
                  /*  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login_screen.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Invalid User Email or password");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intentt=new Intent(Login_screen.this, TabEvents.class);
                            startActivity(intentt);
                            finish();
                        }
                    }).show();*/

                    Validations.MyAlertBox(LoginActivity.this,"Invalid Email or password");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                /// this validation works when services not worked

            }
        }
    }
    //******* HIDING KEYBOARD **********
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null
                && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && v instanceof EditText
                && !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop()
                    || y > v.getBottom())
                Validations.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }
}
