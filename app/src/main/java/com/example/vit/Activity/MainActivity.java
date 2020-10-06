package com.example.vit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vit.config.DBHelper;
import com.example.vit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
EditText editTextEmail;
EditText editTextPassword;
    Button ic_login_button;
    DBHelper dbHelper;
    ProgressBar pbar;
    String Storeuser;
    String Storemob;
    String message;
    String checkusername,checkpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DBHelper(this);
        pbar = (ProgressBar) findViewById(R.id.log_progress);
        editTextEmail=findViewById(R.id.txtName);
        editTextPassword=findViewById(R.id.txtEmail);
        ic_login_button=findViewById(R.id.btnSend);

        ic_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkusername = editTextEmail.getText().toString();
                checkpassword = editTextPassword.getText().toString();
                //validate form
                if(validateLogin(checkusername, checkpassword)){
                    //do loginhj

                    Log.e("ffffffffffffffff",""+checkusername);
                    Log.e("ffffffffffffffff",""+checkpassword);

                    fetchData fetchData = new fetchData();
                    fetchData.execute();
                }
            }
        });
    }

    public boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public class fetchData extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (checkusername == null || checkpassword == null) {

//                Toast.makeText(LoginActivity.this, " Fill the Fields", Toast.LENGTH_SHORT).show();

            } else {

                try {

                    URL url = new URL("https://i-campusbackendapi.herokuapp.com/staffLogin/stafflogin?username="+checkusername+"&password="+checkpassword);
                    message="error";
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }

                    Log.e("dddddddddddddd", "" + data);
                    JSONObject jsonobj = new JSONObject(data);
//                    message = String.valueOf(jsonobj.getJSONObject("status"));
                    JSONObject jObject = jsonobj.getJSONObject("data");
                    singleParsed = (String) jObject.get("_id");
                    Storeuser = (String) jObject.get("designation");
                    String first=(String) jObject.get("firstName");
                    String last=(String) jObject.get("lastName");
                    Storemob =""+(String) jObject.get("firstName")+""+(String) jObject.get("lastName");




                    Log.e("ddddddddd", "" + singleParsed);
                    Log.e("ddddddddd", "" + Storeuser);
                    Log.e("ddddddddd", "" + message);

                    dataParsed = dataParsed + singleParsed + "\n";



              /*      if (message != "Welcome !!") {
                        dbHelper.insertData(Storeuser,singleParsed);
//                        pbar.setVisibility(View.INVISIBLE);

                        Intent i=new Intent(ActivitySignin.this,Student_details.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(ActivitySignin.this, ""+message, Toast.LENGTH_SHORT).show();
//                        pbar.setVisibility(View.INVISIBLE);
                    }*/


                } catch (MalformedURLException e) {
                    e.printStackTrace();
//                    WriteFile(e);
                } catch (IOException e) {
                    e.printStackTrace();
//                    WriteFile(e);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    WriteFile(e);
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!singleParsed.equals("")) {
//                        pbar.setVisibility(View.INVISIBLE);
                pbar.setVisibility(View.GONE);
                dbHelper.insertData(Storeuser,singleParsed,Storemob);
                Intent i=new Intent(MainActivity.this,StaffDash.class);
                i.putExtra("user",""+Storeuser);
                startActivity(i);

            }else{
                pbar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }

        }
    }

}