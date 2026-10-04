package com.xaubuddy.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 50, 40, 40);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("XAU Buddy");
        title.setTextSize(30);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);

        TextView status = new TextView(this);
        status.setText("BOT OFF");
        status.setTextSize(24);
        status.setGravity(Gravity.CENTER);

        Button botButton = new Button(this);
        botButton.setText("BOT ON");

        botButton.setOnClickListener(v -> {
            if (status.getText().toString().equals("BOT OFF")) {
                status.setText("BOT ON");
                botButton.setText("BOT OFF");
            } else {
                status.setText("BOT OFF");
                botButton.setText("BOT ON");
            }
        });

        layout.addView(title);
        layout.addView(status);
        layout.addView(botButton);

        setContentView(layout);
    }
}
