package com.ujb.sampleprintlogo.printer;

import android.content.Context;
import android.serialport.SerialPortFinder;

import com.ujb.sampleprintlogo.R;

public class InitPrinterUtils {
    private static final String TAG = "InitPrinterUtils";

    public static void initPrinter(Context activity){
        PrefHelper.initDefault(activity);
        String[] mDevices;
        String[] mBaudrates;
        Device mDevice ;
        int mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        int mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0);
        mBaudrates = activity.getResources().getStringArray(R.array.baudrates);

        SerialPortFinder serialPortFinder = new SerialPortFinder();
        mDevices = serialPortFinder.getAllDevicesPath();
        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);
        SerialPortManager.instance().open(mDevice);
//        SerialPortManager.instance().open(mDevice.getPath(), mDevice.getBaudrate());

    }
}
