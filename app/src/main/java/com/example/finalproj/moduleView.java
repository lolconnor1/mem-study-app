package com.example.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class moduleView extends AppCompatActivity {

    ArrayList<String> modules;
    ArrayAdapter<String> arrayAdapter;
    Button exitButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = (ListView) findViewById(R.id.listView);
        String[] set = {"Danish 1", "Danish 2", "Danish 3", "Danish 4"};
        modules = new ArrayList<String>(Arrays.asList(set));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modules);
        listView.setAdapter(arrayAdapter);
        exitButton = (Button) findViewById(R.id.exitButton);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), moduleSetup.class);
                intent.putExtra("modNum", position);
                startActivity(intent);
                finish();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}