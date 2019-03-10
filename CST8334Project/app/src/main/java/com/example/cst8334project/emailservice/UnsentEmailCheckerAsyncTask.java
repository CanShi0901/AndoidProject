package com.example.cst8334project.emailservice;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cst8334project.config.HeartHouseHospiceApp;
import com.example.cst8334project.domain.Email;
import com.example.cst8334project.util.ConnectionUtils;

import java.util.List;

/**
 * This class is responsible for checking whether there are any unsent emails
 * (emails that should have been sent earlier but were not, probably due to network connectivity issues)
 * in a background thread. If there are any such emails and the device is currently able to access
 * the SMTP server, this class delegates to {@link EmailSenderAsyncTask} so that it can send
 * the email(s).
 */
public class UnsentEmailCheckerAsyncTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * The name of this class for logging purposes.
     */
    private static final String CLASS_NAME = UnsentEmailCheckerAsyncTask.class.getSimpleName();

    /**
     * Provides service level methods for emails.
     */
    private final EmailService emailService;

    /**
     * The {@link List} of emails that were not able to be sent
     */
    private List<Email> unsentEmails;

    /**
     * Construct a new instance of {@link UnsentEmailCheckerAsyncTask}.
     */
    public UnsentEmailCheckerAsyncTask() {
        this.emailService = EmailServiceImpl.INSTANCE;
    }

    /**
     * This method is responsible for verifying whether the device can currently connect to the
     * SMTP server and whether there are any unsent emails in the data store. If so, it delegates
     * to {@link EmailSenderAsyncTask} so that it can send these emails.
     *
     * @param voids the parameters of the task
     * @return {@code true} if the device can connect to the SMTP server and there are unsent
     * emails in the data store, {@code false} otherwise
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i(CLASS_NAME, "Checking for any unsent emails...");

        // Check if the device can connect to the SMTP server
        if (!ConnectionUtils.canConnectToSMTPServer()) {
            Log.e(CLASS_NAME, "Could not connect to the SMTP server. Skipping check for " +
                    "unsent emails.");
            return false;
        }

        // Check if there are any unsent emails
        unsentEmails = emailService.findAllUnsentEmails();
        int numOfUnsentEmails = unsentEmails.size();

        if (numOfUnsentEmails == 0) {
            Log.i(CLASS_NAME, "There are no unsent emails.");
            return false;
        }

        Log.i(CLASS_NAME, "There are " + numOfUnsentEmails + " to send.");

        return true;
    }

    /**
     * This method is executed after verifying SMTP server connectivity and whether there are any
     * unsent emails. If both conditions are satisfied, then the {@link EmailSenderAsyncTask} class
     * will attempt to send these unsent emails.
     *
     * @param canSendUnsentEmails {@code true} if the device can connect to the SMTP server and
     *                            there are unsent emails in the data store, {@code false} otherwise
     */
    @Override
    protected void onPostExecute(Boolean canSendUnsentEmails) {
        // Send the unsent emails if there were any
        if (canSendUnsentEmails) {
            new EmailSenderAsyncTask(HeartHouseHospiceApp.getAppContext()).execute(unsentEmails.toArray(new Email[0]));
        }
    }
}
