package com.example.youssefamarti.twofactorauthentication;

import android.content.Context;
import android.provider.Settings;


public final class CommonUtilities {
    //put here your sender Id

    static public String getSenderId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

}


