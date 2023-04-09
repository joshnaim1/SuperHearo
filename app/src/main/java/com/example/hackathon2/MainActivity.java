package com.example.hackathon2;
import com.example.hackathon2.R;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements VoiceRecognition.OnSpeechRequestedListener {
    private TextToSpeech tts;
    private Locale currentLocale;
    private int currentGender;
    private VoiceRecognition voiceRecognition;

    public void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    currentLocale = Locale.getDefault();
                    tts.setLanguage(currentLocale);
                    tts.setPitch(1.0f);
                    tts.setSpeechRate(1.0f);
                }
            }
        });

        voiceRecognition = new VoiceRecognition(MainActivity.this, this);

        Button startRecordingButton = findViewById(R.id.start_recording_button);
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceRecognition.isListening()) {
                    voiceRecognition.stopListening();
                    startRecordingButton.setText("Start Recording");
                } else {
                    voiceRecognition.startListening();
                    startRecordingButton.setText("Stop Recording");
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        voiceRecognition.stopListening();
    }


    public void onResults(ArrayList<String> results) {
        // Handle the voice recognition results here
        String userInput = results.get(0); // Get the first recognized phrase
        getGptResponse(userInput);
    }

    private void getGptResponse(String userInput) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer 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");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        OpenAIApi api = retrofit.create(OpenAIApi.class);

        GptRequest gptRequest = new GptRequest(userInput, 50);
        Call<GptResponse> call = api.generateResponse(gptRequest);

        call.enqueue(new Callback<GptResponse>() {
            @Override
            public void onResponse(Call<GptResponse> call, Response<GptResponse> response) {
                if (response.isSuccessful()) {
                    GptResponse gptResponse = response.body();
                    String result = gptResponse.getChoices()[0].getText();
                    // Handle the ChatGPT response here
                } else {
                    // Handle API call failure
                }
            }

            @Override
            public void onFailure(Call<GptResponse> call, Throwable t) {
                // Handle network failure
            }
        });
    }

    @Override
    public void onSpeechRequested(String text) {
        // Process the recognized text here
    }
}
