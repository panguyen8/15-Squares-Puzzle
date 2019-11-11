package com.example.a15squarespuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;


/**
 * PuzzleView Class extends Surface View which controls all the GUI elements and mouse events
 *
 * @author Phuocan Nguyen
 * @version November 2019
 */

public class PuzzleView extends SurfaceView implements View.OnClickListener{

    // size 1 = 4x4
    // size 2 = 7x7
    protected int size = 1;

    //controller class
    PuzzleController puzzle = new PuzzleController(this);

    //initializing the paint values
    Paint backgroundColor = new Paint();
    Paint tileColor = new Paint();
    Paint textPaint = new Paint();


    /**
     * Constructor for PuzzleView
     *
     * @param context - needed to edit surface view
     * @param attrs - to receive attributes of surface view
     */
    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        //the colors for all of the graphics
        backgroundColor.setColor(Color.WHITE);
        tileColor.setColor(Color.BLACK);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(16);
    }

    /**
     * onClick listens for the buttons that are clicked
     *
     * @param v - A view for the onClickListener
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //resets the board
            case R.id.reset:
                puzzle.resetBoard();
                backgroundColor.setColor(Color.WHITE);
                break;

                //Turns the background green if the game is won
            case R.id.checkComplete:
                if (puzzle.checkComplete() == true) {
                    backgroundColor.setColor(Color.GREEN);
                }
                break;

                //sets the bored to 4x4
            case R.id.Normal:
                size = 1;
                break;

                //sets the board to 7x7
            case R.id.Large:
                size = 2;
                break;
        }
        invalidate();
    }


    /**
     * onDraw method that will be
     *
     * @param canvas - the canvas that will be drawn on
     */
    public void onDraw(Canvas canvas) {
        if (size == 1) {
            //draws the background rectangle
            canvas.drawRect(0, 0, 400, 400, backgroundColor);

            //draws each individual tile
            for (int xDir = 0; xDir < 4; xDir++) {
                for (int yDir = 0; yDir < 4; yDir++) {
                    drawBlankTile(canvas, xDir * 100, yDir * 100);
                }
            }

            //draws each individual number
            for (int xDir = 0; xDir < 4; xDir++) {
                for (int yDir = 0; yDir < 4; yDir++) {
                    drawNumbers(canvas, xDir * 100, yDir * 100, xDir, yDir);
                }
            }
        }
        else if (size == 2) {
            //draws the background rectangle
            canvas.drawRect(0, 0, 700, 700, backgroundColor);

            //draws each individual tile
            for (int xDir = 0; xDir < 7; xDir++) {
                for (int yDir = 0; yDir < 7; yDir++) {
                    drawBlankTile(canvas, xDir * 100, yDir * 100);
                }
            }

            //draws each individual number
            for (int xDir = 0; xDir < 7; xDir++) {
                for (int yDir = 0; yDir < 7; yDir++) {
                    drawNumbers(canvas, xDir * 100, yDir * 100, xDir, yDir);
                }
            }
        }
    }

    /**
     * drawNumbers draws the numbers on the grid
     *
     * @param canvas - the canvas that will be drawn on
     * @param xDir - the x coordinate to determine number of board
     * @param yDir - the y coordinate to determine number of board
     * @param xNum - the x coordinate used to draw the number on grid
     * @param yNum - the y coordinate used to draw the number on grid
     */
    public void drawNumbers(Canvas canvas, int xDir, int yDir, int xNum, int yNum) {
        //numbers for 4x4
        if (size == 1) {
            if (puzzle.boardNormal[xNum][yNum] != 0) {
                canvas.drawText(Integer.toString(puzzle.boardNormal[xNum][yNum]), xDir + 40, yDir + 50, textPaint);
            } else {
                //draws empty space instead of 0
                canvas.drawText(" ", xDir + 40, yDir + 50, textPaint);
            }
            //numbers for 7x7
        } else if (size == 2) {
            if (puzzle.boardLarge[xNum][yNum] != 0) {
                canvas.drawText(Integer.toString(puzzle.boardLarge[xNum][yNum]), xDir + 40, yDir + 50, textPaint);
            } else {
                //draws empty space instead of 0
                canvas.drawText(" ", xDir + 40, yDir + 50, textPaint);
            }
        }
    }

    /**
     * drawBlankTile draws the blank tiles of the grid
     *
     * @param canvas - the canvas that will be drawn on
     * @param xDir - the x-coordinate used to draw the square
     * @param yDir - the y-coordinate used to draw the square
     */
    public void drawBlankTile(Canvas canvas, int xDir, int yDir){
        canvas.drawRect(xDir+10, yDir+10, xDir+80, yDir+80, tileColor);
    }

    /**
     * onTouchEvent allows for the move capabilities of game.
     *     touch a tile and it will slide to the place that is not occupied
     *
     * @param e - records the
     */
    public boolean onTouchEvent(MotionEvent e) {
        if (size == 1) {
            //gets the coordinates of mouse click
            float x = e.getX();
            float y = e.getY();

            //turns the coordinates into ints and into single digits
            int xPiece = ((int) x) / 100;
            int yPiece = ((int) y) / 100;

            //if mouse click goes beyond parameters, return false
            if (xPiece > 3 || yPiece > 3) {
                return false;
            }


            //initialize xEnd and yEnd coordinates
            int xEnd = 3;
            int yEnd = 3;
            //find zero
            for (int yDir = 0; yDir < 4; yDir++) {
                for (int xDir = 0; xDir < 4; xDir++) {
                    if (puzzle.boardNormal[xDir][yDir] == 0) {
                        xEnd = xDir;
                        yEnd = yDir;
                    }
                }
            }

            //will only move if correct piece is touched
            puzzle.movePiece(xPiece, yPiece, xEnd, yEnd);
            invalidate();

            return true;
        }
        else if (size == 2){
            //get coordinates of mouse click
            float x = e.getX();
            float y = e.getY();

            //turn coordinates into ints
            int xPiece = ((int) x) / 100;
            int yPiece = ((int) y) / 100;

            //if coordinates go beyond parameters, return false
            if (xPiece > 6 || yPiece > 6) {
                return false;
            }


            //initialize xEnd and yEnd coordinates
            int xEnd = 3;
            int yEnd = 3;

            //find where the empty space is
            for (int yDir = 0; yDir < 7; yDir++) {
                for (int xDir = 0; xDir < 7; xDir++) {
                    if (puzzle.boardLarge[xDir][yDir] == 0) {
                        xEnd = xDir;
                        yEnd = yDir;
                    }
                }
            }

            //if move can be done, then move the piece
            puzzle.movePiece(xPiece, yPiece, xEnd, yEnd);
            invalidate();
            return true;
        }
        return true;
    }

    /**
     * returnSize returns the size of the grid
     *
     */
    public int getSize() {
        return size;
    }


}
