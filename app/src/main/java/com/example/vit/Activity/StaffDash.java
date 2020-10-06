package com.example.vit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vit.config.DBHelper;
import com.example.vit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.os.Build.ID;

public class StaffDash extends AppCompatActivity {
    DBHelper dbHelper;
    String id,na,pa,tok;
    String weekday_name;
    String acadmicyear,acadmicyearname;
    String dayid;
    SimpleAdapter ADAhere;
    String staffdashboardid;
    Map<String, String> datanum,num;
    ListView listview;
    String firstName;
    String designation1;
    TextView dateTextView,staffname,designation,periodcount,acadmicyearname1;
    RelativeLayout relative;

    String sectionidstr,studentstatus;
    String section;
    String sectiomidstr;
    String periodName;
    String periodidName;
    String subjectDescription;
    String subjectid;
    String institution;
    String academicYear;
    List<Map<String, String>> data2= null;
    String courceprogramstr,batchstr,courceprogramstrname,semesterstrname,instiutatestr,semesterstr,sectionstr,regnostr,rollnostr,studentidstr,attendancedatestr,attendancedaystr,periodstr,staffidstr,subjectidstr,acadmicyearstr,studentattendancestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dash);

listview=findViewById(R.id.listview);
        dateTextView=findViewById(R.id.dateTextView);
        staffname=findViewById(R.id.staffname);
        designation=findViewById(R.id.designation);
        periodcount=findViewById(R.id.periodcount);
        acadmicyearname1=findViewById(R.id.acadmicyearname1);
        relative=findViewById(R.id.relative);
        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.GetSQLiteDatabaseRecords();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
            tok = res.getString(3);
        }

        Log.e("na",""+na);
        Log.e("pa",""+pa);
        Log.e("tok",""+tok);

        weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        Log.e("weekday_name",""+weekday_name);
        dateTextView.setText(""+thisDate);
        fetchData4 fetchData4 = new fetchData4();
        fetchData4.execute();
        fetchData5 fetchData5 = new fetchData5();
        fetchData5.execute();

    }

    public class fetchData4 extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {






                URL url = new URL("https://i-campusbackendapi.herokuapp.com/weekDay/afetchattendenceDayId?day="+weekday_name);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }


                JSONObject jsonobj = new JSONObject(data);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonobj.getJSONArray("response");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    dayid  = (jsonObject.optString("_id"));
                    String day  = (jsonObject.optString("day"));
                    Log.e("dayid",""+dayid);
                    Log.e("ssssssssss",""+day);
                }




                // fetch JSONArray named users
//                prest = Integer.parseInt(jsonObj.getString("count"));
//                prest = Integer.parseInt((String) jsonobj.get("count"));
//                Log.e("ssssssssss",""+singleParsed);

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

        }
    }
    public class fetchData5 extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {

                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);

                URL url = new URL("https://i-campusbackendapi.herokuapp.com/academic-year/fetchdataactive?status=active");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }


                JSONObject jsonobj = new JSONObject(data);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonobj.getJSONArray("response");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                  acadmicyear  = (jsonObject.optString("_id"));
                  acadmicyearname  = (jsonObject.optString("year"));
//                    String day  = (jsonObject.optString("day"));
                    Log.e("acadmicyear",""+acadmicyear);
//                    Log.e("ssssssssss",""+day);
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

            acadmicyearname1.setText(""+acadmicyearname);
            fetchData6 fetchData6 = new fetchData6();
            fetchData6.execute();
        }
    }
    public class fetchData6 extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        List<Map<String, String>> data1= null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {


            data1= new ArrayList<Map<String, String>>();

            try {

                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);
                data1 = new ArrayList<Map<String, String>>();
                URL url = new URL("https://i-campusbackendapi.herokuapp.com/time-table/fetchtimestaffdata?academicYear="+acadmicyear+"&staff="+pa+"&day="+dayid);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }


                JSONObject jsonobj = new JSONObject(data);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonobj.getJSONArray("response");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++)
                {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                  String sectionid  = (jsonObject.optString("sectionid"));
                  staffdashboardid  = (jsonObject.optString("_id"));
                  String staff  = (jsonObject.optString("staff"));
                  String day  = (jsonObject.optString("day"));
                  String subject  = (jsonObject.optString("subject"));
                  String period  = (jsonObject.optString("period"));
                   institution  = (jsonObject.optString("institution"));
                   academicYear  = (jsonObject.optString("academicYear"));



                    JSONArray jsonArray1 =jsonObject.getJSONArray ("sectionDetails");
                    JSONArray jsonArray2 =jsonObject.getJSONArray("periodDetails");
                    JSONArray jsonArray3 =jsonObject.getJSONArray("subjectDetails");
                    JSONArray jsonArray4 =jsonObject.getJSONArray("staffDetails");

                    for(int J=0; J < jsonArray1.length(); J++)
                    {
                        Map<String,String> datanum=new HashMap<String,String>();
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(J);
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(J);
                        JSONObject jsonObject3 = jsonArray3.getJSONObject(J);
                        JSONObject jsonObject4 = jsonArray4.getJSONObject(J);
                        weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
                         section  = (jsonObject1.optString("section"));
                         sectiomidstr  = (jsonObject1.optString("_id"));
                         periodName  = (jsonObject2.optString("periodName"));
                         periodidName  = (jsonObject2.optString("_id"));
                         subjectDescription  = (jsonObject3.optString("subjectDescription"));
                         subjectid  = (jsonObject3.optString("_id"));
                        firstName  = (jsonObject4.optString("firstName"));
                        designation1  = (jsonObject4.optString("designation"));
                        Log.e("ssssssssectionsss",""+section);
                        Log.e("ssssssperiodNamessss",""+periodName);
                        Log.e("sssssssriptionssss",""+subjectDescription);
                        Log.e("sssssfirstNamesssss",""+firstName);
                        Log.e("sssssweekday_namess",""+weekday_name);
String studentstatus="Present";



                        datanum.put("section",section);
                        datanum.put("periodName",periodName);
                        datanum.put("subjectDescription",subjectDescription);
                        datanum.put("firstName",firstName);
                        datanum.put("weekday_name",weekday_name);
                        datanum.put("staffdashboardid",staffdashboardid);
                        datanum.put("sectionid",sectiomidstr);
                        datanum.put("institution",institution);
                        datanum.put("academicYear",academicYear);
                        datanum.put("academicYeanamer",acadmicyearname);
                        datanum.put("periodid",periodidName);
                        datanum.put("periodname",periodName);
                        datanum.put("staffid",pa);
                        datanum.put("subjectid",subjectid);
                        datanum.put("studentattendance",studentstatus);
                        datanum.put("attendancedate",thisDate);
                        datanum.put("attendanceday",weekday_name);
//                        datanum.put("joindate",(jsonObject.optString("joindate")));
                        data1.add(datanum);
                    }

                }

                // fetch JSONArray named users
//                prest = Integer.parseInt(jsonObj.getString("count"));
//                prest = Integer.parseInt((String) jsonobj.get("count"));
//                Log.e("ssssssssss",""+singleParsed);

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

            Log.e("ddddddddddddddddddddd",""+data1.toString());

            staffname.setText(""+firstName);
            designation.setText(""+designation1);
            periodcount.setText(""+data1.size());

            String[] fromwhere = { "section","periodName","weekday_name","subjectDescription","staffdashboardid"};

            int[] viewswhere = {R.id.section ,R.id.Period,R.id.day,R.id.subject,R.id.idview};

            ADAhere = new SimpleAdapter(StaffDash.this, data1,R.layout.todaystaffperiod, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    relative=view.findViewById(R.id.relative);

                    relative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            sectionidstr=(String)obj.get("sectionid");

                            periodstr=(String)obj.get("periodid");
                            subjectidstr=(String)obj.get("subjectid");
                            attendancedatestr=(String)obj.get("attendancedate");
                            attendancedaystr=(String)obj.get("attendanceday");
                            studentstatus=(String)obj.get("studentattendance");
                            acadmicyearstr=(String)obj.get("academicYear");
                            acadmicyearname=(String)obj.get("academicYeanamer");
                            subjectDescription=(String)obj.get("subjectDescription");
                            periodName=(String)obj.get("periodname");
                            section=(String)obj.get("section");
                            Toast.makeText(StaffDash.this, sectionidstr, Toast.LENGTH_SHORT).show();
                            Log.e("dddddddddddddddd",""+sectionidstr);
                            fetchData7 fetchData7 = new fetchData7();
                            fetchData7.execute();
                        }
                    });



                    return view;
                }
            };

            listview.setAdapter(ADAhere);


        }
    }


    public class fetchData7 extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL url = new URL("https://i-campusbackendapi.herokuapp.com/time-table/fetchStudentDetails?sectionId="+sectionidstr);
                data2 = new ArrayList<Map<String, String>>();
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }


//                JSONObject jsonobj = new JSONObject("");
//
//                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(data);

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++)
                {

                    Map<String,String> datanum=new HashMap<String,String>();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    courceprogramstr  = (jsonObject.optString("courseprogram"));
                    batchstr  = (jsonObject.optString("batch"));
                    semesterstr  = (jsonObject.optString("semester"));
                    sectionidstr  = (jsonObject.optString("section"));
                    regnostr  = (jsonObject.optString("regNo"));
                    rollnostr  = (jsonObject.optString("rollNo"));
                    studentidstr  = (jsonObject.optString("_id"));



                    datanum.put("courseprogram",courceprogramstr);
                    datanum.put("batch",batchstr);
                    datanum.put("institution",institution);
                    datanum.put("semester",semesterstr);
                    datanum.put("section",sectionidstr);
                    datanum.put("regNo",regnostr);
                    datanum.put("rollNo",rollnostr);
                    datanum.put("studentid",studentidstr);
                    datanum.put("attendancedate",attendancedatestr);
                    datanum.put("attendanceday",attendancedaystr);
                    datanum.put("periodid",periodstr);
                    datanum.put("staffid",pa);
                    datanum.put("subjectid",subjectidstr);
                    datanum.put("academicYear",acadmicyearstr);
                    datanum.put("attendacestatus","Present");
//                        datanum.put("joindate",(jsonObject.optString("joindate")));
                    data2.add(datanum);
                }




                // fetch JSONArray named users
//                prest = Integer.parseInt(jsonObj.getString("count"));
//                prest = Integer.parseInt((String) jsonobj.get("count"));
//                Log.e("ssssssssss",""+singleParsed);

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




            fetchData8 fetchData8= new fetchData8();
            fetchData8.execute();

            Log.e("data2",data2.toString());


        }
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
            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);

                try {

                    URL url = new URL("https://i-campusbackendapi.herokuapp.com/studentAttendence/fetchAttendenceEntryExist1?attendenceDate="+data2.get(0).get("attendancedate")+"&period="+data2.get(0).get("periodid")+"&subjectId="+data2.get(0).get("subjectid"));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }


                    JSONArray jsonArray = new JSONArray(data);
                   int len=jsonArray.length();

                    if (len==0){

                        for(int i=0;i<data2.size();i++){

                            String courseprogram=data2.get(i).get("courseprogram");
                            String batch=data2.get(i).get("batch");
                            String institution=data2.get(i).get("institution");
                            String semester=data2.get(i).get("semester");
                            String section=data2.get(i).get("section");
                            String regNo=data2.get(i).get("regNo");
                            String rollNo=data2.get(i).get("rollNo");
                            String studentid=data2.get(i).get("studentid");
                            String attendancedate=data2.get(i).get("attendancedate");
                            String attendanceday=data2.get(i).get("attendanceday");
                            String periodid=data2.get(i).get("periodid");
                            String staffid=data2.get(i).get("staffid");
                            String subjectid=data2.get(i).get("subjectid");
                            String academicYear=data2.get(i).get("academicYear");
                            String attendacestatus=data2.get(i).get("attendacestatus");
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            JSONObject object = new JSONObject();
                            try {

                                object.put("regNo",""+regNo);
                                object.put("rollNo",""+rollNo);
                                object.put("attendenceDate",""+attendancedate);
                                object.put("attendenceDay",""+attendanceday);
                                object.put("studentAttendence",""+attendacestatus);
                                object.put("courseprogram",courseprogram);
                                object.put("batch",batch);
                                object.put("institution",institution);
                                object.put("semester",semester);
                                object.put("section",section);
                                object.put("studentId",studentid);
                                object.put("period",periodid);
                                object.put("staffId",staffid);
                                object.put("subjectId",subjectid);
                                object.put("academicYear",academicYear);
//
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String url1 ="https://i-campusbackendapi.herokuapp.com/studentAttendence/add";

                            // Enter the correct url for your api service site

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url1,object,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.e("xdddddddddddd",""+ response.toString());
                                            Toast.makeText(StaffDash.this, "User Created Sucessfully", Toast.LENGTH_SHORT).show();
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


                        Intent i=new Intent(StaffDash.this,Studentattedance.class);
                        i.putExtra("attendenceDate",""+thisDate);
                        i.putExtra("periodid",""+data2.get(0).get("periodid"));
                        i.putExtra("section",""+data2.get(0).get("section"));
                        startActivity(i);



                    }else {
                        Intent i=new Intent(StaffDash.this,Studentattedance.class);
                        i.putExtra("attendenceDate",""+thisDate);
                        i.putExtra("periodid",""+data2.get(0).get("periodid"));
                        i.putExtra("section",""+data2.get(0).get("section"));
                        startActivity(i);
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

        }
    }


}