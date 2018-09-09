package com.komputerkit.kasirtoko.Utilitas;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.komputerkit.kasirtoko.DialogFragment.DPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by msaifa on 31/01/2018.
 */

public class Printer {

    Context context ;
    DPrinter fragment ;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    public Printer(Context context,DPrinter activity) {
        this.context = context;
        fragment = activity ;
    }

    public void cekKoneksi(){

    }

    public void findBT(String deviceName) {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                Toast.makeText(context, "Device not Support Bluetooth", Toast.LENGTH_SHORT).show();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                fragment.startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    if (device.getName().equals(deviceName)) {
                        mmDevice = device;
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(context, "Failed Connecting to device", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed Connecting to device", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean openBT() throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            //00001101-0000-1000-8000-00805F9B34FB
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            return true ;
        } catch (NullPointerException e) {
            return false ;
        } catch (Exception e) {
            return false ;
        }
    }

    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // This is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()
                            && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length);
                                        final String data = new String(
                                                encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean printNormal(String hasil) throws IOException {
        try {
            hasil += "\n";
            byte[] cc = new byte[]{0x1B,0x21,0x00};  // 0- normal size text

            mmOutputStream.write(cc);
            mmOutputStream.write(hasil.getBytes());

            return true ;

        } catch (NullPointerException e) {
            return false ;
        } catch (Exception e) {
            return false ;
        }
    }

    public boolean printNormalCenter(String item) throws IOException {
        try {
            int leng = item.length() ;
            String hasil = "" ;
            for(int i=0 ; i<32-leng;i++){
                if((32-leng)/2+1 == i){
                    hasil += item ;
                } else {
                    hasil += " " ;
                }
            }
            hasil += "\n" ;

            byte[] cc = new byte[]{0x1B,0x21,0x00};  // 0- normal size text

            mmOutputStream.write(cc);
            mmOutputStream.write(hasil.getBytes());

            return true ;

        } catch (NullPointerException e) {
            return false ;
        } catch (Exception e) {
            return false ;
        }
    }

    public boolean printBold(String hasil) throws IOException {
        try {
            hasil += "\n";

            byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text

            mmOutputStream.write(bb);
            mmOutputStream.write(hasil.getBytes());

            return true ;

        } catch (NullPointerException e) {
            return false ;
        } catch (Exception e) {
            return false ;
        }
    }

    public boolean printBoldMediumCenter(String item) throws IOException {
        try {

            int leng = item.length() ;
            String hasil = "" ;
            int len = 16 ;
            if (leng % 2 == 1){
                len = 15 ;
            }
            for(int i=0 ; i<len-leng;i++){
                if((len-leng)/2 == i){
                    hasil += item ;
                } else {
                    hasil += " " ;
                }
            }
            hasil += "\n" ;

            byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
            mmOutputStream.write(bb2);
            mmOutputStream.write(hasil.getBytes());

            return true ;
        } catch (NullPointerException e) {
            return false ;
        } catch (Exception e) {
            return false ;
        }
    }

    public boolean printBoldHeight(String hasil) throws IOException {
        try {
            hasil += "\n";
            byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
            mmOutputStream.write(bb3);
            mmOutputStream.write(hasil.getBytes());

            return true ;

        } catch (NullPointerException e) {
            return false ;
        } catch (Exception e) {
            return false ;
        }
    }


    public static String setCenter(String item){
        int leng = item.length() ;
        String hasil = "" ;
        for(int i=0 ; i<32-leng;i++){
            if((32-leng)/2+1 == i){
                hasil += item ;
            } else {
                hasil += " " ;
            }
        }
        return hasil ;
    }
    public static String getStrip(){
        String a = "" ;
        for(int i = 0 ; i < 32 ; i++){
            a+="-" ;
        }
        return a+"" ;
    }
    public static String setRight(String item){
        int leng = item.length() ;
        String hasil = "" ;
        for(int i=0 ; i<32-leng;i++){
            if((31-leng) == i){
                hasil += item ;
            } else {
                hasil += " " ;
            }
        }
        return hasil ;
    }

}
