package com.example.cst8334project;

import android.app.Activity;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cst8334project.emailservice.Email;
import com.example.cst8334project.emailservice.SendEmailActivity;
import com.example.cst8334project.userhistoryservice.Visit;

import java.util.Date;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        // The subject of the email
        final String emailSubject = "Volunteer Visit - " + new Date().toString();

        // The body of the email
        final String emailBody = "Please find attached the information for a volunteer visit.";

        // The text to write to the CSV file
        final String emailAttachmentText = "Name, Duration, Location\nJohn, 90 minutes, Ottawa";

        // The CSV attachment file name
        final String emailAttachmentFileName = "HHH - " + emailSubject + ".csv";

        // Create the Email object and add the subject, body, and attachment text along
        // with a name for the attachment file
        Email email = new Email();
        email.setSubject(emailSubject);
        email.setBody(emailBody);
        email.setAttachmentText(emailAttachmentText);
        email.setCsvAttachmentFileName(emailAttachmentFileName);

        // Create the Visit object corresponding to the email
        Visit visit = new Visit();
        visit.setUserNote("This visit was OK.");

        // Send the Email and add the Visit to the user history
        new SendEmailActivity(this, email, visit).execute();
        //log.i("This is a test");
        Log.i("MainActivity","Another test");

 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
        createFile();
    }

        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.signin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString().trim(), pass.getText().toString().trim());
            }
        });
    }

    private void validate(String username, String userpass){
        if(username.equals("volun") && userpass.equals("123")){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "The username or password you entered was incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "permission granted",Toast.LENGTH_SHORT).show();
                }
        }
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

    public void createFile(){

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"File.csv");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(combinedString.getBytes());
            fos.close();
            Toast.makeText(this, "saved file",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "file not found",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "error saving",Toast.LENGTH_SHORT).show();
        }


    }

    String columnString = "\"PersonName\",\"Gender\",\"Street1\",\"postOffice\",\"Age\"";
    //String dataString   =   "\"" + currentUser.userName +"\",\"" + currentUser.gender + "\",\"" + currentUser.street1 + "\",\"" + currentUser.postOFfice.toString()+ "\",\"" + currentUser.age.toString() + "\"";
    String dataString = "\"" + "Dash Xu" + "\",\"" + "female" + "\",\"" + "Baseline station" + "\",\"" + "POst" + "\",\"" + "21" + "\"";
    String combinedString = columnString + "\n" + dataString;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}