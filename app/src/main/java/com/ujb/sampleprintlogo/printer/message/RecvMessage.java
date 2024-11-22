package com.ujb.sampleprintlogo.printer.message;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.ujb.sampleprintlogo.printer.TimeUtil;

public class RecvMessage implements IMessage {

    private final String message;

    public RecvMessage(Bitmap bitmap) {
        this.message = TimeUtil.currentTime() + "    Pesan Diterima：" + bitmap;
    }

    public RecvMessage(String command) {
        this.message = TimeUtil.currentTime() + "    Pesan Diterima：" + command;
        Log.e("RecvMessage", "command: "+ command);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public boolean isToSend() {
        return false;
    }
}
