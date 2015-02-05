Snaptee SDK
=================

To install:

1) Import Snaptee SDK to project

2) Link your project with Snaptee SDK

3) Add Activities in Manifest

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
        android:name="co.snaptee.sdk.PaymentActivity"
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
    
    <activity
        android:name="co.snaptee.sdk.WebViewActivity"
        android:label=""
        android:screenOrientation="portrait"
        android:theme="@style/Theme.SnapteeSDK">
    </activity>

4) Add required permission

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

5) Copy "Brown-Bold.otf" and "Brown-Regular" into assets/fonts folder

6) Link your project with Stripe SDK

Usage:

1) Start DesignActivity with image URI

    Uri uri = // image URI;
    // newIntent(Context context, String affiliate, String appName, Uri image, String caption (optional), Locale language (optional), String name (optional), String email (optional))
    Intent intent = DesignActivity.newIntent(this, "sdk-demo", "SDK Sample App", uri, "caption", Locale.TRADITIONAL_CHINESE, "name", "email@example.com");
    startActivityForResult(intent, REQUEST_SNAPTEE);

2) Get the result

2.1 Use local broadcast receiver

The client app can register a local broadcast event to obtain an Order object which represents the order the user has made once checkout is completed.

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            OrderInfo order = intent.getParcelableExtra(Param.RESULT_ORDER);
            Log.d("SNAPTEE", "New Order Id: " + order.getId());
            Log.d("SNAPTEE", "New Tracking Url: " + info.getTrackingUrl());
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Param.EVENT_ORDER));
    }

2.2 On ActivityResult

The client app can get all orders in onActivityResult() 

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_SNAPTEE:
                ArrayList<OrderInfo> orders = data.getParcelableArrayListExtra(Param.RESULT_ORDERS);
                Iterator<OrderInfo> iterator = orders.iterator();
                while(iterator.hasNext()) {
                    OrderInfo info = iterator.next();
                    Log.d("SNAPTEE", "Order ID: " + info.getId());
                    Log.d("SNAPTEE", "Order Tracking Url: " + info.getTrackingUrl());
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

Example App:
snaptee-sdk-sample-production-20141114v1.apk
