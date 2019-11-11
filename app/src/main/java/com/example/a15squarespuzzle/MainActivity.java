package com.example.a15squarespuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

/**
 * MainActivity class which connects listeners with surface view and buttons
 *
 * @author Phuocan Nguyen
 * @version November 2019
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PuzzleView puzzle = (PuzzleView) findViewById(R.id.surfaceView);
        PuzzleController puzzleControl = new PuzzleController(puzzle);

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(puzzle);

        Button checkComplete = (Button) findViewById(R.id.checkComplete);
        checkComplete.setOnClickListener(puzzle);

        Button larger = (Button) findViewById(R.id.Large);
        larger.setOnClickListener(puzzle);

        Button smaller = (Button) findViewById(R.id.Normal);
        smaller.setOnClickListener(puzzle);

    }
}
