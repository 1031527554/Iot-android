package com.example.iot;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import ch340Util.ToastUtil;

public class ChineseToSpeech {
    private TextToSpeech textToSpeech;

    public ChineseToSpeech(final Context context) {
        this.textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        ToastUtil.showMessage(context,"不支持语音播报");
                    }
                }
            }
        });
    }

    public void speech(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null,null);
    }

    public void destroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
