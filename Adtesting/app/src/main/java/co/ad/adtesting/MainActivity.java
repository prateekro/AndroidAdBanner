package co.ad.adtesting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    AdView mAdView;
    String appUnitID = " replace-with-app-ad-unit-ID-here ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.ad.adtesting.R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), appUnitID);

    }

    @Override
    protected void onStart() {
        super.onStart();

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.BOTTOM);

        // Create a banner ad
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(appUnitID);

        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // Optionally populate the ad request builder.
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        // Add the AdView to the view hierarchy.
        layout.addView(mAdView);

        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());

        setContentView(layout);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                //Trigger something when ad gets loaded
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                //Trigger something if ad fails to load - Generally used to reload the ad.

                /* Understand Which type of Error has caused Ad to not Load */
                switch (errorCode){
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR :
                        Log.d(TAG, "onAdFailedToLoad: ERROR_CODE_INTERNAL_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST :
                        Log.d(TAG, "onAdFailedToLoad: ERROR_CODE_INVALID_REQUEST");
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR :
                        Log.d(TAG, "onAdFailedToLoad: ERROR_CODE_NETWORK_ERROR");
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL :
                        Log.d(TAG, "onAdFailedToLoad: ERROR_CODE_NO_FILL");
                        break;
                    default:
                        Log.d(TAG, "onAdFailedToLoad: Error Code not known - No Reason known");
                        break;
                }

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                //Handle something when clicked on ad to leave application
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //Handle something when ad is closed - Useful with Full screen ads
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                //Handle something when clicked on ad to leave application
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }
}
