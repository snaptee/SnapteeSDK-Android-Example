package com.example.snapteeSdkSample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import co.snaptee.sdk.DesignActivity;

public class SampleActivity extends Activity {

    private static final int REQUEST_PICK_IMAGE = 1;

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

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void launchDesignActivity(Intent data) {
        Uri uri = data.getData();
        Intent intent = new Intent(this, DesignActivity.class);
        intent.putExtra(DesignActivity.EXTRA_IMAGE, uri);
        intent.putExtra(DesignActivity.EXTRA_APP_NAME, "SDK Sample App");
        startActivity(intent);
    }
}
