package com.reactnativebackgroundactions;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = BackgroundLocationModule.NAME)
public class BackgroundLocationModule extends ReactContextBaseJavaModule {
    public static final String NAME = "BackgroundLocation";
    private static final String CHANNEL_ID = "LocationTrackerChannel";
    
    public BackgroundLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void startTracking(String serverUrl) {
        Context context = getReactApplicationContext();
        Intent serviceIntent = new Intent(context, LocationTrackingService.class);
        serviceIntent.putExtra("serverUrl", serverUrl);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }

    @ReactMethod
    public void stopTracking() {
        Context context = getReactApplicationContext();
        Intent serviceIntent = new Intent(context, LocationTrackingService.class);
        context.stopService(serviceIntent);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("PERMISSIONS", new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE
        });
        return constants;
    }
}
