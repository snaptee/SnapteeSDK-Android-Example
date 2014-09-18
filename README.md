Snaptee SDK
=================

To install:

1) Import Snaptee SDK to eclipse project

2) Link your project with Snaptee SDK

3) Add Activity in Manifest

    <activity 
        android:name="co.snaptee.sdk.DesignActivity"
        android:label="@string/co_snaptee_design"
        android:screenOrientation="portrait"
        android:theme="@style/Theme.SnapteeSDK">
    </activity>

    <activity 
        android:name="co.snaptee.sdk.OrderActivity"
        android:label="@string/co_snaptee_order"
        android:screenOrientation="portrait"
        android:theme="@style/Theme.SnapteeSDK">
    </activity>

    <activity
        android:name="co.snaptee.sdk.PayPalActivity"
        android:label="@string/co_snaptee_paypal"
        android:screenOrientation="portrait"
        android:theme="@style/Theme.SnapteeSDK">
    </activity>

    <activity 
        android:name="co.snaptee.sdk.ReceiptActivity"
        android:label="@string/co_snaptee_receipt"
        android:screenOrientation="portrait"
        android:theme="@style/Theme.SnapteeSDK">
    </activity>

4) Add required permission

	<uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

5) Copy "Brown-Bold.otf" and "Brown-Regular" into assets/fonts folder

6) Import Stripe SDK to eclipse project

7) Link your project with Stripe SDK

Usage:

1) Start DesignActivity  with image URI

    Uri uri = // image URI;
    String appName = ""; //app name
    Intent intent = new Intent(this, DesignActivity.class);
    intent.putExtra(DesignFragment.EXTRA_IMAGE, uri);
    intent.putExtra(DesignActivity.EXTRA_APP_NAME, appName);
    startActivity(intent);
