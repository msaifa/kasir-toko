package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by msaifa on 31/01/2018.
 */

@SuppressLint("ValidFragment")
public class DCariPrinter extends DialogFragment {

    View v ;
    ListView lvPrinter ;
    ArrayAdapter adapter ;
    ArrayList arrayList = new ArrayList() ;
    ConstraintLayout wBack ;

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

    Context context ;
    Utilitas utilitas ;
    DPrinter dPrinter ;

    @SuppressLint("ValidFragment")
    public DCariPrinter(DPrinter dPrinter) {
        context = dPrinter.context ;
        this.dPrinter = dPrinter ;

        utilitas = new Utilitas(context) ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        try{
            load() ;
        } catch (Exception e){
            utilitas.toast("Faild Connecting to Bluetooth");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_cari_printer,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;

        init() ;
        event();
    }

    public void init(){
        lvPrinter = v.findViewById(R.id.lvPrinter) ;
        wBack = v.findViewById(R.id.wBack) ;
    }

    public void event(){
        lvPrinter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utilitas.setSession("printer",arrayList.get(position).toString());
                dPrinter.setPrinter();
                keluar();
            }
        });

        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });
    }

    public void load(){
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayList) ;
        lvPrinter.setAdapter(adapter);

        try{
            openBT();
            findBT();

//            findBT();
        }catch (Exception e){

        }
    }

    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    arrayList.add(device.getName());
                }
                adapter.notifyDataSetChanged();
            }
            openBT() ;
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void openBT() throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            //00001101-0000-1000-8000-00805F9B34FB
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DCariPrinter.this).commit() ;
    }

}
