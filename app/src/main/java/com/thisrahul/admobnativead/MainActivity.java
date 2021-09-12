package com.thisrahul.admobnativead;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

public class MainActivity extends AppCompatActivity {

    private NativeAd nativeAd;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this);

        nativeAd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void nativeAd() {
        AdLoader adloader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(ntiveAd -> {
                    // Show the ad.
                    nativeAd = ntiveAd;
                    setLayoutOfNativeAd();
                    Toast.makeText(this, "forNativeAd", Toast.LENGTH_SHORT).show();
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                        Toast.makeText(MainActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        nativeAd.destroy();
                        Toast.makeText(MainActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                        Toast.makeText(MainActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        Toast.makeText(MainActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        Toast.makeText(MainActivity.this, "onAdClicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Toast.makeText(MainActivity.this, "onAdImpression", Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adloader.loadAd(new AdRequest.Builder().build());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setLayoutOfNativeAd() {
        // Assumes you have a placeholder FrameLayout in your View layout
        // (with id fl_adplaceholder) where the ad is to be placed.
        FrameLayout frameLayout =
                findViewById(R.id.fl_adplaceholder);
        // Assumes that your ad layout is in a file call native_ad_layout.xml
        // in the res/layout folder
//        NativeAdView adView = (NativeAdView) getLayoutInflater()
//                .inflate(R.layout.native_ad_layout, null);
        // This method sets the text, images and the native ad, etc into the ad
        // view.
        showNativeAdView(frameLayout);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNativeAdView(FrameLayout frameLayout) {

        // Inflate a layout and add it to the parent ViewGroup.
        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        NativeAdView adView = (NativeAdView) inflater
                .inflate(R.layout.native_ad_layout, null);

        // Locate the view that will hold the headline, set its text, and call the
        // NativeAdView's setHeadlineView method to register it.
        TextView headlineView = adView.findViewById(R.id.ad_headline);
        headlineView.setText(nativeAd.getHeadline());
        adView.setHeadlineView(headlineView);


        // Repeat the above process for the other assets in the NativeAd
        // using additional view objects (Buttons, ImageViews, etc).

        // If the app is using a MediaView, it should be
        // instantiated and passed to setMediaView. This view is a little different
        // in that the asset is populated automatically, so there's one less step.
        MediaView mediaView = (MediaView) adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        mediaView.setMediaContent(nativeAd.getMediaContent());
        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

        // Call the NativeAdView's setNativeAd method to register the
        // NativeAdObject.
        adView.setNativeAd(nativeAd);

        frameLayout.removeAllViews();
        frameLayout.addView(adView);
    }
}