package com.hieuminh.scores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener, View.OnLongClickListener {
    private Button playerShow[] = new Button[4], Return, Vieww;
    private Button playerScore[] = new Button[4];
    private TextView title;
    private ImageView logo;
    private int mark[], score;
    private ArrayList<String> listMark = new ArrayList(), listName = null;
    private String text;
    private int match, add;
    private int listStore[][] = new int[50][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        connectView();
        init();
    }

    private void connectView() {
        Return = (Button) findViewById(R.id.Return);
        Vieww = (Button) findViewById(R.id.View);

        playerShow[0] = (Button) findViewById(R.id.player1Show);
        playerShow[1] = (Button) findViewById(R.id.player2Show);
        playerShow[2] = (Button) findViewById(R.id.player3Show);
        playerShow[3] = (Button) findViewById(R.id.player4Show);

        playerScore[0] = (Button) findViewById(R.id.player1Score);
        playerScore[1] = (Button) findViewById(R.id.player2Score);
        playerScore[2] = (Button) findViewById(R.id.player3Score);
        playerScore[3] = (Button) findViewById(R.id.player4Score);

        title = (TextView) findViewById(R.id.title1);
        logo = (ImageView) findViewById(R.id.logo1);

        Return.setOnClickListener(this);
        Vieww.setOnClickListener(this);

        for (int i = 0; i < 4; i++) {
            playerScore[i].setOnClickListener(this);
            playerShow[i].setOnLongClickListener(this);
        }

        logo.setOnClickListener(this);
    }

    private void init() {
        Intent intent = getIntent();
        listName = intent.getStringArrayListExtra("list");
        match = 1; score = 3; text = "";
        mark = new int[]{0, 0, 0, 0};
        for (int i = 0; i < listStore.length; i++) {
            for (int j = 0; j < listStore[0].length; j++) {
                listStore[i][j] = 0;
            }
        }
        for (int i = 0; i < 4; i++) {
            playerShow[i].setText(listName.get(i));
            playerScore[i].setText("" + score);
        }
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.logo1: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure that you want to exit?");
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                alertDialogBuilder.setPositiveButton("yes", this);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            break;
            case R.id.player1Score: {
                check(0, playerScore[0]);
            }
            break;
            case R.id.player2Score: {
                check(1, playerScore[1]);
            }
            break;
            case R.id.player3Score: {
                check(2, playerScore[2]);
            }
            break;
            case R.id.player4Score: {
                check(3, playerScore[3]);
            }
            break;
            case R.id.Return: {
                if (score == 3) {
                    if (!listMark.isEmpty()) {
                        for (int i = 0; i < 4; i++) {
                            mark[i] -= listStore[match - 2][i];
                            listStore[match - 2][i] = 0;
                        }
                        listMark.remove((match--) - 2);
                        title.setText("Match: " + match);

                    }
                } else {
                    score = 3;
                    for (int i = 0; i < 4; i++) {
                        listStore[match - 1][i] = 0;
                        playerScore[i].setText("" + score);
                        playerScore[i].setEnabled(true);
                    }
                }
            }
            break;
            case R.id.View: {
                Display();
            }
            break;
        }
    }

    public void check(int i, Button ps) {
        if (score == 1) {
            listStore[match - 1][i] += 1;
            score = 3;
            String m = (match < 10) ? match + ".  " : match + ".";
            listMark.add(String.format(m + "   %d                    %d                       %d                      %d"
                    , listStore[match - 1][0], listStore[match - 1][1], listStore[match - 1][2], listStore[match - 1][3]));

            for (int j = 0; j < 4; j++) {
                mark[j] += listStore[match - 1][j];
                playerScore[j].setText("" + score);
                playerScore[j].setEnabled(true);
            }
            title.setText("Match: " + (++match));
            if (match > 50) init();
            Display();
        } else {
            listStore[match - 1][i] += score--;
            for (int j = 0; j < 4; j++) {
                if (listStore[match - 1][j] == 0) {
                    playerScore[j].setText("" + score);
                }
            }
            ps.setEnabled(false);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        System.out.println(dialogInterface.toString() + " " + i);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.player1Show: {
                AlertDialogChooseAdd(0);
            }
            break;
            case R.id.player2Show: {
                AlertDialogChooseAdd(1);
            }
            break;
            case R.id.player3Show: {
                AlertDialogChooseAdd(2);
            }
            break;
            case R.id.player4Show: {
                AlertDialogChooseAdd(3);
            }
            break;
        }
        return false;
    }

    private void AlertDialogChooseAdd(final int index) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add point for player " + (index + 1));
        final String[] markAdd = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        final Set<String> selectedItems = new HashSet();
        selectedItems.add(markAdd[3]);
        alertDialogBuilder.setSingleChoiceItems(markAdd, 3, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedItems.clear();
                selectedItems.add(markAdd[i]);
            }
        });
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (selectedItems.isEmpty()) {
                    return;
                }
                int add = Integer.parseInt(selectedItems.iterator().next().trim());
//
                listStore[match - 1][index] += add;
                dialogInterface.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void Display() {
        Intent intent = new Intent(this, MainActivity1.class);
        intent.putStringArrayListExtra("listMark", listMark);
        intent.putExtra("listScore", mark);
        intent.putStringArrayListExtra("listName", listName);
        startActivity(intent);
    }
}