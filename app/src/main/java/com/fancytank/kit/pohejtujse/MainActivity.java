package com.fancytank.kit.pohejtujse;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.fancytank.kit.pohejtujse.api.HateClient;
import com.fancytank.kit.pohejtujse.api.dto.Hate;
import com.fancytank.kit.pohejtujse.api.locale.LocaleViewHolder;
import com.fancytank.kit.pohejtujse.api.locale.TextViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocaleViewHolder.LocaleClickedListener {
    private HateClient hateClient;
    private LocaleViewHolder localeViewHolder;
    private TextViewHolder textViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localeViewHolder = new LocaleViewHolder(findViewById(R.id.locale_container), this);
        textViewHolder = new TextViewHolder(findViewById(R.id.text_container));

        hateClient = new HateClient();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;

    private void askPerm(Context thisActivity) {
        // Should we show an explanation?


        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        new AlertDialog.Builder(thisActivity)
                .setTitle("Localization Permission")
                .setMessage("this app needs localization permission")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                })
                .create()
                .show();

    }

    @Override
    public void onLocaleClicked() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPerm(this);
            return;
        }

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            } else {
                localeViewHolder.setLocale(bestLocation.getLatitude(), bestLocation.getLongitude());
            }
        }
//        askForGPS(this); todo later
    }

    private void sedRequest() {
        Hate dupa = new Hate();
        dupa.coordinates = localeViewHolder.getCoordinates();
        dupa.text = textViewHolder.getText();
        hateClient.postHate(dupa);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) throws UnsupportedOperationException {
        onLocaleClicked();
    }

}
