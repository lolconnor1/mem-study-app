package com.example.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class moduleStats extends AppCompatActivity {

    TextView finalTime, finalCorrect, method, module;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module_stats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        int total = i.getIntExtra("total", 0);
        int correct = i.getIntExtra("correct", 0);
        int seconds = i.getIntExtra("time", 0);
        int modNum = i.getIntExtra("module", 0);
        String methodstr = i.getStringExtra("method");

        finalTime = (TextView) findViewById(R.id.finalTime);
        finalCorrect = (TextView) findViewById(R.id.finalCorrect);
        method = (TextView) findViewById(R.id.method);
        module = (TextView) findViewById(R.id.module);

        finalCorrect.setText("Correct Cards: " + correct + "/" + total);
        method.setText("Method: " + methodstr);
        module.setText("Module: Danish " + modNum);

        int minutes = seconds / 60;
        int sec = seconds % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, sec);
        finalTime.setText("Time Elapsed: " + timeFormatted);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(moduleStats.this, moduleView.class);
                startActivity(intent);
                finish();
            }
        });

    }
}