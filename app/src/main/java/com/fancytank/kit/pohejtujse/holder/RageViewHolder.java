package com.fancytank.kit.pohejtujse.holder;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancytank.kit.pohejtujse.R;

import static android.content.Context.SENSOR_SERVICE;

// 10/15/2017.
public class RageViewHolder implements SensorEventListener {
    private final static float G = 9.7803f;
    SensorManager sensorManager;

    ImageView rageImage;
    TextView debugText;

    private float accelerationSum = 0;
    private float maxScale = 100f;
    private RageType[] drawable = {
            new RageType(R.drawable.a0, -100f),
            new RageType(R.drawable.a1, 6f),
            new RageType(R.drawable.a2, 10f),
            new RageType(R.drawable.a3, 20f),
            new RageType(R.drawable.a4, 40f),
            new RageType(R.drawable.a5, 60f),
            new RageType(R.drawable.a6, 80f),
    };
    int rageIndex = -1;

    private static class RageType {
        @DrawableRes
        final int drawableRes;
        final float rageOMeter;

        public RageType(int drawableRes, float rageOMeter) {
            this.drawableRes = drawableRes;
            this.rageOMeter = rageOMeter;
        }
    }

    public RageViewHolder(View hateView, Activity root) {
        rageImage = hateView.findViewById(R.id.hate_image);
        debugText = hateView.findViewById(R.id.debug_text);

        sensorManager = (SensorManager) root.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    public float getRage() {
        float normalized = accelerationSum / maxScale;
        sensorManager.unregisterListener(this);
        return normalized > maxScale ? maxScale : normalized;
    }

    public void unregisterLitener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            debugText.setText(sensorEvent.values[0] + "\n" + sensorEvent.values[1] + "\n" + sensorEvent.values[2] + "\naccelerationSum :" + accelerationSum);
            float sum = Math.abs(sensorEvent.values[0] + sensorEvent.values[1] + sensorEvent.values[2]) - G;
            if (accelerationSum < sum) {
                accelerationSum = sum;
                updateRageIndex();
            }
        }
    }

    private void updateRageIndex() {
        for (int index = 0; index < drawable.length - 1; index++) {
            if (rageIndex <= index && accelerationSum > drawable[index++].rageOMeter) {
                rageIndex = index;
                updateImage(index);
                System.out.println(index);
                break;
            }
        }
    }

    private void updateImage(int i) {
        rageImage.setImageResource(drawable[i].drawableRes);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
