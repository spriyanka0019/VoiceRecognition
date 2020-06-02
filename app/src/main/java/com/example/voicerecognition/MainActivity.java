package com.example.voicerecognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class MainActivity extends AppCompatActivity {
//
//    private TextView txvResult;
//    private ImageView imageView;
//    SpeechRecognizer speechRecognizer;
//    Intent speechrecognizerintent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        txvResult = (TextView) findViewById(R.id.text);
//        imageView = (ImageView) findViewById(R.id.speak);
//    }
//
//    public void getSpeechInput(View view) {
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
//                try {
//                    startActivityForResult(intent, 10);
//                } catch (ActivityNotFoundException a) {
//                    Toast.makeText(getApplicationContext(),
//                            "Sorry your device not supported",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case 10:
//                if (resultCode == RESULT_OK && data != null) {
//                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    txvResult.setText(result.get(0));
////                    if (result.contains("on"))
//                    if (txvResult.getText().toString().contains("on")) {
//                        Toast.makeText(getApplicationContext(), "Turning on", Toast.LENGTH_SHORT).show();
//                    }
//                    if (txvResult.getText().toString().contains("off")) {
//                        Toast.makeText(getApplicationContext(), "Turning off", Toast.LENGTH_SHORT).show();
//                    }
//                    if (txvResult.getText().toString().contains("music")) {
//                        Toast.makeText(getApplicationContext(),  "Playing some music", Toast.LENGTH_SHORT).show();
//                    }
//                    if (txvResult.getText().toString().contains("map")) {
//                        Toast.makeText(getApplicationContext(), "Opening the Google Map", Toast.LENGTH_SHORT).show();
//                    }
//                    if (txvResult.getText().toString().contains("brake")) {
//                        Toast.makeText(getApplicationContext(), " Please press the brake", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }
//
//
//}

public class MainActivity extends AppCompatActivity {

    boolean TurnOn_Off = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        final EditText editText = findViewById(R.id.editText);

        String str = "Turn on the fm 90.2 and volume at 3";



//        String line = "Turn on the AC at 18";
//        Pattern numberPat = Pattern.compile("\\d+");
//        Matcher matcher1 = numberPat.matcher(line);
//
//        Pattern stringPat = Pattern.compile("Turn on ", Pattern.CASE_INSENSITIVE);
//        Matcher matcher2 = stringPat.matcher(line);
//
//        if (matcher1.find() && matcher2.find())
//        {
//            int number = Integer.parseInt(matcher1.group());
//            System.out.println(number + " squared = " + (number * number));
//        }

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    editText.setText(matches.get(0));




                if (editText.getText().toString().contains("turn on")) {
                     TurnOn_Off = false;
                        if (editText.getText().toString().contains("AC")){
                            String line = editText.getText().toString();
                            System.out.println("Text is"+line);

                            Pattern numberPat = Pattern.compile("\\d+");
                            Matcher matcher1 = numberPat.matcher(line);

                            Pattern stringPat = Pattern.compile("turn on ", Pattern.CASE_INSENSITIVE);
                            Matcher matcher2 = stringPat.matcher(line);

                            if (matcher1.find() && matcher2.find())
                            {
                                int number = Integer.parseInt(matcher1.group());
                                Toast.makeText(getApplicationContext(),"Turning on AC at "+number,Toast.LENGTH_LONG).show();

                            }

                    }
                    if (editText.getText().toString().contains("FM")){
                        String line = editText.getText().toString();
                        System.out.println("Text is"+line);

//                        Pattern numberPat = Pattern.compile("\\d*\\.?\\d+");
//                        Matcher matcher1 = numberPat.matcher(line);
//
//                        Pattern stringPat = Pattern.compile("turn on ", Pattern.CASE_INSENSITIVE);
//                        Matcher matcher2 = stringPat.matcher(line);


                        line = line.replaceAll("[^.0-9]+", " ");
                        System.out.println(Arrays.asList(line.trim().split(" ")));
                        List list = Arrays.asList(line.trim().split(" "));
                        double  number1 = Double.parseDouble((String) list.get(0));
                        double number2 = Double.parseDouble((String) list.get(1));

                        if(number1>80 && number1<110){
                            System.out.println("Number in range");
                        }
                        if(number2>0 && number1<10){
                            System.out.println("Number not in range");

                        }
                        System.out.println("Turning on Fm at "+number1+" and volume at "+number2);
                        Toast.makeText(getApplicationContext(),"Turning on fm"+number1+" And volume at "+number2,Toast.LENGTH_LONG).show();


//
//                        if (matcher1.find() && matcher2.find())
//                        {
//                            Double number = Double.parseDouble(matcher1.group());
//                            Toast.makeText(getApplicationContext(),"Turning on fm"+number1+" And volume at "+number2,Toast.LENGTH_LONG).show();
//
//                        }
                    }

                    if (editText.getText().toString().contains("Bluetooth")){
                        Toast.makeText(getApplicationContext(),"Turning on Bluetooth ",Toast.LENGTH_LONG).show();
                    }
                }

                ///Commands for turning off....
                if (editText.getText().toString().contains("turn off")) {
                    TurnOn_Off = false;
                    if (editText.getText().toString().contains("AC")){
                        String line = editText.getText().toString();
                        System.out.println("Text is"+line);

                        Pattern numberPat = Pattern.compile("\\d+");
                        Matcher matcher1 = numberPat.matcher(line);

                        Pattern stringPat = Pattern.compile("turn off ", Pattern.CASE_INSENSITIVE);
                        Matcher matcher2 = stringPat.matcher(line);

                        if (matcher1.find() && matcher2.find())
                        {
                            int number = Integer.parseInt(matcher1.group());
                            Toast.makeText(getApplicationContext(),"Turning off AC  "+number,Toast.LENGTH_LONG).show();

                        }

                    }
                    if (editText.getText().toString().contains("FM")){

                        String line = editText.getText().toString();
                        System.out.println("Text is"+line);

                        Pattern numberPat = Pattern.compile("\\d*\\.?\\d+");
                        Matcher matcher1 = numberPat.matcher(line);

                        Pattern stringPat = Pattern.compile("turn off ", Pattern.CASE_INSENSITIVE);
                        Matcher matcher2 = stringPat.matcher(line);

                        if (matcher1.find() && matcher2.find())
                        {
                            Double number = Double.parseDouble(matcher1.group());
                            Toast.makeText(getApplicationContext(),"Turning off  "+number,Toast.LENGTH_LONG).show();

                        }
                    }

                    if (editText.getText().toString().contains("Bluetooth")){
                        Toast.makeText(getApplicationContext(),"Turning off Bluetooth ",Toast.LENGTH_LONG).show();
                    }
                }
//                if (editText.getText().toString().contains("off")) {
//                    Toast.makeText(getApplicationContext(), "Turning off", Toast.LENGTH_SHORT).show();
//                }
//                if (editText.getText().toString().contains("music")) {
//                    Toast.makeText(getApplicationContext(), "Playing some music", Toast.LENGTH_SHORT).show();
//                }
                if (editText.getText().toString().contains("map")) {
                    Toast.makeText(getApplicationContext(), "Opening the Google Map", Toast.LENGTH_SHORT).show();
                }
//                if (editText.getText().toString().contains("brake")) {
//                    Toast.makeText(getApplicationContext(), " Please press the brake", Toast.LENGTH_SHORT).show();
//                }


            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("Listening...");

                        break;
                }
                return false;
            }
        });
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}
