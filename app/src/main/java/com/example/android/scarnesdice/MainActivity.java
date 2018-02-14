package com.example.android.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int userScore = 0;
    int user_roundScore = 0;
    int comp_roundScore = 0;
    int computerScore = 0;
    boolean userTurn;
    boolean gameOver;
    int WINNING_SCORE = 100;
    int MAX_COMP_TURN_SCORE = 20;
    Random rand;
    int ImageList[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userTurn = true;

        ImageView image = (ImageView) findViewById(R.id.Dice);
        image.setImageResource(ImageList[(5)]);

        TextView scoreView = (TextView)findViewById(R.id.scoreView);
        scoreView.setText("Your Score: 0       Computer Score: 0");

        TextView turnView = (TextView)findViewById(R.id.turnView);
        turnView.setText("Your turn");



        Button rollButton = (Button) findViewById(R.id.Roll);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    roll();

            }
        });


        Button holdButton = (Button) findViewById(R.id.Hold);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (userTurn && !gameOver) {

                    hold();
//                    updateScore();
//                    if (!gameOver) computerTurn();
//                }
            }
        });

        Button resetButton = (Button) findViewById(R.id.Reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();

            }
        });
    }



public void roll() {
        rand = new Random();
        int roll = rand.nextInt(6) + 1;

        ImageView dieImg = (ImageView)findViewById(R.id.Dice);
        dieImg.setImageResource(ImageList[(roll - 1)]);


        if (roll == 1) {
        user_roundScore = 0;
        updateRoundScore(0);
        switchTurn();
        } else {
        user_roundScore += roll;
        updateRoundScore(user_roundScore);
        }
}


public void hold() {

        if (userTurn) userScore += user_roundScore;
        else computerScore += user_roundScore;

        updateScore();

        user_roundScore = 0;
        updateRoundScore(0);

        if ((userScore > WINNING_SCORE) || (computerScore > WINNING_SCORE)) gameOver();

        if (!gameOver) switchTurn();
    }

    public void updateScore() {
        TextView scoreView = (TextView) findViewById(R.id.scoreView);
        if (gameOver) {
            if (userScore >= WINNING_SCORE) {
                scoreView.setText("Your Score: " + userScore + "    You won!");
            } else {
                scoreView.setText("Computer's Score: " + computerScore + "    Computer won!");
            }
        } else {
            scoreView.setText("Your Score: " + userScore + "           \t Computer Score: " + computerScore);
        }
    }

    private void updateRoundScore(int score) {
        TextView turnScoreView = (TextView)findViewById(R.id.turnScore);
        turnScoreView.setText("Turn score: " + user_roundScore);
    }

    public void reset() {
        userScore = 0;
        computerScore = 0;

        user_roundScore = 0;
        updateRoundScore(0);

        userTurn = true;
        gameOver = false;

        TextView scoreView = (TextView)findViewById(R.id.scoreView);
        scoreView.setText("Your Score: 0    Computer Score: 0");

        TextView turnView = (TextView)findViewById(R.id.turnView);
        turnView.setText("Your turn");

        ImageView dieImg = (ImageView)findViewById(R.id.Dice);
        dieImg.setImageResource(ImageList[5]);

        Button rollButton = (Button) findViewById(R.id.Roll);
        rollButton.setEnabled(true);
        Button holdButton = (Button) findViewById(R.id.Hold);
        holdButton.setEnabled(true);

    }


    public void gameOver() {
        // disable roll & hold buttons
        Button rollButton1 = (Button) findViewById(R.id.Roll);
        rollButton1.setEnabled(false);
        Button holdButton1 = (Button) findViewById(R.id.Hold);
        holdButton1.setEnabled(false);

        gameOver = true;
    }

    public void switchTurn() {

        TextView turnView = (TextView) findViewById(R.id.turnView);

        if (userTurn)
            turnView.setText("Computer's Turn");
        else
            turnView.setText("Your Turn");

        userTurn = !userTurn;

        if (userTurn) {
            Button rollButton = (Button) findViewById(R.id.Roll);
            rollButton.setEnabled(true);
            Button holdButton = (Button) findViewById(R.id.Hold);
            holdButton.setEnabled(true);
        }

        if (!userTurn) computerTurn();
    }

    public void computerTurn() {

        final Handler handler = new Handler();

        // disable roll & hold buttons
        Button rollButton = (Button) findViewById(R.id.Roll);
        rollButton.setEnabled(false);
        Button holdButton = (Button) findViewById(R.id.Hold);
        holdButton.setEnabled(false);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((user_roundScore + computerScore >= WINNING_SCORE) || (user_roundScore >= MAX_COMP_TURN_SCORE))
                    hold();
                else {
                    roll();
                }

                if (!userTurn) handler.postDelayed(this, 1000);
                else updateRoundScore(0);
            }
        }, 1000);

        updateScore();

        user_roundScore = 0;
        updateRoundScore(0);

    }


}
