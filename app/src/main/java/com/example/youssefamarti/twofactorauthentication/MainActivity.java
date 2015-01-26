package com.example.youssefamarti.twofactorauthentication;



import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.gcm.GoogleCloudMessaging;



// Activity which will display the received data
public class MainActivity extends Activity {

    private String TAG = "** GCMPushDEMOAndroid**"; // Tag ID as String
    private TextView mDisplay;                      // Label which will output received data
    String regId = "";                              // Regisration ID
    GoogleCloudMessaging gcm;
    // Method which creates the layout
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplay = (TextView)findViewById(R.id.ABC);
        gcm = GoogleCloudMessaging.getInstance(this);


    }

    public void Login(View sender) {
        // Try to get the registered user by device ID
        try {
            regId = gcm.register(CommonUtilities.getSenderId(this));

            // If not registered in GCM, register him in a background Thread
            if(regId.isEmpty()) {
                registerInBackground();
            }

            //if device isn't reg it will reg in background thread with gcm
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the regisration failed, print 3 dots
        if(regId.isEmpty()) {
            regId = "...";
        }

        mDisplay.setText("Registration ID: " + regId);
    }
    public void Logout(View sender) {
        // Remove the registration ID from the textview
        mDisplay.setText("Not logged on to VPN ");
    }
    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String[] params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    }
                    regId = gcm.register(CommonUtilities.getSenderId(MainActivity.this));
                    msg = "Device registered, registration ID=" + regId;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    /*
                    ****** sendRegistrationIdToBackend(); ******
                    */
                    // *** No code yet for sending register ***

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(MainActivity.this, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                // Display GCM message
                mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null); // Start the background thread

}
        /**
         * Stores the registration ID and app versionCode in the application's
         * {@code SharedPreferences}.
         *
         * @param context application's context.
         * @param regId registration ID
         */
        private void storeRegistrationId(Context context, String regId) {
            final SharedPreferences prefs = getGCMPreferences(context);
            // Save the regisration ID in the app config file
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("r", regId);
            editor.commit();
    }

        private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
            return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

}
