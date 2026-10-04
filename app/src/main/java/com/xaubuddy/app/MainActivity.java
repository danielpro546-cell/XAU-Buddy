package com.xaubuddy.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    TextView botStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30,40,30,30);

        TextView title = new TextView(this);
        title.setText("XAU Buddy V2");
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER);

        TextView mode = new TextView(this);
        mode.setText("MODE: DEMO\nLIVE: LOCKED");
        mode.setTextSize(20);

        TextView dashboard = new TextView(this);
        dashboard.setText(
                "Balance: $10\n" +
                "Risk: 0.5%\n" +
                "Lot Size: 0.01\n" +
                "RR: 1:2\n" +
                "SL: Waiting...\n" +
                "TP: Waiting...\n" +
                "Daily Loss: 0%\n" +
                "Trades: 0/3\n" +
                "Setup: Waiting..."
        );

        dashboard.setTextSize(18);

        botStatus = new TextView(this);
        botStatus.setText("BOT OFF");
        botStatus.setTextSize(24);

        Button botButton = new Button(this);
        botButton.setText("BOT ON");

        botButton.setOnClickListener(v -> {

            if(botStatus.getText().toString().equals("BOT OFF")){
                botStatus.setText("BOT ON");
                botButton.setText("BOT OFF");
            }
            else{
                botStatus.setText("BOT OFF");
                botButton.setText("BOT ON");
            }

        });


        Button scanButton = new Button(this);
        scanButton.setText("SCAN SETUP");

        scanButton.setOnClickListener(v -> {
            dashboard.setText(
                    "Balance: $10\n" +
                    "Risk: 0.5%\n" +
                    "Lot Size: 0.01\n" +
                    "RR: 1:2\n" +
                    "Setup: DEMO SCAN\n" +
                    "Signal: WAITING"
            );
        });


        Button closeButton = new Button(this);
        closeButton.setText("EMERGENCY CLOSE ALL");

        closeButton.setOnClickListener(v ->
                Toast.makeText(this,
                "DEMO CLOSE ALL",
                Toast.LENGTH_SHORT).show()
        );


        layout.addView(title);
        layout.addView(mode);
        layout.addView(dashboard);
        layout.addView(botStatus);
        layout.addView(botButton);
        layout.addView(scanButton);
        layout.addView(closeButton);


        setContentView(layout);
    }
}
