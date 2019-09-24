package com.example.myapplicationtechapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplicationtechapp.Adapters.CustomAdapter;
import com.example.myapplicationtechapp.Pojodata.Pojoclass;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout linearLayout;
    ArrayList<Pojoclass> pojoclasses=new ArrayList<Pojoclass>();
    ImageView back;
    String techid;
    CustomAdapter customAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String pageName,pageTitle,pageImage,pageDescription;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycleview);

        new HomeActivity.getdata().execute();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);





        back = findViewById(R.id.iv_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                finish();
            }
        });


    }


    private class
    getdata extends AsyncTask<String,String,JSONObject> {

        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        private String titel_id,pojoData1,pageName,pageDescription ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(HomeActivity.this);
//            pDialog.setMessage("Getting Data ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            json = JSONParser.makeServiceCall("http://96.125.162.228/Technology/app-tech-content",1, nameValuePairs);
            return json;

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);


            try{
                JSONObject result = new JSONObject(jsonObject.toString());
                if (result.getString("status").toString().equals("success")){

                    JSONArray array= new JSONArray(result.getString("data"));
                    for(int i=0;i<array.length();i++){
                        JSONObject json_data = array.getJSONObject(i);
                        Pojoclass pojoData = new Pojoclass();
                        pojoData.titel_id = json_data.getString("id").toString();
                        pojoData.name= json_data.getString("pageName").toString();
                        pojoData.cources= json_data.getString("pageDescription").toString();
                        pojoData.images= json_data.getString("pageImage").toString();
                        pojoclasses.add(pojoData);


                    }
                }





                customAdapter = new CustomAdapter(HomeActivity.this, pojoclasses);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
