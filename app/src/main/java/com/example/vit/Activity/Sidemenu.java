package com.example.vit.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vit.R;
import com.example.vit.config.DBHelper;
import com.google.android.material.navigation.NavigationView;

public class Sidemenu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;
    String id,na,pa,tok;
TextView user_name,user_id;
CardView attendance;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidemenu);

        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        dbHelper=new DBHelper(this);

        Cursor res = dbHelper.GetSQLiteDatabaseRecords();


        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
            tok = res.getString(3);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.nav_view);
        user_name=findViewById(R.id.user_name);
        user_id=findViewById(R.id.user_id);
        attendance=findViewById(R.id.attendance);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Sidemenu.this,StaffDash.class);
                startActivity(i);
            }
        });

        user_name.setText(""+tok);
        user_id.setText(""+na);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Sidemenu.this,Sidemenu.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_attendance) {


                    startActivity(new Intent(Sidemenu.this,StaffDash.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);



                }
                else if (id == R.id.nav_map) {
                    Intent i = new Intent(Sidemenu.this, Sidemenu.class);
                    startActivity(i);

                    /*Fragment fragment1 = new Maps_Fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contain_frame, fragment1).commit();*/

                } /*else if (id == R.id.nav_timeline) {
                    Intent i = new Intent(MainActivity.this, Time_line_cal.class);
                   startActivity(i);

                    *//*Fragment fragment1 = new Maps_Fragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contain_frame, fragment1).commit();*//*

                }*/
                else if (id == R.id.nav_signout) {
                    dbHelper.deleteRow();
                    startActivity(new Intent(Sidemenu.this,MainActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }


                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contain_frame, fragment);
                    ft.commit();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }







    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Sidemenu.this);
        builder.setTitle("Bussness Master");
        builder.setMessage(type);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
//        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        //noinspection SimplifiableIfStatement

    /*    if (id == R.id.menu_search) {

            Intent intent = new Intent(this,Fooditem_search.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.menu_printer)  {
            Toast.makeText(this, "Android Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Android Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);

    }
}