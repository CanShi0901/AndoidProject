package com.example.cst8334project;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cst8334project.calendar.CalendarActivity;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle aToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        dLayout = findViewById(R.id.drawerLayout);
        aToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open, R.string.close);

        dLayout.addDrawerListener(aToggle);
        aToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView View = (NavigationView) findViewById(R.id.navigationView);
        View.setNavigationItemSelectedListener(this);

    }
//        Button timeSheet = findViewById(R.id.timeSheet);
//        Button appointment = findViewById(R.id.appointment);
//        Button history = findViewById(R.id.history);


//        timeSheet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View View) {
//                Intent intent = new Intent(MainMenu.this, VolunteerInfoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        appointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View View) {
//                Intent intent = new Intent(MainMenu.this, CalendarActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View View) {
//                Intent intent = new Intent(MainMenu.this, UserHistoryActivity.class);
//                startActivity(intent);
//            }
//        });

        @Override
        public boolean onOptionsItemSelected(MenuItem item){

            if(aToggle.onOptionsItemSelected(item)){
                return true;
            }

            return super.onOptionsItemSelected(item);

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.timeSheet)
        {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.appointment)
        {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.history)
        {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}


