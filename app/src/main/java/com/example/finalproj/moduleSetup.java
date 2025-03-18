package com.example.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class moduleSetup extends AppCompatActivity {

    Button backButton, startButton;
    final static String[] INTERACTION_TYPES = { "Tapping", "Typing", "Voice" };
    Spinner intType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module_setup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = (Button) findViewById(R.id.backButton);
        startButton = (Button) findViewById(R.id.startButton);
        intType = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, R.layout.spinnerstyle, INTERACTION_TYPES);
        intType.setAdapter(adapter2);

        Intent intent = getIntent();
        int num = intent.getIntExtra("modNum", 0);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), moduleView.class);
                startActivity(intent);
                finish();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = INTERACTION_TYPES[intType.getSelectedItemPosition()];
                //differentiate between types here
                if(type.equals("Tapping")){
                    Intent intent = new Intent(getApplicationContext(), tapModule.class);
                    intent.putExtra("modNum", num);
                    startActivity(intent);
                    finish();
                }
                else if(type.equals("Typing")){

                }
                else if(type.equals("Voice")){

                }
            }
        });

    }
}