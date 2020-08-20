package com.hieuminh.scores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {
    private Button playerShow[] = new Button[4];
    private TextView playerScore[] = new TextView[4];
    private ListView List;
    private ArrayList<String> listName = null, listMark = null;
    private int listScore[] = new int[4];
    private TableRow tabModeScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        connectView();
        Intent intent = getIntent();
        listName = intent.getStringArrayListExtra("listName");
        listMark = intent.getStringArrayListExtra("listMark");
        listScore = intent.getIntArrayExtra("listScore");

        for (int i = 0; i < 4; i++) {
            playerShow[i].setText(listName.get(i));
            playerScore[i].setText("*");
            playerShow[i].setBackgroundColor(0xFFFFFFFF);
        }

        ArrayAdapter aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMark);
        List.setAdapter(aa);

    }

    private void connectView() {
        playerShow[0] = (Button) findViewById(R.id.player1Show1);
        playerShow[1] = (Button) findViewById(R.id.player2Show2);
        playerShow[2] = (Button) findViewById(R.id.player3Show3);
        playerShow[3] = (Button) findViewById(R.id.player4Show4);

        playerScore[0] = (TextView) findViewById(R.id.score1);
        playerScore[1] = (TextView) findViewById(R.id.score2);
        playerScore[2] = (TextView) findViewById(R.id.score3);
        playerScore[3] = (TextView) findViewById(R.id.score4);

        tabModeScore = (TableRow) findViewById(R.id.tabModeScore);
        List = (ListView) findViewById(R.id.scoreList);

        tabModeScore.setOnClickListener(this);
    }

    private void master() {
        int listTam[] = listScore;
        ArrayList listTT = new ArrayList<Integer>();
        listTT.add(listTam[0]);
        listTT.add(listTam[1]);
        listTT.add(listTam[2]);
        listTT.add(listTam[3]);
        Collections.sort(listTT);
        setBackgroundButton((int) listTT.get(2));
        setBackgroundButton((int) listTT.get(3));
    }

    public void setBackgroundButton(int value) {
        for (int i = 0; i < 4; i++) {
            if (value == listScore[i]) {
                playerShow[i].setBackgroundColor(0xFF00FF00);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tabModeScore) {
            if (playerScore[0].getText().toString().equals("*")) {
                for (int i = 0; i < 4; i++) {
                    playerScore[i].setText("" + listScore[i]);
                }
                master();
            } else {
                for (int i = 0; i < 4; i++) {
                    playerScore[i].setText("*");
                    playerShow[i].setBackgroundColor(0xFFFFFFFF);
                }
            }
        }
    }
}