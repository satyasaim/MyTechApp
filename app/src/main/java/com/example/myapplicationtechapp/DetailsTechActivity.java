package com.example.myapplicationtechapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplicationtechapp.Adapters.CustomAdapter;
import com.example.myapplicationtechapp.Pojodata.Pojoclass;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsTechActivity extends AppCompatActivity {

    ImageView back;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String tech_id;
    TextView tv_name,tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tech);





        new DetailsTechActivity.getDetails(tech_id.toString()).execute();

        tv_name = findViewById(R.id.tv_name);
        tv_description = findViewById(R.id.tv_description);


        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsTechActivity.this,HomeActivity.class));
                finish();
            }
        });


    }

    private class getDetails extends AsyncTask<String,String, JSONObject> {
        private ProgressDialog pDialog;
        private ArrayList<NameValuePair> nameValuePairs;
        private JSONObject json;
        String tech_id,pagename,pagedescp;
        int i=0;

        public getDetails(String tech_id) {
            this.tech_id=tech_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        pagename = json_data.getString("pageTitle").toString();
                        pagedescp = json_data.getString("pageDescription").toString();


                        tv_name.setText(pagename);
                        tv_description.setText(pagedescp);


                    }
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
