package com.ujb.sampleprintlogo.printer;


import android.app.Activity;

import com.ujb.sampleprintlogo.R;

public class SamplePrintUtils {
    private static final String footers = "Terimakasih\nSelamat Jalan.\nSemoga Selamat";
    private static final String spbu_id = "3112401";
    private static final String spbu_name = "spbu_name";
    private static final String spbu_address = "Address”: “Jl. Raya Fatmawati";
    private static final String phone_no = "(021)123456";

    public static void PrintNotaSample1(Activity activity) {
        try {
            String text_titik = activity.getString(R.string.text_titik);

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_big));
            SerialPortManager.instance().sendCommand("\n" + "SIMPLE PRINT 1" + "\n");
            SerialPortManager.instance().sendCommand("\n\n" + spbu_id + "\n");
            
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_small));
            SerialPortManager.instance().sendCommand(spbu_name + "\n");
            SerialPortManager.instance().sendCommand(spbu_address + "\n");
            SerialPortManager.instance().sendCommand(phone_no + "\n");
            
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_left)); // kiri
            SerialPortManager.instance().sendCommand("\nShift          : " + "\n");
            SerialPortManager.instance().sendCommand("No. Trans      : " + "\n");
            SerialPortManager.instance().sendCommand("Waktu          : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik + "\n");
            SerialPortManager.instance().sendCommand("Pulau/Pompa    : " + "\n");
            SerialPortManager.instance().sendCommand("Nama Product   : " + "\n");
            SerialPortManager.instance().sendCommand("Harga/Liter    : " + "\n");
            SerialPortManager.instance().sendCommand("Volume         : " + "\n");
            SerialPortManager.instance().sendCommand("Total Harga    : " + "\n");
            SerialPortManager.instance().sendCommand("Operator       : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik + "\n");
            SerialPortManager.instance().sendCommand("CASH" + "\n");
            SerialPortManager.instance().sendCommand("No. Kend.      : " + "\n");
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center)); //center
            SerialPortManager.instance().sendCommand("\n" + footers + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.cutting)); //cutting

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void PrintNotaSample2(Activity activity) {
        try {
            String text_titik = activity.getString(R.string.text_titik);

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_big));
            SerialPortManager.instance().sendCommand("\n" + "SIMPLE PRINT 2" + "\n");
            SerialPortManager.instance().sendCommand("\n\n" + spbu_id + "\n");

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_small));
            SerialPortManager.instance().sendCommand(spbu_name + "\n");
            SerialPortManager.instance().sendCommand(spbu_address + "\n");
            SerialPortManager.instance().sendCommand(phone_no + "\n");

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_left)); // kiri
            SerialPortManager.instance().sendCommand("\nShift          : " + "\n");
            SerialPortManager.instance().sendCommand("No. Trans      : " + "\n");
            SerialPortManager.instance().sendCommand("Waktu          : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik + "\n");
            SerialPortManager.instance().sendCommand("Pulau/Pompa    : " + "\n");
            SerialPortManager.instance().sendCommand("Nama Product   : " + "\n");
            SerialPortManager.instance().sendCommand("Harga/Liter    : " + "\n");
            SerialPortManager.instance().sendCommand("Volume         : " + "\n");
            SerialPortManager.instance().sendCommand("Total Harga    : " + "\n");
            SerialPortManager.instance().sendCommand("Operator       : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik + "\n");
            SerialPortManager.instance().sendCommand("CASH" + "\n");
            SerialPortManager.instance().sendCommand("No. Kend.      : " + "\n");
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center)); //center
            SerialPortManager.instance().sendCommand("\n" + footers + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.cutting)); //cutting
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void PrintNotaSampleSIUPO1(Activity activity) {
        try {
            String text_titik = activity.getString(R.string.text_titik_siupo);

            String marginEnd = activity.getString(R.string.margin_right_printer);
            SerialPortManager.instance().sendHexCommand(marginEnd);
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_big));
            SerialPortManager.instance().sendCommand("SIMPLE PRINT 1"+ "\n");
            SerialPortManager.instance().sendCommand("\n\n" +spbu_id + "\n");
            
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_small));
            SerialPortManager.instance().sendCommand(spbu_name + "\n");
            SerialPortManager.instance().sendCommand(spbu_address + "\n");
            SerialPortManager.instance().sendCommand(phone_no+"\n");

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_left)); // kiri
            SerialPortManager.instance().sendCommand("\nShift       : " + "\n");
            SerialPortManager.instance().sendCommand("No. Trans   : " + "\n");
            SerialPortManager.instance().sendCommand("Waktu       : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik+"\n");
            SerialPortManager.instance().sendCommand("Pulau/Pompa : " + "\n");
            SerialPortManager.instance().sendCommand("Nama Product: " + "\n");
            SerialPortManager.instance().sendCommand("Harga/Liter : " + "\n");
            SerialPortManager.instance().sendCommand("Volume      : " + "\n");
            SerialPortManager.instance().sendCommand("Total Harga : " + "\n");
            SerialPortManager.instance().sendCommand("Operator    : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik+"\n");
            SerialPortManager.instance().sendCommand("CASH" + "\n");
            SerialPortManager.instance().sendCommand("No. Kend.   : " + "\n");
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center)); //center
            SerialPortManager.instance().sendCommand("\n" + footers + "\n" + "\n" + "\n" + "\n" + "\n" +  "\n" );
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.cutting)); //cutting

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void PrintNotaSampleSIUPO2(Activity activity) {
        try {
            String text_titik = activity.getString(R.string.text_titik_siupo);

            String marginEnd = activity.getString(R.string.margin_right_printer);
            SerialPortManager.instance().sendHexCommand(marginEnd);

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_big));
            SerialPortManager.instance().sendCommand("SIMPLE PRINT 2"+ "\n");
            SerialPortManager.instance().sendCommand("\n\n" +spbu_id + "\n");

            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center));
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_small));
            SerialPortManager.instance().sendCommand(spbu_name + "\n");
            SerialPortManager.instance().sendCommand(spbu_address + "\n");
            SerialPortManager.instance().sendCommand(phone_no+"\n");
            
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_left)); // kiri
            SerialPortManager.instance().sendCommand("\nShift       : " + "\n");
            SerialPortManager.instance().sendCommand("No. Trans   : " + "\n");
            SerialPortManager.instance().sendCommand("Waktu       : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik+"\n");
            SerialPortManager.instance().sendCommand("Pulau/Pompa : " + "\n");
            SerialPortManager.instance().sendCommand("Nama Product: " + "\n");
            SerialPortManager.instance().sendCommand("Harga/Liter : " + "\n");
            SerialPortManager.instance().sendCommand("Volume      : " + "\n");
            SerialPortManager.instance().sendCommand("Total Harga : " + "\n");
            SerialPortManager.instance().sendCommand("Operator    : " + "\n");
            SerialPortManager.instance().sendCommand(text_titik+"\n");
            SerialPortManager.instance().sendCommand("CASH" + "\n");
            SerialPortManager.instance().sendCommand("No. Kend.   : " + "\n");
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.text_align_center)); //center
            SerialPortManager.instance().sendCommand("\n" + footers + "\n" + "\n" + "\n" + "\n" + "\n" +  "\n" );
            SerialPortManager.instance().sendHexCommand(activity.getString(R.string.cutting)); //cutting

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
