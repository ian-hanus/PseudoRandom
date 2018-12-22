package com.example.ianha.randomgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button myButton;
    private int lower, upper;
    private EditText lowerInput, upperInput;
    private TextView javaReturn, personalReturn, sampleReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lowerInput = (EditText) findViewById(R.id.lowerBound);
        upperInput = (EditText) findViewById(R.id.upperBound);
        myButton = (Button) findViewById(R.id.runButton);
        javaReturn = (TextView) findViewById(R.id.javaReturn);
        personalReturn = (TextView) findViewById(R.id.personalReturn);
        sampleReturn = (TextView) findViewById(R.id.sampleReturn);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lower = Integer.valueOf(lowerInput.getText().toString());
                upper = Integer.valueOf(upperInput.getText().toString());

                if(lower > upper){
                    personalReturn.setText("Lower bound must be less than or equal to upper bound.");
                    javaReturn.setText(" ");
                    sampleReturn.setText(" ");
                }
                else{
                    String javaRand = Integer.toString(randGenJava(lower, upper));
                    String timeRand = Integer.toString(randGenTime(lower, upper));
                    String sampleRand = Integer.toString(randGenSample(lower, upper));

                    javaReturn.setText("Using java.util.random(): " + javaRand);
                    personalReturn.setText("Using modulus of the current time in microseconds : " + timeRand);
                    sampleReturn.setText("Using high frequency sampling of sine squared wave: " + sampleRand);
                }
        }
        });
    }

    /*
    Using Java's built in random function; could also create array of range, Collections.shuffle(),
    and get the first element
     @param lowerBound
        -The lower bound of the desired range
     @param upperBound
        -The upper bound of the desired range
      @return
        -Single random integer in the desired range
     */
    public int randGenJava(int lowerBound, int upperBound) {
        int randRange = (upperBound + 1) - lowerBound;
        Random rand = new Random();
        int needConvert = rand.nextInt(randRange);
        int ret = needConvert + lowerBound;
        return ret;
    }

    /*
    Using modulus of JVM's universal time in milliseconds
    Problem if the range is too large and generating multiple random numbers
     @param lowerBound
        -The lower bound of the desired range
     @param upperBound
        -The upper bound of the desired range
      @return
        -Single random integer in the desired range
     */
    public int randGenTime(int lowerBound, int upperBound){
        int randRange = upperBound - lowerBound + 1;
        long currentTime = Math.abs(System.currentTimeMillis());
        return (int) (currentTime % randRange + lowerBound);
    }

    /*
    Sampling sin squared wave at a very high frequency. Accounts for small differences in changes
    of time.
     @param lowerBound
        -The lower bound of the desired range
     @param upperBound
        -The upper bound of the desired range
      @return
        -Single random integer in the desired range
     */
    public int randGenSample(int lowerBound, int upperBound){
        int randRange = upperBound - lowerBound;
        int currentTime = (int) System.currentTimeMillis();
        int sampleAmp = (int) Math.round(randRange * Math.pow(Math.sin(23991680.31451 * currentTime), 2));
        return sampleAmp + lowerBound;
    }
}
