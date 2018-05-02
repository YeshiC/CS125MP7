package doge.org.cs125mp7;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifTextView;

public class Dog extends AppCompatActivity {

    static boolean exists = false;
    //doge's level of happiness
    static int happy;
    //determine which interaction is selected atm
    static int selected;
    //gif element for dog.gif
    static GifTextView dog;
    //gif element for dogsleeping.gif
    static GifTextView dogSleeping;
    //button to select food
    static ImageButton food;
    //Happiness bar
    static ImageView hBar;
    static int tempature;
    static ImageButton pets;
    static boolean asleep;
    static int hunger;

    //dog.setVisibility(View.VISIBLE);
    //food.setImageResource(R.drawable.heart2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);

        MainMenu temp3 = new MainMenu();
        tempature = temp3.getTemp();
        //connects the varibles in this java class with xml elements
        food = findViewById(R.id.food);
        dog = findViewById(R.id.dog100);
        dogSleeping = findViewById(R.id.dog200);
        hBar = findViewById(R.id.red);
        pets = findViewById(R.id.pet);

        if (!exists) {
            createDog();
        }
        setHBar();

        setFood();
        main(new String[0]);

    }

    public static void main(String[] args) {

        Runnable helloRunnable = new Runnable() {
            public void run() {
                setHBar();
                setFood();
                if (hunger > 60 && asleep) {
                    wakeUp();
                }
                if (hunger > 80) {
                    happy -= hunger + 80;
                }
                if (hunger < 100) {
                    hunger++;
                }

                happy -= 4;
            }
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(helloRunnable, 0, 5, TimeUnit.SECONDS);


        food.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setHBar();

                if (hunger < 0) {
                    hunger = 0;
                }

                else if (hunger > 100) {
                    hunger = 100;
                }

                else if (hunger >= 20) {
                    happy += 5;
                    hunger -= 30;
                }

                if (hunger < 20 && happy > 90 && !asleep) {
                    goToSleep();
                }

                setHBar();
            }
        });

        pets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                happy += 10;
                setHBar();
            }
        });

    }


    public void createDog() {
        happy = 50;
        hunger = 50;
        asleep = false;
        exists = true;
    }

    public static void goToSleep() {
        dog.setVisibility(View.INVISIBLE);
        dogSleeping.setVisibility(View.VISIBLE);
    }

    public static void wakeUp() {
        dog.setVisibility(View.VISIBLE);
        dogSleeping.setVisibility(View.INVISIBLE);
    }

    public static void setHBar() {

        MainMenu temp2 = new MainMenu();
        setFood();
        if (happy > 95) {
            happy = 95;
        }
        if (happy < 0) {
            happy = 0;
        }
        ViewGroup.LayoutParams params = hBar.getLayoutParams();
        params.height = happy * 6 + 30;
        hBar.setLayoutParams(params);
        tempature = temp2.getTemp();
        setFood();
    }

    public static void setFood() {
        if (tempature < 40) {
            food.setImageResource(R.drawable.food3);
        }
        else if (tempature > 95) {
            food.setImageResource(R.drawable.food4);
        }
        else {
            food.setImageResource(R.drawable.food2);
        }
    }

}
