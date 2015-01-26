package com.example.youssefamarti.twofactorauthentication;

import android.content.Context;
import android.provider.Settings;


public final class CommonUtilities {

    // Returns the device ID which is unique for registration
    static public String getSenderId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

}
