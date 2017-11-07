package edu.orangecoastcollege.cs273.magicanswer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Brandy on 11/6/17.
 */

public class ShakeDetector implements SensorEventListener {

    private static final long ELAPSED_TIME = 1000;

    private static final float THRESHOLD = 20;

    private long previousShake;

    private OnShakeListener mListener;

    /**
     * Creates a new instance of a edu.orangecoastcollege.cs273.magicanswer.ShakeDetector, using an OnShakeListener.
     * @param listener listens for an accelerometer change.
     */
    public ShakeDetector(OnShakeListener listener) {
        mListener = listener;
    }

    /**
     * Called when there is a new sensor event.
     * @param sensorEvent The sensor event that changed.
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Ignore all other events, except ACCELEROMETERS
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Neutralize the effect of gravity (subtract it out from each value)
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;

            float netForce = (float) Math.sqrt(Math.pow(gForceX, 2) +  Math.pow(gForceY, 2) + Math.pow(gForceZ, 2));
            if(netForce >= THRESHOLD)
            {
                // Get current time
                long currentTime = System.currentTimeMillis();
                if(currentTime > previousShake + ELAPSED_TIME)
                {
                    // Reset the previous shake to current time
                    previousShake = currentTime;

                    // Register a shake event (it qualifies)
                    mListener.onShake();
                }
            }
        }
    }

    /**
     * Called when the accuracy of the registered sensor has changed. Unlike onSensorChanged(),
     * this is only called when the accuracy value changes.
     *
      * @param sensor The Sensor whose accuracy changed.
     * @param i The new accuracy of this sensor, one of <code>SensorManager.SENSOR_STATUS_*</code>
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing, not being used

    }

    // Define an interface for others to implement whenever a true shake occurs.
    // Interface = contract (method declarations WITHOUT implementation)
    // Some other class has to implement the method

    /**
     * Used for actions to take when a true shake occurs.
     */
    public interface OnShakeListener {
        void onShake();
    }
}
