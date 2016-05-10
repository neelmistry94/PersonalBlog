package edu.umd.bferraro.personalblog;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by danieldresner on 5/5/16.
 */
public class LockScreenActivity extends Activity {

    Button zero, one, two, three, four, five, six, eight, nine, unlock, delete;
    ImageButton seven;
    TextView numText;
    int code = 0;
    Intent homePageIntent;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);

        zero = (Button) findViewById(R.id.buttonKey0);
        one = (Button) findViewById(R.id.buttonKey1);
        two = (Button) findViewById(R.id.buttonKey2);
        three = (Button) findViewById(R.id.buttonKey3);
        four = (Button) findViewById(R.id.buttonKey4);
        five = (Button) findViewById(R.id.buttonKey5);
        six = (Button) findViewById(R.id.buttonKey6);
        seven = (ImageButton) findViewById(R.id.buttonKey7);
        eight = (Button) findViewById(R.id.buttonKey8);
        nine = (Button) findViewById(R.id.buttonKey9);
        unlock = (Button) findViewById(R.id.unlock);
        delete = (Button) findViewById(R.id.delete);

        numText = (TextView) findViewById(R.id.textViewOutputScreen);

        zero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 1;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 2;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 3;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 4;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 5;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 6;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 7;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 8;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(code/1000 == 0){
                    code *= 10;
                    code += 9;
                    numText.setText(Integer.toString(code));
                }
            }
        });
        unlock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (code == 3861){
                    homePageIntent = new Intent(LockScreenActivity.this, HomePageActivity.class);
                    startActivityForResult(homePageIntent, 0);
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(LockScreenActivity.this).create();
                    alertDialog.setTitle("Incorrect Passcode!");
                    alertDialog.show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                code /= 10;
                if(code == 0){
                    numText.setText("");
                }
                else{
                    numText.setText(Integer.toString(code));
                }
            }
        });
//


    }



}
