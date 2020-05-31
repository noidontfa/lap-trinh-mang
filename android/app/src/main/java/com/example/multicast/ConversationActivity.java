package com.example.multicast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.multicast.model.GroupModel;
import com.example.multicast.model.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 900;
    private static final int IMAGE_REQUEST = 323;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private ArrayList<Message> messages;
    private String INET_ADDR = "224.0.0.3";
    private int PORT = 8888;
    private EditText edt;
    private Button button;
    private ImageButton btnImage;
    private String mId="me";
    private String mName="name";
    private byte[] image=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();
        String groupIp = intent.getStringExtra("GROUP_IP");
        String name = intent.getStringExtra("USER_NAME");
        String uid = intent.getStringExtra("USER_ID");
        this.INET_ADDR = groupIp;
        this.mName = name;
        this.mId = uid;
        Log.d(TAG, "onCreate: " +groupIp);
        Log.d(TAG, "onCreate: " + name);
        Log.d(TAG, "onCreate: " + uid);


        runThread();

        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null){
            WifiManager.MulticastLock lock = wifi.createMulticastLock("HelloAndroid");
            lock.acquire();
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {}

        // Standard code for initializing a ListView from an ArrayList
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        edt = (EditText) findViewById(R.id.main_txt);
        button = (Button) findViewById(R.id.main_send);
        btnImage = (ImageButton) findViewById(R.id.main_btn_image);
        messages = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(getApplicationContext(),messages,mId);
        recyclerView.setAdapter(adapter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendThread();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, IMAGE_REQUEST);
            }
        });
        btnImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                image=null;
                btnImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_black_24dp));
                return true;
            }
        });


        Log.d("TAG", "onCreate: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendThread() {
        new Thread() {
            @Override
            public void run() {
                if((edt.getText() != null && !edt.getText().toString().equals("")) ||
                        image != null) {
                    try {
                        InetAddress group = InetAddress.getByName(INET_ADDR);
                        MulticastSocket s = new MulticastSocket(PORT);
                        s.joinGroup(group);
                        Message message = new Message(mId,mName,edt.getText().toString(),image);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream out = null;
                        try {
                            out = new ObjectOutputStream(bos);
                            out.writeObject(message);
                            out.flush();
                            byte[] bytes = bos.toByteArray();
                            DatagramPacket messagePacket = new DatagramPacket(bytes, bytes.length,
                                    group, PORT);
                            s.send(messagePacket);
//                            s.setSoTimeout(5000);
                        } finally {
                            try {
                                bos.close();
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        image = null;
//                                        edt.setText("");
//                                    }
//                                });
                            } catch (IOException ex) {
                                // ignore close exception
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void runThread() {

        new Thread() {
            public void run() {
                MulticastSocket socket = null;
                InetAddress group = null;

                try {
                    socket = new MulticastSocket(PORT);
                    group = InetAddress.getByName(INET_ADDR);
                    socket.joinGroup(group);

                    DatagramPacket packet;
                    while (true) {
                        byte[] buf = new byte[10000000];
                        packet = new DatagramPacket(buf, buf.length);
                        socket.receive(packet);

                        // Java byte values are signed. Convert to an int so we don't have to deal with negative values for bytes >= 0x7f (unsigned).
                        int[] valueBuf = new int[2];
                        for (int ii = 0; ii < valueBuf.length; ii++) {
                            valueBuf[ii] = (buf[ii] >= 0) ? (int) buf[ii] : (int) buf[ii] + 256;
                        }

                        final int value = (valueBuf[0] << 8) | valueBuf[1];

                        Log.d("hvhau", packet.getData().toString());

                        synchronized (messages) {
                            Log.d("hvhau","have msg");
                            ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
                            ObjectInput in = null;
                            try {
                                in = new ObjectInputStream(bis);
                                Log.d("hvhau","have obj");
                                Message message = (Message) in.readObject();
                                Log.d("hvhau","this"+message.toString());
                                messages.add(message);
                            } catch (ClassNotFoundException e) {
                                Log.d("hvhau",e.getLocalizedMessage());
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (in != null) {
                                        in.close();
                                    }
                                } catch (IOException ex) {
                                    // ignore close exception
                                }
                            }
                        }

                        // We're running on a worker thread here, but we need to update the list view from the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (messages) {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
                catch(IOException e) {
                    System.out.println(e.toString());
                }
                finally {
                    if (socket != null) {
                        try {
                            if (group != null) {
                                socket.leaveGroup(group);
                            }
                            socket.close();
                        }
                        catch(IOException e) {

                        }
                    }
                }
            }


        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri uri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImage.setImageBitmap(selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                image = stream.toByteArray();
                Toast.makeText(getApplicationContext(),image.toString(),Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
        }
    }
}
