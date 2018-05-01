package doge.org.cs125mp7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pl.droidsonroids.gif.GifTextView;

public class Dog extends AppCompatActivity {

    static boolean exists = false;
    static int happy;
    GifTextView dogElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        dogElement = findViewById(R.id.dog100);
        if (!exists) {
            createDog();
        }
    }

    public void createDog() {
        happy = 100;
        exists = true;
        for (int cnt = 0; cnt <1000; cnt++) {
            dogElement.setVisibility(View.VISIBLE);
        }
    }

}
