package com.example.youssefamarti.twofactorauthentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GcmIntentService extends Service {
    public GcmIntentService() {


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
