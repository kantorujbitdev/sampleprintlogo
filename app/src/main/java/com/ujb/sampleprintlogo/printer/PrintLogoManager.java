package com.ujb.sampleprintlogo.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ujb.sampleprintlogo.R;

public class PrintLogoManager {
    private static PrintLogoManager instance;
    private final Context context;
    private final byte[][] pertaminaImageBytes;
    private final byte[][] myPeImageBytes;
    private final int pertaminaWidth;
    private final int pertaminaHeight;
    private final int myPeWidth;
    private final int myPeHeight;

    private PrintLogoManager(Context context) {
        this.context = context;
        this.pertaminaImageBytes = convertBitmapToPrinterFormat(R.drawable.logo_pe);
        this.myPeImageBytes = convertBitmapToPrinterFormat(R.drawable.logo_mype);
        Bitmap pertaminaBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_pe);
        Bitmap myPeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_mype);
        this.pertaminaWidth = pertaminaBitmap.getWidth();
        this.pertaminaHeight = pertaminaBitmap.getHeight();
        this.myPeWidth = myPeBitmap.getWidth();
        this.myPeHeight = myPeBitmap.getHeight();

    }

    public static PrintLogoManager getInstance(Context context) {
        if (instance == null) {
            instance = new PrintLogoManager(context);
        }
        return instance;
    }

    private byte[][] convertBitmapToPrinterFormat(int resourceId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int bytesWidth = (width + 7) / 8;

        byte[][] imageBytesArray = new byte[height][bytesWidth];

        for (int y = 0; y < height; y++) {
            byte[] rowBytes = new byte[bytesWidth];
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                int gray = (r + g + b) / 3;
                if (gray < 128) {
                    int byteIndex = x / 8;
                    int bitIndex = x % 8;
                    rowBytes[byteIndex] |= (1 << (7 - bitIndex));
                }
            }
            imageBytesArray[y] = rowBytes;
        }
        return imageBytesArray;
    }

    public void defineBitmap(byte[][] bitmapData, int width, int height) {
        int bytesWidth = (width + 7) / 8; // +7 untuk membulatkan ke atas ke kelipatan 8
        byte[] command = new byte[]{0x1B, 0x58, 0x34, (byte) bytesWidth, (byte) height};

        // Kirim perintah pembuka untuk mendefinisikan bitmap
        SerialPortManager.instance().sendCommand(command);

        // Mengirim data baris demi baris
        for (byte[] row : bitmapData) {
            SerialPortManager.instance().sendCommand(row);
        }
    }

    public void printLogo(String type_nota, String printerName) {
        byte[][] imageBytes;
        int width;
        int height;

        if (type_nota.equalsIgnoreCase("pertamina")) {
            imageBytes = pertaminaImageBytes;
            width = pertaminaWidth;
            height = pertaminaHeight;
        } else {
            imageBytes = myPeImageBytes;
            width = myPeWidth;
            height = myPeHeight;
        }

        int paperWidth;
        if (printerName.equalsIgnoreCase("NAME_WOOSIM")) {
            paperWidth = 640;
        } else {
            paperWidth = 384;
        }

        int marginLeftPixels = ((paperWidth - width) / 2) - 30;
        int nL = marginLeftPixels % 256;
        int nH = marginLeftPixels / 256;

        // Send margin command
        byte[] marginCommand = new byte[4];
        marginCommand[0] = 0x1D; // GS
        marginCommand[1] = 0x4C; // L
        marginCommand[2] = (byte) nL; // nL
        marginCommand[3] = (byte) nH; // nH
        SerialPortManager.instance().sendCommand(marginCommand);

        defineBitmap(imageBytes, width, height);

        // Reset margin to default
        byte[] resetMarginCommand = new byte[]{0x1D, 0x4C, 0x00, 0x00}; // Reset to no margin
        SerialPortManager.instance().sendCommand(resetMarginCommand);
    }
}
