package com.example.voicerecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
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
//        imageView = (ImageView) findViewById(R.id.listen);
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
    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FMFrequency = "FMFrequency";
    public static final String VolumeLevel = "VolumeLevel";
    public static final String AC_degree = "ACdegree";
    public static final String Drive_Mode = "Drive_Mode";


    private static final String TAG = "TTS Engine";
    TextView txvResult;
    private ImageView imageView;
    SpeechRecognizer speechRecognizer;
    Intent speechrecognizerintent;
    public static Boolean Iswordfound = false;
    private TextToSpeech mTTs;


    float fmfrequency = 0.0f;
    float volumeLevel = 10.0f;
    int ac_degree = 18;
    String drivemode = "Eco";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.listen);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(Locale.ENGLISH);

                    //Good indian female voice pitch 0.7 and speed = 0.2
//                            mTtsEngine.setLanguage(Locale.GERMAN);
                    mTTs.setPitch((float) 0.7);
                    mTTs.setSpeechRate((float) 0.9);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not Supported");
                    }

                } else {
                    Log.w(TAG, "Could not open TTS Engine (onInit status=" + status
                            + "). Ignoring text to speech");
                    mTTs = null;
                }

            }
        });
    }


    public void speak(String string) {
        mTTs.speak(string, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTs != null) {
            mTTs.stop();
            mTTs.shutdown();
        }
        super.onDestroy();
    }

    public void getSpeechInput(View view) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iswordfound = false;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, 10);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void save_preferences() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(AC_degree, ac_degree);
            editor.putFloat(FMFrequency, fmfrequency);
            editor.putFloat(VolumeLevel, volumeLevel);
            editor.apply();
        }

    }

    public void retrieve_preferences() {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ac_degree = sharedPreferences.getInt(AC_degree, ac_degree);
        fmfrequency = sharedPreferences.getFloat(FMFrequency, fmfrequency);
        volumeLevel = sharedPreferences.getFloat(VolumeLevel, volumeLevel);
        System.out.println(volumeLevel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                retrieve_preferences();

                //displaying the first match
                if (result != null)
                    txvResult.setText(result.get(0));

                if (txvResult.getText().toString().contains("turn on")) {
                    if (txvResult.getText().toString().contains("AC") && !Iswordfound) {
                        Iswordfound = true;

                        String line = txvResult.getText().toString();

                        System.out.println("Text is" + line);

                        Pattern numberPat = Pattern.compile("\\d+");
                        Matcher matcher1 = numberPat.matcher(line);

                        Pattern stringPat = Pattern.compile("turn on ", Pattern.CASE_INSENSITIVE);
                        Matcher matcher2 = stringPat.matcher(line);

                        if (matcher1.find() && matcher2.find()) {
                            ac_degree = Integer.parseInt(matcher1.group());
                            Toast.makeText(getApplicationContext(), "Turning on AC at " + ac_degree, Toast.LENGTH_LONG).show();
                        }
                        speak("Setting the AC at " + ac_degree + " degree celcius");
                        //Call the highlighter function
                        String arraywords[] = {"AC",String.valueOf(ac_degree)};
                        TextHighlighter(arraywords);

                    }

                    if (txvResult.getText().toString().contains("FM") || txvResult.getText().toString().equalsIgnoreCase("Volume") && !Iswordfound) {
                        String line = txvResult.getText().toString();
                        Iswordfound = true;

                        boolean checkFMNumber1 = false;
                        boolean checkFMNumber2 = false;

                        boolean checkVolume1 = false;
                        boolean checkVolume2 = false;

                        float FMValue = fmfrequency;
                        float VolumeLevel = volumeLevel;


                        line = line.replaceAll("[^.0-9]+", " ");
                        String[] string = line.split("\\s+");
                        System.out.println(Arrays.toString(string) + string.length);

                        if (string.length == 2) {
                            fmfrequency = Float.parseFloat(string[1]);
                            System.out.println(fmfrequency);
                            checkFMNumber1 = isBetween(fmfrequency, 80, 110);
                            checkVolume1 = isBetween(fmfrequency, 1, 10);

                            if (checkFMNumber1) {
                                Toast.makeText(getApplicationContext(), "Turn on fm " + fmfrequency, Toast.LENGTH_LONG).show();
                            } else if (checkVolume1) {
                                Toast.makeText(getApplicationContext(), "Turn on Volume " + fmfrequency, Toast.LENGTH_LONG).show();
                            }


                        } else if (string.length == 4 || string.length == 3) {
                            fmfrequency = Float.parseFloat(string[1]);
                            volumeLevel = Float.parseFloat(string[2]);
                            System.out.println(fmfrequency);

                            checkFMNumber1 = isBetween(fmfrequency, 80, 110);
                            checkFMNumber2 = isBetween(volumeLevel, 80, 110);

                            //Assigning the value of FM
                            if (checkFMNumber1) {
                                FMValue = fmfrequency;
                            } else if (checkFMNumber2) {
                                FMValue = volumeLevel;
                            } else
                                FMValue = 98.3f;

                            checkVolume1 = isBetween(fmfrequency, 1.0, 10.0);
                            checkVolume2 = isBetween(volumeLevel, 1.0, 10.0);

                            //Assigning the value of VolumeLevel
                            if (checkVolume1) {
                                VolumeLevel = (int) fmfrequency;
                            } else if (checkVolume2) {
                                VolumeLevel = volumeLevel;
                            } else
                                VolumeLevel = 5;

                            Toast.makeText(getApplicationContext(), "Turning on fm " + fmfrequency + " And volume  " + volumeLevel, Toast.LENGTH_LONG).show();

                        } else if (string.length == 0) {
                            Toast.makeText(getApplicationContext(), "Turning on ", Toast.LENGTH_LONG).show();
                        }

                        //Call the highlighter function
                        String arraywords[] = {"FM",String.valueOf(fmfrequency), "volume",String.valueOf((int)volumeLevel)};
                        TextHighlighter(arraywords);
                        speak("Turning on FM " + fmfrequency + "and Volume at " + (int)volumeLevel );
                    }

                    if (txvResult.getText().toString().contains("Bluetooth") && !Iswordfound) {
                        Iswordfound = true;
                        Toast.makeText(getApplicationContext(), "Turning on Bluetooth ", Toast.LENGTH_LONG).show();
                        //Call the highlighter function
                        String arraywords[] = {"Bluetooth"};
                        TextHighlighter(arraywords);
                        speak("Turning on Bluetooth");


                    }
                }

                ///Commands for turning off....
                else if (txvResult.getText().toString().contains("turn off") ) {
                    if (txvResult.getText().toString().contains("AC") &&!Iswordfound) {
                        Iswordfound = true;
                        String line = txvResult.getText().toString();
                        System.out.println("Text is" + line);

                        Pattern numberPat = Pattern.compile("\\d+");
                        Matcher matcher1 = numberPat.matcher(line);

                        Pattern stringPat = Pattern.compile("turn off ", Pattern.CASE_INSENSITIVE);
                        Matcher matcher2 = stringPat.matcher(line);

                        if (matcher1.find() && matcher2.find()) {
                            int number = Integer.parseInt(matcher1.group());
                            Toast.makeText(getApplicationContext(), "Turning off AC  " + number, Toast.LENGTH_LONG).show();
                        }
                        //Call the highlighter function
                        speak("AC turned off");


                    }
                    if (txvResult.getText().toString().contains("FM") &&!Iswordfound) {
                        Iswordfound = true;
                        String line = txvResult.getText().toString();

                        Pattern numberPat = Pattern.compile("\\d*\\.?\\d+");
                        Matcher matcher1 = numberPat.matcher(line);

                        Pattern stringPat = Pattern.compile("turn off ", Pattern.CASE_INSENSITIVE);
                        Matcher matcher2 = stringPat.matcher(line);

                        if (matcher1.find() && matcher2.find()) {
                            Double number = Double.parseDouble(matcher1.group());
                            Toast.makeText(getApplicationContext(), "Turning off  " + number, Toast.LENGTH_LONG).show();

                        }
                        speak("FM turned off");

                    }

                    if (txvResult.getText().toString().contains("Bluetooth") &&!Iswordfound) {
                        Iswordfound = true;
                        Toast.makeText(getApplicationContext(), "Turning off Bluetooth ", Toast.LENGTH_LONG).show();
                        speak("Bluetooth turned off");

                    }
                }
                if (txvResult.getText().toString().contains("map") && !Iswordfound) {
                    Iswordfound = true;
                    Toast.makeText(getApplicationContext(), "Opening the Google Map", Toast.LENGTH_SHORT).show();
                    //Call the highlighter function
                    String arraywords[] = {"Google", "map"};
                    TextHighlighter(arraywords);
                    speak("Opening the Google Map");

                }
                if (txvResult.getText().toString().contains("drive mode") && !Iswordfound) {
                    Iswordfound = true;
                    if (txvResult.getText().toString().contains("eco") || txvResult.getText().toString().contains("Eco")) {
                        drivemode = "Eco";
                    }  if (txvResult.getText().toString().contains("sport") || txvResult.getText().toString().contains("Sport")) {
                        drivemode = "sport";
                    }  if (txvResult.getText().toString().contains("normal") || txvResult.getText().toString().contains("Normal")) {
                        drivemode = "normal";
                    }
                    //Call the highlighter function
                    String arraywords[] = {"drive", "mode", drivemode};
                    TextHighlighter(arraywords);
                    speak("The car is in" + drivemode+"mode");
                }  if (txvResult.getText().toString().contains("time") && !Iswordfound) {
                    Iswordfound = true;
                    long timestamp = System.currentTimeMillis();
                    Date getime = new Date(timestamp);
                    String timeZone = "GMT+5:30";
                    SimpleDateFormat current_time = new SimpleDateFormat("hh mm a");
                    current_time.setTimeZone(TimeZone.getTimeZone(timeZone));
                    String time = current_time.format(getime);
                    String arraywords[] = {"Time", String.valueOf(time)};
                    TextHighlighter(arraywords);
                    speak("Time is " + time);
                }
                if(!Iswordfound){
                    speak("Sorry I couldn't catch you");
                }
            }
            save_preferences();
        }
    }

    public static boolean isBetween(double value, double min, double max) {
        return ((value >= min) && (value <= max));
    }

    public void TextHighlighter(String[] arraywords ){
        //Create new list
        System.out.println(Arrays.asList(arraywords));
        ArrayList<String> mList = new ArrayList<>();
        String full_text = txvResult.getText().toString();
        //split strings by space
        String[] splittedWords = full_text.split(" ");
        System.out.println(Arrays.asList(splittedWords));
        SpannableString str=new SpannableString(full_text);
        //Check the matching words
        for (int i = 0; i < arraywords.length; i++) {
            for (int j = 0; j < splittedWords.length; j++) {
                if (arraywords[i].equalsIgnoreCase(splittedWords[j])) {
                    mList.add(arraywords[i]);
                }
            }
        }
        //make the words bold
        for (int k = 0; k < mList.size(); k++) {
            int val = full_text.indexOf(mList.get(k));
            System.out.println(val);
            final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(50,205,50));

            str.setSpan(new StyleSpan(Typeface.BOLD), val, val + mList.get(k).length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(fcs, val, val + mList.get(k).length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        txvResult.setText(str);
    }
}

