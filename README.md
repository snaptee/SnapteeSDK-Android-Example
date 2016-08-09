Snaptee SDK   :womans_clothes:  :necktie: :tada:
=================

## Requirements

Android minSdkVersion supported by SnapteeSDK is **18**, which correspond to android
**4.3**.

## Incorporate Snaptee SDK with your app

1. Create a directory structure like this
   ```
   your_android_app/
     libs/
       snaptee-sdk.aar
   ```
   `snaptee-sdk.aar` can be found in this repo at `snaptee-sdk-sample/libs/`

   Then let your app aware of Snaptee SDK by adding following snippest to your
   `build.gradle`:
   ``` gradle
   repositories {
        flatDir {
            dirs 'libs'
        }
        [ your other repositories ... ]
    }

    dependencies {
        compile(name:'snaptee-sdk', ext:'aar')
        [ your other dependencies ... ]
    }
    ```

2. Add Activities into `AndroidManifest.xml`
   ``` xml
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
   ```

3. Add required permission

   ``` xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
   ```

## Usage

1. Start DesignActivity with image URI

   ``` java
    Uri uri = // image URI;
    // newIntent(Context context, String affiliate, String appName, Uri image, String caption (optional), Locale language (optional), String name (optional), String email (optional))
    Intent intent = DesignActivity.newIntent(this, "sdk-demo", "SDK Sample App", uri, "caption", Locale.TRADITIONAL_CHINESE, "name", "email@example.com");
    startActivityForResult(intent, REQUEST_SNAPTEE);
   ```

2. Get the result

  1. Use local broadcast receiver

     The client app can register a local broadcast event to obtain an Order object which represents the order the user has made once checkout is completed.

     ```java
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
     ```

  2. On ActivityResult

     The client app can get all orders in onActivityResult() 

     ``` java
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
     ```

If you have any problem, the source of example app here may guide you.  Also feel free to conteact us!

## Example App

Want to test SnapteeSDK?
Find the apk in https://github.com/snaptee/SnapteeSDK-Android-Example/releases

It is generated from the source code in this repo, so you may also compile it yourself!  Just import it into AndroidStudio or run `./gradlew assembleRelease`
