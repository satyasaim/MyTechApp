package com.example.myapplicationtechapp;

import android.annotation.SuppressLint;
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

public class SignupActivity extends AppCompatActivity {

    EditText firstname,lastname,email,password,conpassword;
    Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstname = findViewById(R.id.et_firstname);
        lastname = findViewById(R.id.et_lastname);
        email = findViewById(R.id.et_email_signup);
        password = findViewById(R.id.et_password_signup);
        conpassword = findViewById(R.id.et_conpassword);
        signup = findViewById(R.id.but_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
//                finish();

                if (firstname.getText().toString().length() == 0) {
                    Validations.MyAlertBox(SignupActivity.this, "Please Enter First Name");
                    firstname.requestFocus();
                } else if (lastname.getText().toString().length() == 0) {
                    Validations.MyAlertBox(SignupActivity.this, "Please Enter Last Name");
                    lastname.requestFocus();
                } else if (email.getText().toString().length() == 0) {
                    Validations.MyAlertBox(SignupActivity.this, "Please Enter Email");
                    email.requestFocus();
                } else if (password.getText().toString().length() == 0) {
                    Validations.MyAlertBox(SignupActivity.this, "Please Enter Password");
                    password.requestFocus();
                } else if (!password.getText().toString().equals(conpassword.getText().toString())) {
                    Validations.MyAlertBox(SignupActivity.this, "Password Not Matched");

                } else {
                    if (Validations.isConnectedToInternet(SignupActivity.this)) {
                        new SignupActivity.userSignUp(
                                firstname.getText().toString(),
                                lastname.getText().toString(),
                                email.getText().toString(),
                                password.getText().toString(),
                                conpassword.getText().toString()).execute();
                    } else {

                        Validations.MyAlertBox(SignupActivity.this, "Connection Lost");
                    }

                }
            }
        });


    }


    @SuppressLint("StaticFieldLeak")
    private class userSignUp extends AsyncTask<String, String, JSONObject> {
        //private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String user_id,firstname1,lastname1,email1,password1,conpassword1;

        public userSignUp(final String firstname, final String lastname, final String email,
                          final String password,final String conpassword) {
            //this.pDialog = pDialog;


            this.firstname1 = firstname;
            this.lastname1 = lastname;
            this.email1 = email;
            this.password1 = password;
            this.conpassword1 = conpassword;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("first_name", firstname1));
            nameValuePairs.add(new BasicNameValuePair("last_name", lastname1));
            nameValuePairs.add(new BasicNameValuePair("email", email1));
            nameValuePairs.add(new BasicNameValuePair("password",password1));

            json = JSONParser.makeServiceCall("http://96.125.162.228/Technology/app-user-signup",2, nameValuePairs);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //    Toast.makeText(getBaseContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();

            JSONObject result;
            try {
                result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equals("success")) {
                     JSONObject data = new JSONObject(result.getString("data"));
                    // Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_SHORT).show();

                 /*      username = username_signup.getText().toString();
                       email = email_signup.getText().toString();
                       password = ed_password_signup.getText().toString();
                       cmpassword = ed_password_signup_cn.getText().toString();
                       gender = signup_gender_spin.getSelectedItem().toString();
                       date =     signup_date.getSelectedItem().toString();
                       month =    signup_month.getSelectedItem().toString();
                       year =     signup_year.getSelectedItem().toString();
                       music =     signup_music_spin.getSelectedItem().toString();
                       profile_pic = imageUploadValue.toString().trim();



                    sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();


                    user_id = data.getString("_id").toString();
                    username = data.getString("userName").toString();
                    email = data.getString("email").toString();
                    password = data.getString("password").toString();
                    cmpassword = data.getString("").toString();
                    gender = data.getString("gender").toString();
                    date = data.getString("dob").toString();
                    month = data.getString("dob").toString();
                    year = data.getString("dob").toString();
                    music = data.getString("Musicinterest").toString();
                    profile_pic = data.getString("profilepic").toString();



                    editor.putString("_id",user_id);
                    editor.putString("Username", username);
                    editor.putString("Email", email);
                    editor.putString("Password", password);
                    editor.putString("ConPassword", cmpassword);
                    editor.putString("date", date);
                    editor.putString("month", month);
                    editor.putString("year", year);
                    editor.putString("Music",music);
                    editor.putString("profilepic",imageUploadValue);
                    editor.commit();
*/











                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle("");
                    builder.setMessage("Registered Successfully");
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentt=new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intentt);
                            finish();
                        }
                    }).show();



                } else if (result.getString("status").toString().equals("userName failed")) {
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


                    Validations.MyAlertBox(SignupActivity.this,"UserName already Exist");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                // this validation works when services not worked
                Validations.MyAlertBox(SignupActivity.this, "Connection Lost");
            }
        }
    }
}
