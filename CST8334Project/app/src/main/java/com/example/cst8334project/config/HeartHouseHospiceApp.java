package com.example.cst8334project.config;

import android.app.Application;

import com.example.cst8334project.authentication.NewPasswordCheckerAsyncTask;
import com.example.cst8334project.emailservice.UnsentEmailCheckerAsyncTask;

/**
 * A subclass of the base Android {@link Application} class used to maintain state specifically
 * for this application. This class will be instantiated before any other class in the application,
 * and thus is useful for performing specific initialization tasks.
 */
public class HeartHouseHospiceApp extends Application {

    /**
     * At app startup, check for new passwords from the client and attempt to send any unsent
     * emails if there were any.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        new NewPasswordCheckerAsyncTask(this).execute();
        new UnsentEmailCheckerAsyncTask(this).execute();
    }
}
