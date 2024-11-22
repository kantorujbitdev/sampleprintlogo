package com.ujb.sampleprintlogo.printer;


import static com.ujb.sampleprintlogo.printer.TimeUtil.getCurrentDate;

import android.os.HandlerThread;
import android.serialport.SerialPort;
import android.util.Log;

import com.licheedev.hwutils.ByteUtil;
import com.ujb.sampleprintlogo.printer.message.LogManager;
import com.ujb.sampleprintlogo.printer.message.SendMessage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class SerialPortManager {

    private static final String TAG = "SerialPortManager";

    private SerialReadThread mReadThread;
    private OutputStream mOutputStream;
    private HandlerThread mWriteThread;
    private Scheduler mSendScheduler;

    private static class InstanceHolder {

        public static SerialPortManager sManager = new SerialPortManager();
    }

    public static SerialPortManager instance() {
        return InstanceHolder.sManager;
    }

    private SerialPort mSerialPort;

    private SerialPortManager() {
    }

    public SerialPort open(Device device) {
        return open(device.getPath(), device.getBaudrate());
    }

    public SerialPort open(String devicePath, String baudrateString) {
        if (mSerialPort != null) {
            close();
        }

        try {
            File device = new File(devicePath);
            int baurate = Integer.parseInt(baudrateString);
            mSerialPort = new SerialPort(device, baurate);

            mReadThread = new SerialReadThread(mSerialPort.getInputStream());
            mReadThread.start();

            mOutputStream = mSerialPort.getOutputStream();

            mWriteThread = new HandlerThread("write-thread");
            mWriteThread.start();
            mSendScheduler = AndroidSchedulers.from(mWriteThread.getLooper());

            return mSerialPort;
        } catch (Throwable tr) {
            Log.e(TAG, "Gagal membuka port serial: "+ tr);
            close();
            return null;
        }
    }

    public void close() {
        if (mReadThread != null) {
            mReadThread.close();
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mWriteThread != null) {
            mWriteThread.quit();
        }

        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    private void sendData(byte[] datas) throws Exception {
        mOutputStream.write(datas);
    }

    private Observable<Object> rxSendData(final byte[] datas) {

        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    sendData(datas);
                    emitter.onNext(new Object());
                } catch (Exception e) {

                    Log.e(TAG, "Mengirim：" + ByteUtil.bytes2HexStr(datas) + "\nGagal"+ e);

                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                        return;
                    }
                }
                emitter.onComplete();
            }
        });
    }

    public void sendCommand(final String command) {
        Log.e(TAG, "Time Send data: " + getCurrentDate());
        Log.e(TAG, "Send data: " + command);

        byte[] bytes = command.getBytes();
        rxSendData(bytes).subscribeOn(mSendScheduler).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                LogManager.instance().post(new SendMessage(command));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Gagal mengirim : "+ e);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Complete Time Send data: " + getCurrentDate());
            }
        });
    }

    public void sendHexCommand(final String message) {
        byte[] bytes = hexStringToByteArray(message);
        rxSendData(bytes).subscribeOn(mSendScheduler).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                LogManager.instance().post(new SendMessage(message));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Gagal mengirim : " + e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

    public void sendCommand(byte[] command) {
        try {
            mOutputStream.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
