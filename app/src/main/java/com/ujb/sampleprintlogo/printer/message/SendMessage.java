package com.ujb.sampleprintlogo.printer.message;

import android.graphics.Bitmap;

import com.ujb.sampleprintlogo.printer.TimeUtil;


public class SendMessage implements IMessage {

    private Bitmap bitmap;
    private final String message;

    public SendMessage(String command) {
        this.message = TimeUtil.currentTime() + "    Kirim data：" + command;
    }

    public SendMessage(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.message = TimeUtil.currentTime() + "    Kirim data：" + bitmap;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean isToSend() {
        return true;
    }
}
