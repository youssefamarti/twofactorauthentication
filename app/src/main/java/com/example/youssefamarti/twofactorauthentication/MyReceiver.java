package com.example.youssefamarti.twofactorauthentication;

// Imports
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

// Class which receives notifications
public class MyReceiver extends BroadcastReceiver {
    GoogleCloudMessaging gcm;
    String messageType;
    // Constructor of class MyReceiver
    public MyReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        gcm = GoogleCloudMessaging.getInstance(context);
        messageType = gcm.getMessageType(intent);

        // Filter messages based on message type. It is likely that GCM will be extended in the future
        // with new message types, so just ignore message types you're not interested in, or that you
        // don't recognize.
        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))     {
            Log.d("YA","ERROR!");
        // It's an error.
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            Log.d("YA","DELETE!");
        // Deleted messages on the server.
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            Log.d("YA","MESSAGE!");
        // It's a regular GCM message, do some work.
        }

    }
}
