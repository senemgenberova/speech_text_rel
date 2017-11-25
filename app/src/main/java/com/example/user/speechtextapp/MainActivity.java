package com.example.user.speechtextapp;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView speechToTextTxt;
    private EditText textToSpeechTxt;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechToTextTxt = (TextView) findViewById(R.id.speechToTextTxt);
        textToSpeechTxt = (EditText) findViewById(R.id.textToSpeechTxt);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    public void textToSpeech(View view) {
        if(textToSpeechTxt.length() != 0){
            tts.speak(textToSpeechTxt.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            Log.e("e","oldu");
        }
        else{
            Toast.makeText(this, "Please enter any text", Toast.LENGTH_SHORT).show();
        }
    }

    public void speechToText(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }
        else{
            Toast.makeText(this, "Your device cannot enable speech", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            ArrayList<String> textList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechToTextTxt.setText(textList.get(0));
        }
    }
}
