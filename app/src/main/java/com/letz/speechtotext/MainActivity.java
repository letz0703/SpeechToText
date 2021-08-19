package com.letz.speechtotext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    TextView tvResult;
    ImageButton mic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv);
        mic = findViewById(R.id.btniMic);

        mic.setOnClickListener(v -> {
            convertSpeech();
        });
    }

    // click listener에서 호출할 함수
    public void convertSpeech() {
        Intent iConvertSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        );

        iConvertSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL
                , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        launchSpeechConverter.launch(iConvertSpeech);
    }

    ActivityResultLauncher<Intent> launchSpeechConverter = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<String> speakResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        tvResult.setText(speakResults.get(0));
                    }
                }
            }
    );
}








