package com.example.cst8334project;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cst8334project.domain.Visit;
import com.example.cst8334project.userhistoryservice.VisitService;
import com.example.cst8334project.userhistoryservice.VisitServiceImpl;

import java.util.Calendar;

public class IndirectActivity extends Activity {

    TimePickerDialog picker;

    String admin = "no";
    String fundraisingEvent = "no";
    String board = "no";
    String training = "no";
    String outreach = "no";
    String serviceType = "none";
    CheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5;

    EditText eText1;
    EditText eText2;
    EditText eText3;
    EditText eText4;
    EditText eText5;

    int adminHour, adminMin;
    int boardHour, boardMin;
    int trainingHour, trainingMin;
    int fundraisingEventHour, fundraisingEventMin;
    int outreachHour, outreachMin;


    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect);


        checkbox1 = findViewById(R.id.CB1);
        checkbox2 = findViewById(R.id.CB2);
        checkbox3 = findViewById(R.id.CB3);
        checkbox4 = findViewById(R.id.CB4);
        checkbox5 = findViewById(R.id.CB5);

        eText1 = findViewById(R.id.editText1);
        eText2 = findViewById(R.id.editText2);
        eText3 = findViewById(R.id.editText3);
        eText4 = findViewById(R.id.editText4);
        eText5 = findViewById(R.id.editText5);

        eText1.setInputType(InputType.TYPE_NULL);
        eText2.setInputType(InputType.TYPE_NULL);
        eText3.setInputType(InputType.TYPE_NULL);
        eText4.setInputType(InputType.TYPE_NULL);
        eText5.setInputType(InputType.TYPE_NULL);

        checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox1.isChecked())
                    {
                        eText1.setText("");
                        return;
                    }

                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                serviceType = "admin";

                picker = new TimePickerDialog(IndirectActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText1.setText(sHour + ":" + sMinute);
                                adminHour = sHour;
                                adminMin = sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox2.isChecked())
                {
                    eText2.setText("");
                    return;
                }
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                serviceType = "board";

                picker = new TimePickerDialog(IndirectActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText2.setText(sHour + ":" + sMinute);
                                boardHour = sHour;
                                boardMin = sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox3.isChecked())
                {
                    eText3.setText("");
                    return;
                }
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                serviceType = "training";

                picker = new TimePickerDialog(IndirectActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText3.setText(sHour + ":" + sMinute);
                                trainingHour = sHour;
                                trainingMin = sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        checkbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox4.isChecked())
                {
                    eText4.setText("");
                    return;
                }
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                serviceType = "fundraising";

                picker = new TimePickerDialog(IndirectActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText4.setText(sHour + ":" + sMinute);
                                fundraisingEventHour = sHour;
                                fundraisingEventMin  = sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        checkbox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox5.isChecked())
                {
                    eText5.setText("");
                    return;
                }
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                serviceType = "outreach";

                picker = new TimePickerDialog(IndirectActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText5.setText(sHour + ":" + sMinute);
                                outreachHour = sHour;
                                outreachMin = sMinute;

                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        btnSubmit = findViewById(R.id.btn_directSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                   VisitServiceImpl vsi = VisitServiceImpl.INSTANCE;
                    Visit visit = new Visit();
                    visit.setServiceType(serviceType);
                    visit.setUserNote("Performed service above.");
                    vsi.addVisit(visit);

                    Intent intent = new Intent(IndirectActivity.this, VolunteerInfoActivity.class);
                    startActivityForResult(intent,50);

                submitForm();


            }
        });
    }
//
    private void submitForm(){
        initialize();
        if (!validate()){
            Toast.makeText(this, "Sign up has Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            onLoginSuccess();
        }
    }

    public void onLoginSuccess() {
    }

    public boolean validate() {
        return validate() ;

    }

    private void initialize(){

        if(checkbox1.isChecked()){
            admin = "yes";
        }

        if(checkbox2.isChecked()){
            fundraisingEvent = "yes";
        }

        if(checkbox3.isChecked()){
            board = "yes";
        }

        if(checkbox4.isChecked()){
            training = "yes";
        }

        if(checkbox5.isChecked()){
            outreach = "yes";
        }



    }
}

