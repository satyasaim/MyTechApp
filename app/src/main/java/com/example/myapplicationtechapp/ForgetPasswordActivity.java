package com.example.myapplicationtechapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email_forgot;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email_forgot = findViewById(R.id.et_forgetpass);
        submit = findViewById(R.id.but_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_forgot.getText().toString().length()==0){
                    Validations.MyAlertBox(ForgetPasswordActivity.this,"Please Enter Email");
                    email_forgot.requestFocus();
                }else {
                    if (Validations.isConnectedToInternet(ForgetPasswordActivity.this)){
                        new ForgetPasswordActivity.forgetpassword(email_forgot.getText().toString()).execute();
                    }else {
                        Validations.MyAlertBox(ForgetPasswordActivity.this,"Internet Connection Lost");
                    }


                }
            }
        });
    }

    private class forgetpassword extends AsyncTask<String,String, JSONObject> {
        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String email;

        public forgetpassword(final String email) {

            this.email = email;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", email));
            //nameValuePairs.add(new BasicNameValuePair("password",password));
            //json = JSONParser.makeServiceCall("http://96.125.162.228/rinse/api/users/signin",2,nameValuePairs);
            json = JSONParser.makeServiceCall("http://96.125.162.228/Technology/app-user-forgotPassword",2,nameValuePairs);
            return json;


        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //    Toast.makeText(getBaseContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            try {
                JSONObject result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equalsIgnoreCase("success")) {
                    JSONObject data = new JSONObject(result.getString("data"));
                    // Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_SHORT).show();

                    email = email_forgot.getText().toString();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ForgetPasswordActivity.this);
                    builder.setTitle(R.string.message);
                    builder.setMessage(R.string.frgtEmailSent);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent forgot=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                            startActivity(forgot);
                            finish();
                        }
                    }).show();


                } else if (result.getString("status").toString().equals("failed")) {
                    /* android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignUp_Screen.this);
                    builder.setTitle("Job Portal");
                    builder.setCancelable(false);
                    builder.setMessage("User Already Exists");
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();*/
                    Validations.MyAlertBox(ForgetPasswordActivity.this,"The email you`ve enterd doesn`t match an existing account.");


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e1) {
                /// this validation works when services not worked

            }
        }
    }
}
