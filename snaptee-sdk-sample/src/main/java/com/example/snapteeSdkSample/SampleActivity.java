package com.example.snapteeSdkSample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import co.snaptee.sdk.DesignActivity;
import co.snaptee.sdk.Param;
import co.snaptee.sdk.model.OrderInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class SampleActivity extends Activity {

    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_SNAPTEE = 2;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            OrderInfo order = intent.getParcelableExtra(Param.RESULT_ORDER);
            Log.d("SNAPTEE", "New Order: " + order);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        findViewById(R.id.button_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Param.EVENT_ORDER));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void pickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    launchDesignActivity(data);
                } else {
                    finish();
                }
                break;
            case REQUEST_SNAPTEE:
                ArrayList<OrderInfo> orders = data.getParcelableArrayListExtra(Param.RESULT_ORDERS);
                Iterator<OrderInfo> iterator = orders.iterator();
                while(iterator.hasNext()) {
                    OrderInfo info = iterator.next();
                    Log.d("SNAPTEE", "Order: " + info);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void launchDesignActivity(Intent data) {
        Uri uri = data.getData();
        Intent intent = DesignActivity.newIntent(this, "sdk-demo", "SDK Sample App", uri, "caption", null, "name", "email@example.com");
        startActivityForResult(intent, REQUEST_SNAPTEE);
    }
}
