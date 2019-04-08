package com.example.cst8334project.authentication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cst8334project.config.HeartHouseHospiceApp;
import com.example.cst8334project.util.ConnectionUtils;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.Objects;

import javax.mail.*;
import javax.mail.internet.*;

import static com.example.cst8334project.emailservice.EmailConstants.*;
import static com.example.cst8334project.util.FileUtils.*;

/**
 * This class is responsible for checking whether the client has changed the password that will
 * be used to authenticate users. It does so by checking the designated email for this purpose
 * for any messages from the client. Only the most recent message by the client is used to set the
 * password.
 */
public final class NewPasswordCheckerAsyncTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * The name of this class for logging purposes.
     */
    private static final String CLASS_NAME = NewPasswordCheckerAsyncTask.class.getSimpleName();

    /**
     * The key for the password in the {@link android.content.SharedPreferences} file
     * for this application.
     */
    private static final String PASSWORD_KEY = "hhh.pw";

    /**
     * This method is executed in a background thread and is responsible for iterating through
     * the email messages in the email address designated by the client for the purposes of
     * changing the password used to authenticate volunteers. Only the most recent password
     * is used.
     *
     * @param voids the parameters of the task
     * @return {@code true} if the password was changed, {@code false} otherwise
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i(CLASS_NAME, "Checking for new password from the client...");

        // Get the password stored in the file
        String passwordFromFile = readFromSharedPreferences(PASSWORD_KEY, AuthenticationManager.getPassword());

        // If the device cannot connect to the IMAP server
        if (!ConnectionUtils.canConnectToIMAPServer()) {
            // Use the password stored in the file to authenticate the user
            AuthenticationManager.setPassword(passwordFromFile);
            Log.e(CLASS_NAME, "Could not connect to the Google IMAP server. " +
                    "Skipping password check and using the stored password: " + passwordFromFile);
            return false;
        }

        // Initially assume that the password has not been changed
        boolean passwordChanged = false;

        // Obtain the Session instance
        Session session = Session.getInstance(IMAP_PROPERTIES);

        try {
            /*
               Obtain a handle to the IMAPS message store and connect using the login credentials
               for the email account that listens for new passwords.
            */
            Store store = session.getStore("imaps");
            store.connect(PASSWORD_LISTENER_EMAIL_ADDRESS, PASSWORD_LISTENER_EMAIL_PASSWORD);

            // Obtain a handle to the inbox folder in the message store
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);

            // Get the inbox message count
            int messageCount = inbox.getMessageCount();

            // If there aren't any messages, skip the password check and use the stored password
            if (messageCount == 0) {
                AuthenticationManager.setPassword(passwordFromFile);
                Log.e(CLASS_NAME, "The inbox is empty. Skipping password check and using" +
                        " the stored password: " + passwordFromFile);
                return false;
            }

            // Obtain the array of messages in the inbox folder ordered by date in ascending order
            Message[] messages = inbox.getMessages();

            // Reverse the messages array so that the newest message is first
            ArrayUtils.reverse(messages);

            // Iterate through the messages
            for (Message message: messages) {
                // Get the email address of the sender
                String senderEmailAddress = ((InternetAddress) message.getFrom()[0]).getAddress();

                // If the email is from the client (Heart House Hospice)
                if (AuthenticationManager.isEmailFromClient(senderEmailAddress)) {
                    Log.i(CLASS_NAME, "Email from client found in inbox.");

                    // Extract the password from the email
                    String password = extractPasswordFromEmail(message);

                    Log.i(CLASS_NAME, "The password extracted from the email is: " + password);

                    // Only set the password if it wasn't already changed
                    if (!passwordChanged) {
                        AuthenticationManager.setPassword(password);

                        // Update the password in the file as well
                        writeToSharedPreferences(PASSWORD_KEY, password);

                        // Set the password changed flag to true
                        passwordChanged = true;
                    } else {
                        // Else delete the (older) email since the password has been changed already
                        message.setFlag(Flags.Flag.DELETED, true);
                        Log.i(CLASS_NAME, "Deleting an older email from the client that " +
                                "was received on: " + message.getReceivedDate());
                    }
                } else {
                    // Flag any emails that are not from the client to be deleted
                    message.setFlag(Flags.Flag.DELETED, true);

                    MimeMessage mimeMessage = (MimeMessage) message;
                    String from = ((InternetAddress) mimeMessage.getFrom()[0]).getAddress();
                    String subject = mimeMessage.getSubject();
                    String date = mimeMessage.getReceivedDate().toString();

                    Log.i(CLASS_NAME, "Deleting email that is not from the client:\n"
                                            + String.format("FROM: %s %n" +
                                                            "SUBJECT: %s %n" +
                                                            "DATE: %s %n",
                                                            from,
                                                            subject,
                                                            date));
                }
            }

            // Permanently remove all emails that are flagged to be deleted
            inbox.expunge();

            // Close the inbox folder and the message store
            inbox.close(true);
            store.close();

        } catch (MessagingException | IOException e) {
            Log.e(CLASS_NAME, "Error occurred while attempting to check for " +
                    "new passwords from the client.", e);
        }

        return passwordChanged;
    }

    /**
     * Log after checking for new passwords from the client.
     *
     * @param passwordChanged whether the password was changed or not
     */
    @Override
    protected void onPostExecute(Boolean passwordChanged) {
        String password = AuthenticationManager.getPassword();
        Log.i(CLASS_NAME, "The password was" + (passwordChanged ? "" : " not")
                + " changed. It is: " + password);

        // Display a toast if the password was changed
        if (passwordChanged) {
            Toast.makeText(HeartHouseHospiceApp.getAppContext(), "Password was changed.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Extract the password sent by the client from the given {@link Message}.
     *
     * @param message the email {@link Message}
     * @return the password sent by the client from the given {@link Message}
     * @throws IOException        thrown by the {@link javax.activation.DataHandler}
     * @throws MessagingException for other failures
     */
    private String extractPasswordFromEmail(Message message) throws IOException, MessagingException {
        Objects.requireNonNull(message, "Message object cannot be null.");
        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        return mimeMultipart.getBodyPart(0).getContent().toString().split("\\W+")[0];
    }
}