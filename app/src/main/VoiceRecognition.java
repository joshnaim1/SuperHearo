import android.content.Context;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;

public class VoiceRecognition {
    private SpeechRecognizer speechRecognizer;
    private VoiceRecognitionListener listener;

    public VoiceRecognition(Context context, VoiceRecognitionListener listener) {
        this.listener = listener;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    listener.onResults(matches);
                }
            }

            // Implement other methods from RecognitionListener with empty bodies
            // ...

            @Override
            public void onBeginningOfSpeech() {
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
            public void onEvent(int eventType, Bundle params) {
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onRmsChanged(float rmsdB) {
            }
        });
    }

    public void startListening() {
        speechRecognizer.startListening(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
    }

    public void destroy() {
        speechRecognizer.destroy();
    }

    public interface VoiceRecognitionListener {
        void onResults(ArrayList<String> results);
    }
}
