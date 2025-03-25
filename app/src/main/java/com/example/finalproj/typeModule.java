package com.example.finalproj;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

public class typeModule extends Activity implements View.OnTouchListener{

    final String TITLE_KEY = "title_key";
    final String TEXT_KEY = "text_key";
    final String SHOWING_BACK_KEY = "showing_back_key";

    // resources for card front and back fragments, etc.
    static int cardNumber; // keep track of the card currently being viewed
    static String title, text;
    static int backgroundColor;

    static String[] cardTitle;
    static String[] cardText;

    FrameLayout cardView; // we need this to attach the touch listener
    GestureDetector gestureDetector; // use Android's GestureDetector for touch gestures
    Vibrator vib; // vibrate when the dialog pops up
    static boolean showingBack; // true = showing back of card

    Dictionary dictionary;
    static Random rand;
    Button exitButton;
    static int total;
    static int correct;
    TextView timeView;
    static TextView correctView;

    private int seconds = 0;
    private boolean running = false;
    private Handler handler = new Handler();
    static String def;

    static boolean corbool = false;
    static ArrayList<Integer> srs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_module);


        Intent i = getIntent();
        int num = i.getIntExtra("modNum", 0);

        total = 0;
        correct = 0;

        dictionary = new Dictionary(num);
        rand = new Random();

        cardTitle = dictionary.getAllWords();
        cardText = dictionary.getAllMeanings();

        if (savedInstanceState == null)
        {
            // put the front of the card in the content view (as a fragment)
            getFragmentManager().beginTransaction().add(R.id.container2, new typeModule.CardFrontFragment()).commit();

            // default to Top Story card
            cardNumber = 1;
            setCard(cardNumber);
        }

        // UI responds to touch gestures
        cardView = (FrameLayout)findViewById(R.id.container2);
        cardView.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this.getBaseContext(), new typeModule.MyGestureListener());

        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); // for long-press gesture
        exitButton = (Button) findViewById(R.id.exitButton3);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeModule.this, moduleStats.class);
                intent.putExtra("time", seconds);
                intent.putExtra("total", total);
                intent.putExtra("correct", correct);
                intent.putExtra("method", "Typing");
                intent.putExtra("module", num + 1);
                startActivity(intent);
                finish();
            }
        });
        timeView = (TextView) findViewById(R.id.timeView2);
        correctView = (TextView) findViewById(R.id.correctView2);
        correctView.setText(correct + "/" + total);
        running = true; // Start the timer
        startTimer();
    }

    /*
     * Set the resources for the specified card, then set showingBack to false to show the front of
     * the card.
     */
    private static void setCard(int n)
    {
        title = cardTitle[n];
        text = cardText[n];
        showingBack = false; // next flip will show back
    }



    /*
     * The UI responds to finger gestures. No need to get down-and-dirty with onTouch. Let Android's
     * GestureDetector determine the type of gesture and then do the work in the GestureDetector's
     * listener methods.
     */
    @Override
    public boolean onTouch(View v, MotionEvent me)
    {
        // let the gesture detector process the touch event (see MyGestureListener below)
        gestureDetector.onTouchEvent(me);
        return true;
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

    // save state variables in the event of a screen rotation
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString(TITLE_KEY, title);
        savedInstanceState.putString(TEXT_KEY, text);
        savedInstanceState.putBoolean(SHOWING_BACK_KEY, showingBack);
        super.onSaveInstanceState(savedInstanceState);
    }

    // restore state variables after a screen rotation
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        title = savedInstanceState.getString(TITLE_KEY);
        text = savedInstanceState.getString(TEXT_KEY);
        showingBack = savedInstanceState.getBoolean(SHOWING_BACK_KEY);
    }

    // ====================
    // Inner classes at end
    // ====================

    // ==================================================================================================
    // A fragment representing the front of the card (select image accordingly)
    public static class CardFrontFragment extends Fragment
    {
        View frontView;
        TextView cardFrontText;
        EditText definition;
        Button enter;

        public CardFrontFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            frontView = inflater.inflate(R.layout.fragment_type_front, container, false);
            cardFrontText = (TextView) frontView.findViewById(R.id.textView4);
            cardFrontText.setText(title);
            definition = (EditText) frontView.findViewById(R.id.editTextText);
            enter = (Button) frontView.findViewById(R.id.enterButton);
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    def = definition.getText().toString().toLowerCase();
                    total++;
                    if(def.equals(text.toLowerCase())){
                        correct++;
                        corbool = true;
                        srs.add(cardNumber);
                        if(srs.size() == 15){
                            srs.clear();
                        }
                    }
                    else{
                        corbool = false;
                    }
                    correctView.setText(correct + "/" + total);

                    //flips it to back
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_enter,
                            R.animator.card_flip_exit).replace(R.id.container2, new typeModule.CardBackFragment()).commit();
                    showingBack = true;
                }
            });

            return frontView;
        }
    }

    // ==================================================================================================
    // A fragment representing the back of the card (select text and background color accordingly)
    public static class CardBackFragment extends Fragment
    {
        View backView;
        TextView titleView, textView, cor;

        Button nextButton;

        public CardBackFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            backView = inflater.inflate(R.layout.fragment_type_back, container, false);
            titleView = (TextView)backView.findViewById(R.id.text3);
            textView = (TextView)backView.findViewById(R.id.text4);
            cor = (TextView) backView.findViewById(R.id.textView5);
            nextButton = (Button) backView.findViewById(R.id.nextButton);
            titleView.setText(title);
            textView.setText(text);

            if(corbool){
                cor.setText("Correct");
                cor.setTextColor(0xFF239b56);

            }
            else{
                cor.setText("Wrong");
                cor.setTextColor(0xFFFF4545);
            }
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //generate random card number, go to it
                    int newCardNum = rand.nextInt(15);
                    while (srs.contains(newCardNum)) {
                        newCardNum = rand.nextInt(15);
                    }
                    cardNumber = newCardNum;
                    setCard(cardNumber);
                    getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_slide_left_enter,
                            R.animator.card_slide_right_exit).replace(R.id.container2, new typeModule.CardFrontFragment()).commit();

                }
            });

            return backView;
        }
    }

    // ==================================================================================================
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        running = false; // Stop the timer
    }
}