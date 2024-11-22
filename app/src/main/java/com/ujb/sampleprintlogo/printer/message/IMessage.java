package com.ujb.sampleprintlogo.printer.message;

import android.graphics.Bitmap;

public interface IMessage {

    String getMessage();

    Bitmap getBitmap();

    boolean isToSend();
}
