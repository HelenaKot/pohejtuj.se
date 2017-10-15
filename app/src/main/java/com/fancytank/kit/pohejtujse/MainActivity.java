package com.fancytank.kit.pohejtujse;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fancytank.kit.pohejtujse.api.dto.Hate;
import com.fancytank.kit.pohejtujse.holder.CameraViewHolder;
import com.fancytank.kit.pohejtujse.holder.LocaleViewHolder;
import com.fancytank.kit.pohejtujse.holder.TextViewHolder;

import java.util.List;

import static com.mindorks.paracamera.Camera.REQUEST_TAKE_PHOTO;

public class MainActivity extends AppCompatActivity implements LocaleViewHolder.LocaleClickedListener, TextViewHolder.TextListener {
    static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String SEND_DATA_BUNDLE_CODE = "sendDataReqCode";

    private LocaleViewHolder localeViewHolder;
    private TextViewHolder textViewHolder;
    private CameraViewHolder cameraViewHolder;

    private View send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localeViewHolder = new LocaleViewHolder(findViewById(R.id.locale_container), this);
        textViewHolder = new TextViewHolder(findViewById(R.id.text_container), this);
        cameraViewHolder = new CameraViewHolder(findViewById(R.id.camera_container), this);
        send = findViewById(R.id.send_container);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        send = findViewById(R.id.send_container);
        findViewById(R.id.send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRequest();
            }
        });
    }


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
    public void tryUpdateLocale() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateLocale();
            }
        }, 300);
    }

    private void updateLocale() {
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
                return;
            }
        }
        tryUpdateLocale();
//        askForGPS(this); todo later
    }


    private void goToRequest() {
        Intent intent = new Intent(this, SendActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SEND_DATA_BUNDLE_CODE, makeRequest());
        intent.putExtras(bundle);
        startActivity(intent);
        //todo ovveride pending transition
    }

    private Parcelable makeRequest() {
        Hate hateMsg = new Hate();
        hateMsg.coordinates = localeViewHolder.getCoordinates();
        hateMsg.text = textViewHolder.getText();
        return hateMsg;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) throws UnsupportedOperationException {
        tryUpdateLocale();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            cameraViewHolder.notifyImageTaken();
        }
    }

    @Override
    public void onTextChanged(boolean notEmpty) {
        if (notEmpty)
            send.setVisibility(View.VISIBLE);
    }
}
