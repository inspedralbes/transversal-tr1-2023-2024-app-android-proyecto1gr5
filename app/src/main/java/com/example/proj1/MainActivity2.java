package com.example.proj1;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proj1.databinding.ActivityMain2Binding;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity2 extends AppCompatActivity {

    Socket mSocket;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mSocket = IO.socket("http://192.168.56.1:3001");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.connect();

        if (mSocket.connected()) {
            Toast.makeText(MainActivity2.this, "Socket Connected!!", Toast.LENGTH_SHORT).show();
        }

        mSocket.on("canviEstat", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = args[0].toString();
                Log.d("msg:",data);

                //TextView missatge = new TextView(MainActivity2.this);
                TextView estat = (TextView) findViewById(R.id.estat);
                estat.setText(data);

                /*runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        ((LinearLayout)findViewById(R.id.layout)).addView(missatge);
                    }
                });*/
            }


        });

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
