package com.ujb.sampleprintlogo.printer;

import android.os.SystemClock;
import android.util.Log;

import com.licheedev.hwutils.ByteUtil;
import com.ujb.sampleprintlogo.printer.message.LogManager;
import com.ujb.sampleprintlogo.printer.message.RecvMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class SerialReadThread extends Thread {

    private static final String TAG = "SerialReadThread";

    private BufferedInputStream mInputStream;

    public SerialReadThread(InputStream is) {
        mInputStream = new BufferedInputStream(is);
    }

    @Override
    public void run() {
        byte[] received = new byte[4096];
        int size;

        Log.e(TAG, "Mulai membaca data");

        while (true) {

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {

                int available = mInputStream.available();

                if (available > 0) {
                    size = mInputStream.read(received);
                    if (size > 0) {
                        onDataReceive(received, size);
                    }
                } else {
                    // Jeda beberapa saat untuk menghindari looping dan menyebabkan penggunaan CPU yang berlebihan.
                    SystemClock.sleep(1);
                }
            } catch (IOException e) {
                Log.e(TAG, "Gagal membaca data: "+ e);
            }
        }

        Log.e(TAG, "Akhir proses membaca");
    }


    private void onDataReceive(byte[] received, int size) {
        String hexStr = ByteUtil.bytes2HexStr(received, 0, size);
        LogManager.instance().post(new RecvMessage(hexStr));
    }

    public void close() {

        try {
            mInputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Abnormal: "+ e);
        } finally {
            super.interrupt();
        }
    }
}
