package com.farmingapp;

/**
 * Created by adamrais on 18-03-31.
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Simple UI demonstrating how to connect to a Bluetooth device,
 * send and receive messages using Handlers, and update the UI.
 */
public class measurementPage extends Activity {

    // Tag for logging
    private static final String TAG = "BluetoothActivity";

    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    //private final String address = "00:06:66:66:33:89";
    //00:21:13:02:2E:A9
    public String address = ""; // mac address will be provided by user

    // The thread that does all the work
    BluetoothThread btt;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

    /**
     * Launch the Bluetooth thread.
     */
    LinearLayout linearLayout_1, linearLayout_2, linearLayout_3, linearLayout_4;
    TextView tv_onYourBluetooth;
    TextView tvMessage;
    Button bt_onBluetooth, connectButton;
    EditText et_macAddress;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_page);

        linearLayout_1 = findViewById(R.id.linearLayout_1);
        linearLayout_2 = findViewById(R.id.linearLayout_2);
        linearLayout_3 = findViewById(R.id.linearLayout_3);
        linearLayout_4 = findViewById(R.id.linearLayout_4);
        tvMessage = findViewById(R.id.displayField);

        tv_onYourBluetooth = findViewById(R.id.tv_onYourBluetooth);
        et_macAddress = findViewById(R.id.et_macAddress);

        bt_onBluetooth = findViewById(R.id.bt_onBluetooth);
        connectButton = findViewById(R.id.connectButton);

        bt_onBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter.enable()) {

                    Toast.makeText(getApplicationContext(), "Bluetooth is now ON", Toast.LENGTH_SHORT).show();
                    bt_onBluetooth.setVisibility(View.GONE);
                    tv_onYourBluetooth.setText("Your Bluetooth is ON");
                    linearLayout_2.setVisibility(View.VISIBLE);
                }
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_macAddress.getText().toString().trim().equalsIgnoreCase("")) {

                    Toast.makeText(getApplicationContext(), "Please enter desired arduino MAC address", Toast.LENGTH_SHORT).show();
                } else {
                    // Initialize the Bluetooth thread, passing in a MAC address
                    // and a Handler that will receive incoming messages
                    address = et_macAddress.getText().toString().trim();

                    btt = new BluetoothThread(address, new Handler() {

                        @Override
                        public void handleMessage(Message message) {

                            String s = (String) message.obj;

                            linearLayout_3.setVisibility(View.VISIBLE);
                            linearLayout_4.setVisibility(View.VISIBLE);

                            // Do something with the message
                            if (s.equals("CONNECTED")) {
                                TextView tv = findViewById(R.id.statusText);
                                tv.setText("Connected.");
                                Toast.makeText(getApplicationContext(), "You are connected to your device", Toast.LENGTH_SHORT).show();

                                // Here You will receive the value from your arduino after successful connection.
                                // Convert the received value into integer

                                // You can convert your "string" value into "int" like as this example
                                ////////////////////////////
                               /* String msg = "810";
                                int val;
                                val = Integer.parseInt(msg);*/
                                ////////////////////////////

                                // Then pass the value to this function

                                //showMessageOnThreshold(); // You will pass here your value instead of "123"

                            } else if (s.equals("DISCONNECTED")) {
                                TextView tv = findViewById(R.id.statusText);
                                tv.setText("Disconnected.");
                                Toast.makeText(getApplicationContext(), "Your device is disconnected", Toast.LENGTH_SHORT).show();

                            } else if (s.equals("CONNECTION FAILED")) {
                                TextView tv = findViewById(R.id.statusText);
                                tv.setText("Connection failed!");
                                btt = null;
                                Toast.makeText(getApplicationContext(), "Your MAC address is not working", Toast.LENGTH_SHORT).show();
                            } else {
                                TextView tv = findViewById(R.id.readField);
                                System.out.println("s: "+s.toString());
                                int i = 0;
                                try{

                                     i = Integer.parseInt(s.toString().trim().replaceAll("\r", "").replaceAll("\n", ""));
                                }catch(NumberFormatException ex){ // handle your exception
                                    i=-1;
                                }

                                showMessageOnThreshold(i);
                                tv.setText(s);
                            }
                        }
                    });

                    // Get the handler that is used to send messages
                    //    writeHandler = btt.getWriteHandler();

                    // Run the thread
                    btt.start();

                    TextView tv = findViewById(R.id.statusText);
                    tv.setText("Connecting...");
                }
            }
        });
    }

    protected void showMessageOnThreshold(int value) {
        System.out.println("value: "+value);
        if (value < 420) {
            //Toast.makeText(getApplicationContext(), "Your Plant is Dry! It needs water", Toast.LENGTH_LONG).show();
            tvMessage.setText("Your Plant is Dry! It needs water");

        } else if (value > 420 && value < 890) {
            //Toast.makeText(getApplicationContext(), "Your Plant is growing perfectly", Toast.LENGTH_LONG).show();
            tvMessage.setText("Your Plant is growing perfectly");

        } else if (value > 890) {
            //Toast.makeText(getApplicationContext(), "Your Plant is very Wet! Stop watering", Toast.LENGTH_LONG).show();
            tvMessage.setText("Your Plant is very Wet! Stop watering");

        }
    }

    /**
     * Kill the thread when we leave the activity.
     */
    protected void onPause() {
        super.onPause();

        if (btt != null) {
            btt.interrupt();
            btt = null;
        }
    }
}

