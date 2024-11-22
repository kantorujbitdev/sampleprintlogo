package com.ujb.sampleprintlogo;

import static com.ujb.sampleprintlogo.printer.PrefHelperNamePrinter.NAME_WOOSIM;

import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.ujb.sampleprintlogo.printer.Device;
import com.ujb.sampleprintlogo.printer.PrefHelper;
import com.ujb.sampleprintlogo.printer.PrefHelperNamePrinter;
import com.ujb.sampleprintlogo.printer.PreferenceKeys;
import com.ujb.sampleprintlogo.printer.PrintLogoManager;
import com.ujb.sampleprintlogo.printer.SamplePrintUtils;
import com.ujb.sampleprintlogo.printer.SerialPortManager;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "MainActivity";
    Spinner mSpinnerDevices;
    Spinner mSpinnerBaudrate;
    Button mBtnOpenDevice;
    Button mBtnTestPrint1;
    Button mBtnTestPrint2;

    private Device mDevice;

    private int mDeviceIndex;
    private int mBaudrateIndex;

    private String[] mDevices;
    private String[] mBaudrates;

    private boolean mOpened = false;

    private String[] mNamePrinter;
    Spinner mSpinnerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrefHelperNamePrinter.initDefault(this);
        String PrinterName = PrefHelperNamePrinter.getDefault().getString(NAME_WOOSIM, "");

        mSpinnerName = findViewById(R.id.spinner_name_printer);
        mSpinnerDevices = findViewById(R.id.spinner_devices);
        mSpinnerBaudrate = findViewById(R.id.spinner_baudrate);
        mBtnOpenDevice = findViewById(R.id.btn_open_device);
        mBtnTestPrint1 = findViewById(R.id.test_print1);
        mBtnTestPrint2 = findViewById(R.id.test_print2);

        PrefHelper.initDefault(this);
        mOpened = PrefHelper.getDefault().getBoolean(PreferenceKeys.STATUS_SERIAL, false);

        initDevice();
        initSpinners();
        updateViewState(mOpened);
        mBtnOpenDevice.setOnClickListener(v -> switchSerialPort());
        mBtnTestPrint1.setOnClickListener(v -> {
            Toast.makeText(this, "Proses cetak Nota 1 sedang berjalan", Toast.LENGTH_SHORT).show();
            SerialPortManager.instance().open(mDevice);
            PrintLogoManager.getInstance(this).printLogo("pertamina", PrinterName);
            if (PrinterName.equalsIgnoreCase(NAME_WOOSIM)) SamplePrintUtils.PrintNotaSample1(this);
            else SamplePrintUtils.PrintNotaSampleSIUPO1(this);
        });

        mBtnTestPrint2.setOnClickListener(v -> {
            Toast.makeText(this, "Proses cetak Nota 2 sedang berjalan", Toast.LENGTH_SHORT).show();
            SerialPortManager.instance().open(mDevice);
            PrintLogoManager.getInstance(this).printLogo("my_pertamina", PrinterName);
            if (PrinterName.equalsIgnoreCase(NAME_WOOSIM)) SamplePrintUtils.PrintNotaSample2(this);
            else SamplePrintUtils.PrintNotaSampleSIUPO2(this);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SerialPortManager.instance().close();
    }

    private void initDevice() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();

        // devices
        mDevices = serialPortFinder.getAllDevicesPath();

        if (mDevices.length == 0) {
            mDevices = new String[]{
                    getString(R.string.no_serial_device)
            };
        }

        mNamePrinter = getResources().getStringArray(R.array.name_printer);

        // select baud
        mBaudrates = getResources().getStringArray(R.array.baudrates);

        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        mDeviceIndex = mDeviceIndex >= mDevices.length ? mDevices.length - 1 : mDeviceIndex;
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 6);


        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);
    }

    private boolean isFirst = true;

    private void initSpinners() {
        mNamePrinter = getResources().getStringArray(R.array.name_printer);

        ArrayAdapter<String> namePrinterAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_default_item, mNamePrinter);
        namePrinterAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerName.setAdapter(namePrinterAdapter);
        mSpinnerName.setOnItemSelectedListener(this);

        ArrayAdapter<String> deviceAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_default_item, mDevices);
        deviceAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerDevices.setAdapter(deviceAdapter);
        mSpinnerDevices.setOnItemSelectedListener(this);

        ArrayAdapter<String> baudrateAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_default_item, mBaudrates);
        baudrateAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerBaudrate.setAdapter(baudrateAdapter);
        mSpinnerBaudrate.setOnItemSelectedListener(this);

        //getdata
        String mNameIndex = PrefHelperNamePrinter.getDefault().getString(NAME_WOOSIM, "");
        if (mNameIndex.equalsIgnoreCase(NAME_WOOSIM)) mSpinnerName.setSelection(0);
        else mSpinnerName.setSelection(1);

        if (isFirst) {
            mSpinnerDevices.setSelection(mDeviceIndex);
            mSpinnerBaudrate.setSelection(mBaudrateIndex);
        }
    }

    /**
     * Buka atau tutup port serial
     */
    private void switchSerialPort() {
        if (mOpened) {
            SerialPortManager.instance().close();
            mOpened = false;
        } else {
            // Simpan konfigurasi
            PrefHelper.getDefault().saveInt(PreferenceKeys.SERIAL_PORT_DEVICES, mDeviceIndex);
            PrefHelper.getDefault().saveInt(PreferenceKeys.BAUD_RATE, mBaudrateIndex);
            PrefHelper.getDefault().saveBoolean(PreferenceKeys.STATUS_SERIAL, true);

            mOpened = SerialPortManager.instance().open(mDevice) != null;
            if (mOpened) {
                Toast.makeText(this, "Berhasil membuka port serial", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Gagal membuka port serial", Toast.LENGTH_SHORT).show();
            }
        }

        updateViewState(mOpened);
        isFirst = false;
    }

    private void updateViewState(boolean isSerialPortOpened) {

        int stringRes = isSerialPortOpened ? R.string.close_serial_port : R.string.open_serial_port;

        mBtnOpenDevice.setText(stringRes);

        mSpinnerName.setEnabled(!isSerialPortOpened);
        mSpinnerDevices.setEnabled(!isSerialPortOpened);
        mSpinnerBaudrate.setEnabled(!isSerialPortOpened);
        mBtnTestPrint1.setEnabled(isSerialPortOpened);
        mBtnTestPrint2.setEnabled(isSerialPortOpened);
    }

    String NamePrinter = "";

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_devices) {
            mDeviceIndex = position;
            mDevice.setPath(mDevices[mDeviceIndex]);
        } else if (parent.getId() == R.id.spinner_baudrate) {
            mBaudrateIndex = position;
            mDevice.setBaudrate(mBaudrates[mBaudrateIndex]);
        } else if (parent.getId() == R.id.spinner_name_printer) {
            if (position == 0) {
                NamePrinter = NAME_WOOSIM;
                if (!isFirst) {
                    mSpinnerBaudrate.setSelection(6);
                }
            } else if (position == 1) {
                NamePrinter = "SIUPO";
                if (!isFirst) {
                    mSpinnerBaudrate.setSelection(8);
                }
            }
            PrefHelperNamePrinter.getDefault().saveString(NAME_WOOSIM, NamePrinter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}