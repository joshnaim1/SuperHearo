package com.example.hackathon2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;

public class VoiceRecognition {
    private final OnSpeechRequestedListener speechRequestedListener;
    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;

    private boolean listening;
    public VoiceRecognition(Activity context, OnSpeechRequestedListener speechRequestedListener) {
        this.speechRequestedListener = speechRequestedListener;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());

        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float rmsdB) {
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int error) {
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && matches.size() > 0) {
                    String userInput = matches.get(0);
                    // Process the user input here
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        };

        speechRecognizer.setRecognitionListener(recognitionListener);
    }


    public void stopListening() {
        listening = false;
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
        }
    }


    public boolean isListening() {
        return listening;
    }

    public void startListening() {
        listening = true;
        speechRecognizer.startListening(recognizerIntent);
    }


    public interface OnSpeechRequestedListener {
        void onSpeechRequested(String text);
    }
}
