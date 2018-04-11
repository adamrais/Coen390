package com.farmingapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // declare bluetooth method

    //turn off the blutooth
    public void turnOff(View view){

        bluetoothAdapter.disable(); // disable bluetooth
        if (bluetoothAdapter.isEnabled()){

            Toast.makeText(getApplicationContext(),"Bluetooth is still ON",Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(getApplicationContext(),"Bluetooth OFF",Toast.LENGTH_LONG).show();
        }
    }
    public void findDevices(View view){

        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE); // check for devices around
        startActivity(intent);

    }
    public void viewDevices(View view){

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        ListView viewListDevices = (ListView) findViewById(R.id.viewListDevices);

        ArrayList pairedDevicesArray = new ArrayList();

        //convert set to array list

        for (BluetoothDevice bluetoothDevice : pairedDevices) {

            pairedDevicesArray.add(bluetoothDevice.getAddress()); // get the name of the bluetooth to be displayed

        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pairedDevicesArray);
        viewListDevices.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        if(bluetoothAdapter.enable()){

            Toast.makeText(getApplicationContext(),"Bluetooth ON",Toast.LENGTH_LONG).show();
        }else {

            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);

            if(bluetoothAdapter.enable()){

                Toast.makeText(getApplicationContext(),"Bluetooth is now ON",Toast.LENGTH_LONG).show();
            }
        }
    }
}
