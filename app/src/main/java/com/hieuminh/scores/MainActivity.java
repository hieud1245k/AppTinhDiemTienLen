package com.hieuminh.scores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText player[] = new EditText[4];
    private Button reset, next;
    private TextView title;
    private ArrayList list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
    }

    private void connectView() {
        title = (TextView) findViewById(R.id.title);
        reset = (Button) findViewById(R.id.reset);
        next = (Button) findViewById(R.id.next);
        player[0] = (EditText) findViewById(R.id.player1);
        player[1] = (EditText) findViewById(R.id.player2);
        player[2] = (EditText) findViewById(R.id.player3);
        player[3] = (EditText) findViewById(R.id.player4);
        reset.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        list.removeAll(list);
        if (view.getId() == R.id.reset) {
            title.setText("Enter the names of 4 players");
            for (int i = 0; i < 4; i++) {
                player[i].setText("");
            }
        }
        if (view.getId() == R.id.next) {
            for (int i = 0; i < 4; i++) {
                if (player[i].getText().toString().equals("")) {
                    Toast.makeText(this, "Player " + (i + 1) + " is empty", Toast.LENGTH_SHORT).show();
                    list.removeAll(list);
                    return;
                }
                list.add(player[i].getText().toString());
            }
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putStringArrayListExtra("list", list);
            startActivity(intent);
        }
    }
}