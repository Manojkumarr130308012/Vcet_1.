package com.example.vit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vit.Adapter.CustomAdapter;
import com.example.vit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Studentattedance extends AppCompatActivity {
String attendenceDate,periodid,section;
    List<Map<String, String>> data1= null;
    ListView listview;
    SimpleAdapter ADAhere;
    ListView simpleList;
  public String questions[];
  public String studentid[];
  public String statuss[];
    Button submit;
    String firstName;
    String lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentattedance);
        simpleList=findViewById(R.id.simpleListView);
        submit = (Button) findViewById(R.id.submit);

        Intent i=getIntent();
        attendenceDate=i.getStringExtra("attendenceDate");
        periodid=i.getStringExtra("periodid");
        section=i.getStringExtra("section");

        fetchData8 fetchData8 = new fetchData8();
        fetchData8.execute();

    }



    public class fetchData8 extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            data1= new ArrayList<Map<String, String>>();

            try {

                URL url = new URL("https://i-campusbackendapi.herokuapp.com/studentAttendence/fetchStudentAttendenceDetails1?attendenceDate="+attendenceDate+"&period="+periodid+"&section="+section);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }


                JSONArray jsonArray = new JSONArray(data);
                String name[]=new String[jsonArray.length()];
                String stuid[]=new String[jsonArray.length()];
                String status[]=new String[jsonArray.length()];

                for(int i=0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id=(jsonObject.optString("_id"));
                    String rollNo=(jsonObject.optString("rollNo"));
                    String studentAttendence=(jsonObject.optString("studentAttendence"));
                    String studentId=(jsonObject.optString("studentId"));
                    JSONArray jsonArray1 =jsonObject.getJSONArray ("studentDetails");
                    for(int J=0; J < jsonArray1.length(); J++)
                    {
                        Map<String,String> datanum=new HashMap<String,String>();
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(J);
                      firstName = (jsonObject1.optString("firstName"));
                      lastName  = (jsonObject1.optString("lastName"));
                        datanum.put("name",firstName+""+lastName);
                        datanum.put("studentAttendence",studentAttendence);
                        datanum.put("rollNo",rollNo);
                        datanum.put("id",id);
                        data1.add(datanum);

                    }
                    name[i]=firstName+""+lastName;
                    stuid[i]=id;
                    status[i]=studentAttendence;

                    questions=name;
                    studentid=stuid;
                    statuss=status;
                }

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
//        WriteFile(e);
            } catch (IOException e) {
                e.printStackTrace();
//        WriteFile(e);
            } catch (JSONException e) {
                e.printStackTrace();
//        WriteFile(e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CustomAdapter customAdapter = new CustomAdapter(Studentattedance.this, questions,statuss);
            simpleList.setAdapter(customAdapter);
            // perform setOnClickListerner event on Button
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "";
                    // get the value of selected answers from custom adapter
                    for (int i = 0; i < CustomAdapter.selectedAnswers.size(); i++) {
                        message = message + "\n" + studentid[i] + " " + CustomAdapter.selectedAnswers.get(i);

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JSONObject object = new JSONObject();
                        try {

                            object.put("studentAttendence",""+CustomAdapter.selectedAnswers.get(i));

//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url1 ="https://i-campusbackendapi.herokuapp.com/studentAttendence/update?id="+studentid[i];

                        // Enter the correct url for your api service site

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,url1,object,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("xdddddddddddd",""+ response.toString());
//                                        Toast.makeText(Studentattedance.this, "User Created Sucessfully", Toast.LENGTH_SHORT).show();
//                                    Intent i=new Intent(Patientadd.this,Adminadthar.class);
//                                    startActivity(i);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("xddddd",""+error);

                            }
                        });
                        requestQueue.add(jsonObjectRequest);



                    }
                    // display the message on screen with the help of Toast.
                    Toast.makeText(getApplicationContext(), "StudentAttendance Update Sucessfully", Toast.LENGTH_LONG).show();
                }
            });


        }
    }
}