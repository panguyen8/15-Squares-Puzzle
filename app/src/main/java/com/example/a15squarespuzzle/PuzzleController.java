package com.example.a15squarespuzzle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * PuzzleController class which controls the gameplay of the game.
 *
 * Click to move and when puzzle is done, click verify. If board turns green
 * it indicates a correct board and will not switch otherwise.
 *
 * Board can be 4x4 of 7x7
 *
 * @author Phuocan Nguyen
 * @version November 2019
 */


public class PuzzleController{
    //initialize puzzleV and 4x4 board and 7x7 board
    protected PuzzleView puzzleV;
    protected int boardNormal[][] = new int[4][4];
    protected int boardLarge[][] = new int[7][7];

    /**
     * Constructor for PuzzleController
     *
     * @param view - needed to edit surface view
     */
    public PuzzleController(PuzzleView view) {
        puzzleV = view;
        buildBoard();
    }

    /**
     * buildBoard is a method that initializes 2d array
     *
     */
    public void buildBoard() {
        //for boardSmall
        int count = 1;
        for (int yDir = 0; yDir < 4; yDir++) {
            for (int xDir = 0; xDir < 4; xDir++) {
                boardNormal[xDir][yDir] = count;
                count++;
            }
        }
        boardNormal[3][3] = 0;
        resetBoard();

        //set size to two to randomize board
        puzzleV.size = 2;
        //for boardLarge
        int countLarge = 1;
        for (int yDir = 0; yDir < 7; yDir++) {
            for (int xDir = 0; xDir < 7; xDir++) {
                boardLarge[xDir][yDir] = countLarge;
                countLarge++;
            }
        }
        boardLarge[6][6] = 0;

        //reset board randomizes the values
        resetBoard();

        //turn size back into 1
        puzzleV.size = 1;
    }

    /**
     * movePiece takes two coordinates to move the pieces
     *
     */
    public void movePiece(int oldX, int oldY, int newX, int newY) {
        //checks to make sure that only the adjacent pieces can be moved
        if (newX > (oldX+1) || newX < (oldX-1)) {
            return;
        }
        if (newY > (oldY+1) || newY < (oldY-1)) {
            return;
        }
        //checks for diagonals, but makes sure that it doesn't go out of bounds
        if (oldX == 0) {
            //top right
            if ((newX == (oldX+1)) && (newY == (oldY-1))) {
                return;
            }
            //top left
            if ((newX == (oldX-1)) && (newY == (oldY-1))) {
                return;
            }
        }
        if (oldX == 3) {
            //bottom right
            if ((newX == (oldX+1)) && (newY == (oldY+1))) {
                return;
            }
            //bottom left
            if ((newX == (oldX-1)) && (newY == (oldY+1))) {
                return;
            }
        }
        if (oldY == 0) {
            //bottom left
            if ((newX == (oldX-1)) && (newY == (oldY+1))) {
                return;
            }
            //top left
            if ((newX == (oldX-1)) && (newY == (oldY-1))) {
                return;
            }
        }
        if (oldY == 3) {
            //bottom right
            if ((newX == (oldX+1)) && (newY == (oldY+1))) {
                return;
            }
            //top right
            if ((newX == (oldX+1)) && (newY == (oldY-1))) {
                return;
            }
        }
        //bottom right
        if ((newX == (oldX+1)) && (newY == (oldY+1))) {
            return;
        }
        //bottom left
        if ((newX == (oldX-1)) && (newY == (oldY+1))) {
            return;
        }
        //top right
        if ((newX == (oldX+1)) && (newY == (oldY-1))) {
            return;
        }
        //top left
        if ((newX == (oldX-1)) && (newY == (oldY-1))) {
            return;
        }

        //for 4x4
        if (puzzleV.getSize() == 1) {
            if (boardNormal[newX][newY] != 0) {
                return;
            }
            //moves the piece
            boardNormal[newX][newY] = boardNormal[oldX][oldY];
            boardNormal[oldX][oldY] = 0;

            //for 7x7
        } else if (puzzleV.getSize() == 2) {
            if (boardLarge[newX][newY] != 0) {
                return;
            }
            //moves the pieces
            boardLarge[newX][newY] = boardLarge[oldX][oldY];
            boardLarge[oldX][oldY] = 0;
        }
    }

    /**
     * checkComplete checks whether the user has won game
     *
     * @return returns true if the user has correctly completed puzzle
     */
    public boolean checkComplete() {
        //counter that counts each tile and make sure it corresponds with the correct one
        if (puzzleV.getSize() == 1) {
            int count = 0;
            for (int yDir = 0; yDir < 4; yDir++) {
                for (int xDir = 0; xDir < 4; xDir++) {
                    count++;
                    //16th tile should be empty, so return true
                    if (count == 16) {
                        return true;
                    }
                    if (boardNormal[xDir][yDir] != count) {
                        return false;
                    }
                }
            }
        }
        //for 7x7
        else if (puzzleV.getSize() == 2) {
            int count = 0;
            for (int yDir = 0; yDir < 7; yDir++) {
                for (int xDir = 0; xDir < 7; xDir++) {
                    count++;
                    //49th spot is empty, and so return true
                    if (count == 49) {
                        return true;
                    }
                    if (boardLarge[xDir][yDir] != count) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * resetBoard resets the entire board randomly
     *
     */
    public void resetBoard() {
        //for 4x4
        if (puzzleV.getSize() == 1) {
            //randomizes the board
            shuffle(boardNormal);

            //initialize xy coordinates of the 0th spot
            int emptyX = 0;
            int emptyY = 0;

            //look for empty spot
            for (int yDir = 0; yDir < 4; yDir++) {
                for (int xDir = 0; xDir < 4; xDir++) {
                    if (boardNormal[xDir][yDir] == 0) {
                        emptyX = xDir;
                        emptyY = yDir;
                    }
                }
            }

            //switch the spot at [3][3] with 0
            int temp = boardNormal[3][3];
            boardNormal[emptyX][emptyY] = temp;
            boardNormal[3][3] = 0;

            //for 7x7
        } else if (puzzleV.getSize() == 2) {
            //randomizes large board
            shuffle(boardLarge);
            //look for empty spot
            int emptyX = 0;
            int emptyY = 0;
            //look for where 0 is
            for (int yDir = 0; yDir < 7; yDir++) {
                for (int xDir = 0; xDir < 7; xDir++) {
                    if (boardLarge[xDir][yDir] == 0) {
                        emptyX = xDir;
                        emptyY = yDir;
                    }
                }
            }

            //switch spot at [6][6] with 0
            int temp = boardLarge[6][6];
            boardLarge[emptyX][emptyY] = temp;
            boardLarge[6][6] = 0;
        }
    }

    /*
     * Shuffle method used for randomizing the array
     *
     * @param a - 2d int array
     *
     * CITATION - FISHER YATES ALGORITHM FOR RANDOMIZATION
     * Author: Ronald Fisher and Frank Yates
     * Date: 1938 discovered, 2014 stackoverflow
     * Available: https://stackoverflow.com/questions/20190110/2d-int-array-shuffle
     */
    void shuffle(int[][] a) {
        Random random = new Random();

        for (int i = a.length - 1; i > 0; i--) {
            for (int j = a[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                //changes where the array items are placed
                int temp = a[i][j];
                a[i][j] = a[m][n];
                a[m][n] = temp;
            }
        }
    }



}
