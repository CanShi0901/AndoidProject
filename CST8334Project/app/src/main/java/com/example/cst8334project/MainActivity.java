package com.example.cst8334project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends Activity {

    protected static final String ACTIVITY_NAME = "MainActivity";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //log.i("This is a test");
        // Log.i("MainActivity","Another test");

        Log.i(ACTIVITY_NAME, "In onCreate()");

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent,50);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==50){
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
        }
        if(resultCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME, "Returned Message is Result Ok");
        }
        String messagePassed = data.getStringExtra("Response");
        Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}