package com.example.youssefamarti.twofactorauthentication;

// Import
import com.example.youssefamarti.twofactorauthentication.CommonUtilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

// Activity which will display the received data
public class MainActivity extends Activity {

    private String TAG = "** GCMPushDEMOAndroid**"; // Tag ID as String
    private TextView mDisplay; // Label which will output received data
    String regId = ""; // Regisration ID

    // Method which creates the layout
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNotNull(CommonUtilities.SENDER_ID, "SENDER_ID");
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        mDisplay = (TextView) findViewById(R.id.textView1);

        regId = GCMRegistrar.getRegistrationId(this);

        if (regId.equals("")) {
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            Log.v(TAG, "Already registered");
        }
        /**
         * call asYnc Task
         */
        new sendIdOnOverServer().execute();
        mDisplay.setText("RegId=" + regId);
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(getString(R.string.error_config,
                    name));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        GCMRegistrar.unregister(this);
    }

    // Background process class
    public class sendIdOnOverServer extends AsyncTask<string string="" void=""> {

        ProgressDialog pd = null;

        // This method executes code before the background task starts
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MainActivity.this, "Please wait",
                    "Loading please wait..", true);
            pd.setCancelable(true);

        }

        // Background process which won't interrupt the UI thread
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpResponse response = null;
                HttpParams httpParameters = new BasicHttpParams();
                HttpClient client = new DefaultHttpClient(httpParameters);
                String url = "http://10.0.0.30//parsing/GCM.php?" + "&regID="
                        + regId;
                Log.i("Send URL:", url);
                HttpGet request = new HttpGet(url);

                response = client.execute(request);

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));

                String webServiceInfo = "";
                while ((webServiceInfo = rd.readLine()) != null) {
                    Log.d("****Status Log***", "Webservice: " + webServiceInfo);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        // Method which executes code when the background process has finished
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

        }

    }

}
