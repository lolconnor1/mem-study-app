package com.example.finalproj;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class dragModule extends AppCompatActivity {

    Button exitButton;

    TextView correctView, timeView, wordView;
    TextView text1, text2, text3, text4, text5,
            text6, text7, text8, text9, text10,
            text11, text12, text13, text14, text15;
    ConstraintLayout box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13, box14, box15;


    int seconds = 0, total = 0, correct = 0, num;
    Dictionary dictionary;
    static String[] words;
    static String[] definitions;
    static Random rand = new Random();
    static ArrayList<Integer> srs;

    ArrayList<TextView> textList;
    ArrayList<ConstraintLayout> boxList;

    int currentWord;
    boolean running = false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drag_module);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        num = i.getIntExtra("modNum", 0);
        dictionary = new Dictionary(num);
        words = dictionary.getAllWords();
        definitions = dictionary.getAllMeanings();
        wordView = (TextView) findViewById(R.id.word);
        currentWord = rand.nextInt(15);
        wordView.setText(words[currentWord]);
        srs = new ArrayList<>();

        timeView = (TextView) findViewById(R.id.timeView4);
        correctView = (TextView) findViewById(R.id.correctView4);
        correctView.setText(correct + "/" + total);
        running = true; // Start the timer
        startTimer();

        exitButton = (Button) findViewById(R.id.exitButton5);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dragModule.this, moduleStats.class);
                intent.putExtra("time", seconds);
                intent.putExtra("total", total);
                intent.putExtra("correct", correct);
                intent.putExtra("method", "Dragging");
                intent.putExtra("module", num + 1);
                startActivity(intent);
                finish();
            }
        });

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);
        text8 = findViewById(R.id.text8);
        text9 = findViewById(R.id.text9);
        text10 = findViewById(R.id.text10);
        text11 = findViewById(R.id.text11);
        text12 = findViewById(R.id.text12);
        text13 = findViewById(R.id.text13);
        text14 = findViewById(R.id.text14);
        text15 = findViewById(R.id.text15);

        textList = new ArrayList<>();

        textList.add(text1);
        textList.add(text2);
        textList.add(text3);
        textList.add(text4);
        textList.add(text5);
        textList.add(text6);
        textList.add(text7);
        textList.add(text8);
        textList.add(text9);
        textList.add(text10);
        textList.add(text11);
        textList.add(text12);
        textList.add(text13);
        textList.add(text14);
        textList.add(text15);

        int k = 0;
        for(TextView text : textList){
            text.setText(definitions[k]);
            k++;
        }

        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        box3 = findViewById(R.id.box3);
        box4 = findViewById(R.id.box4);
        box5 = findViewById(R.id.box5);
        box6 = findViewById(R.id.box6);
        box7 = findViewById(R.id.box7);
        box8 = findViewById(R.id.box8);
        box9 = findViewById(R.id.box9);
        box10 = findViewById(R.id.box10);
        box11 = findViewById(R.id.box11);
        box12 = findViewById(R.id.box12);
        box13 = findViewById(R.id.box13);
        box14 = findViewById(R.id.box14);
        box15 = findViewById(R.id.box15);

        boxList = new ArrayList<>();

        boxList.add(box1);
        boxList.add(box2);
        boxList.add(box3);
        boxList.add(box4);
        boxList.add(box5);
        boxList.add(box6);
        boxList.add(box7);
        boxList.add(box8);
        boxList.add(box9);
        boxList.add(box10);
        boxList.add(box11);
        boxList.add(box12);
        boxList.add(box13);
        boxList.add(box14);
        boxList.add(box15);

        for(ConstraintLayout box : boxList){
            box.setOnDragListener((view, event) -> {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return event.getClipDescription() != null; // Accept the drag
                    case DragEvent.ACTION_DRAG_ENTERED:
                        view.setBackgroundColor(0xFFB39DDB); // Highlight
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        view.setBackgroundColor(0xFF3F51B5); // Restore color
                        return true;
                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();
                        draggedView.setVisibility(View.VISIBLE);
                        view.setBackgroundColor(0xFF3F51B5); // Restore color
                        checkCorrect(boxList.indexOf(box));
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        if (!event.getResult()) { // If drop was unsuccessful
                            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
                        }
                        return true;
                    default:
                        return false;
                }
            });
            wordView.setOnLongClickListener(view -> {
                ClipData clipData = ClipData.newPlainText("", "DraggedText"); // Drag data
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(clipData, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE); // Hide original while dragging
                return true;
            });

        }
    }
    private void checkCorrect(int boxIndex){
        total++;
        //check position of the box in array vs current word index
        if(boxIndex == currentWord){
            correct++;
            //light up boxIndex as green
            srs.add(currentWord);
            if(srs.size() == 15){
                srs.clear();
            }

            boxList.get(boxIndex).setBackgroundColor(0xFF2EA602);

        }
        else{
            //light up the currentWord box green, boxIndex red
            boxList.get(boxIndex).setBackgroundColor(0xFFFF4545);
            boxList.get(currentWord).setBackgroundColor(0xFF2EA602);
        }
        int test = currentWord;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reset the background color of all TextViews back to the original (transparent or other color)
                boxList.get(test).setBackgroundColor(0xFF3F51B5); // Restore color
                boxList.get(boxIndex).setBackgroundColor(0xFF3F51B5); // Restore color
            }
        }, 500);

        correctView.setText(correct + "/" + total);
        newWord();
    }
    private void newWord(){
        int newWordInt = rand.nextInt(15);
        while(srs.contains(newWordInt)){
            newWordInt = rand.nextInt(15);
        }
        currentWord = newWordInt;
        wordView.setText(words[currentWord]);
    }

    private void startTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    int minutes = seconds / 60;
                    int sec = seconds % 60;
                    String timeFormatted = String.format("%02d:%02d", minutes, sec);
                    timeView.setText(timeFormatted);

                    seconds++;
                    handler.postDelayed(this, 1000); // Repeat every second
                }
            }
        });
    }
}