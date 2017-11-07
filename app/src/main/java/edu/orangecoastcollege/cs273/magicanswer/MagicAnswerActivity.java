package edu.orangecoastcollege.cs273.magicanswer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity allows the user to enter a question into the EditText.
 * When the device is "shaken" according to the <code>ShakeDetector</code>,
 * a random answer will be displayed below the quesiton.
 *
 * @author Brian Wegener
 * @version 1.0
 *
 * Created on 11/2/2017
 */
public class MagicAnswerActivity extends AppCompatActivity {

    private MagicAnswer magicAnswer;
    private TextView answerTextView;
    private EditText questionEditText;

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    // Create a reference to our ShakeDetector
    private ShakeDetector mShakeDetector;


    /**
     * Initializes <code>MagicAnswerActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized
     *                           after being previously shut down. otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_answer);

        // TASK 1: SET THE REFERENCES TO THE LAYOUT ELEMENTS
        questionEditText = (EditText) findViewById(R.id.questionEditText);
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        // TASK 2: CREATE A NEW MAGIC ANSWER OBJECT
        magicAnswer = new MagicAnswer(this);

        // TASK 3: REGISTER THE SENSOR MANAGER AND SETUP THE SHAKE DETECTION
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener()
        {
            @Override
            public void onShake()
            {
                if(!TextUtils.isEmpty(questionEditText.getText().toString())) displayMagicAnswer();
                else
                    Toast.makeText(MagicAnswerActivity.this, "You must ask a question!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * Displays a random answer to the screen.
     */
    public void displayMagicAnswer()
    {
        String randomAnswer = magicAnswer.getRandomAnswer();
        answerTextView.setText(randomAnswer);
    }

    /**
     * Registers the ShakeDetector for the accelerometer at a rate suitable for the UI.
     */
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }


}
