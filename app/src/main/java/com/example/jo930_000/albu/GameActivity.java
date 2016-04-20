package com.example.jo930_000.albu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


/**
 * Created by 11 on 2016-04-18.
 */
public class GameActivity extends AppCompatActivity {

    GridView gameCardView;
    TextView countDown;

    GameAdapter gameAdapter;
    int[] randomNum;
    Game game;
    Handler mHandler;
    BackThread backThread;

    int timeLimit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        randomNum = new int[10];
        game = new Game();
        game.generateRandomNumber(this.randomNum);

        timeLimit = 10;

        gameCardView = (GridView) findViewById(R.id.gameboard);
        countDown = (TextView) findViewById(R.id.countdown);
        //Intent intent = getIntent();
        gameAdapter = new GameAdapter(this, R.layout.itemforgamecard, this.randomNum);
        gameCardView.setAdapter(gameAdapter);

        mHandler = new Handler();
        backThread = new BackThread();
        backThread.start();

        /*
        SystemClock.sleep(10000);
        Intent i = new Intent();
        setResult(1,i);
        finish();
        */
    }

    private boolean GameSuccess(){

        return false;
    }


    class GameAdapter extends BaseAdapter {
        private int[] index;
        private Context context;
        private int count = 1;


        Intent intent;
        LayoutInflater inflater;

        public GameAdapter(Context aContext, int aLayout, int[] aIndex) {
            this.inflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = aContext;
            this.index = aIndex;
        }

        public int getCount() {
            return 10;
        }

        public Object getItem(int position) {
            return index[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.itemforgamecard, parent, false);
            }
            Button button = (Button) convertView.findViewById(R.id.numbercard);
            button.setText("" + index[position]);

            button.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    if (index[position] == count) {
                        v.setBackgroundResource(R.drawable.clicked);
                        count++;
                    }

                }
            });

            if (count == 11) {
                intent = new Intent(context, Bye.class);
                context.startActivity(intent);
            }
            return convertView;
        }
    }

    private class BackThread extends Thread {
        Intent intent;
        public void run() {
            super.run();
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        timeLimit--;
                        countDown.setText(""+timeLimit);
                    }
                });

                if(timeLimit == 0)
                    break;
            }

            //intent = new Intent(getApplicationContext(), AlarmSound.class);
            //startActivity(intent);
        }
    }
}