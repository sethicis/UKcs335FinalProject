package com.example.sensor;

import android.app.Activity;
import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

//Important for catching onSensor Events. The class MUST implement sensorEventListener
//From my limited research it looks like the sensor listener and sensormanager MUST be implemented and handled in an activity.
public class MainActivity extends Activity implements SensorEventListener{
    private SensorManager mSensorManager; //Important for catching onSensor Events
    private Sensor mAcceleration; //Important for catching onSensor Events
    private final String TESTING = "ACCEL_TEST"; //Debug constant
    private float most; //used to keep track of the largest velocity magnitude we saw.
    private Timer myTimer; //Used for grace period
    private CustomHandler myHandler;//Used for grace period
    private boolean timing = false; //Grace period variable. While false we accept sensor input.

    // Used for grace period check
    class CustomTimerTask extends TimerTask {
        @Override
        public void run(){
            myHandler.sendEmptyMessage(0);
        }
    }
    //Message handler designed to receive the scheduled events sent from child threads
    class CustomHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            timing = false; // Ends the grace period
            myTimer.cancel();
            myTimer = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Important for catching onSensor Events.
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); //Initalizes the sensor service
        mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); //Tells the sensor service what sensor we want to use
        myHandler = new CustomHandler(); // used for the grace period check
        myTimer = new Timer(); //Use for the grace period check
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Important for catching onSensor Events so we don't kill the battery
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //Important for catching onSensor Events so we don't kill the battery.
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    //Important for catching onSensor Events
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float limit = -8f; //Filter insures that only events with a forward velocity magnitude greater than 8 are accepted
        //Grace period has to be implemented to avoid getting too many sensor events
        if (event.values[2] < limit && !timing){ //While we aren't in the grace period between throws don't accept input
            myTimer = new Timer();
            CustomTimerTask customTimerTask = new CustomTimerTask();
            // Wait time between reading sensor events = 600 ms
            myTimer.schedule(customTimerTask, 600);
            timing = true;
            most = event.values[2];
            String output;
            output = "Most: " + Float.toString(most);
            Log.d(TESTING,output);
            most = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;

        }
    }

}
