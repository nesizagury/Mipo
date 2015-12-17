package com.example.aaa;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);



        TextView tv1 = (TextView) findViewById(R.id.textView3);
        TextView tv2 = (TextView) findViewById(R.id.textView4);
        TextView tv3 = (TextView) findViewById(R.id.textView5);
        TextView tv4 = (TextView) findViewById(R.id.textView6);
        TextView tv5 = (TextView) findViewById(R.id.textView7);
        TextView tv6 = (TextView) findViewById(R.id.textView8);
        TextView tv7 = (TextView) findViewById(R.id.textView9);
        TextView tv8 = (TextView) findViewById(R.id.textView10);
        TextView tv9 = (TextView) findViewById(R.id.textView11);
        TextView tv10 = (TextView) findViewById(R.id.textView12);
        TextView tv11 = (TextView) findViewById(R.id.textView13);
        TextView tv12 = (TextView) findViewById(R.id.textView14);
        TextView tv13 = (TextView) findViewById(R.id.textView15);

        StringBuilder contents = new StringBuilder();
        InputStream is = this.getResources().openRawResource(R.raw.user0);
        BufferedReader input =  new BufferedReader(new InputStreamReader(is), 1024*8);

        String line = null;
        List <String> list = new ArrayList<String>();
        try {
            int i = 0;
            while (( line = input.readLine()) != null){
                if (i%2 == 0)
                list.add(line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tv1.setText(list.get(0));
        tv2.setText(list.get(1));
        tv3.setText(list.get(2));
        tv4.setText(list.get(3));
        tv5.setText(list.get(4));
        tv6.setText(list.get(5));
        tv7.setText(list.get(6));
        tv8.setText(list.get(7));
        tv9.setText(list.get(8));
        tv10.setText(list.get(9));
        tv11.setText(list.get(10));
        tv12.setText(list.get(11));
        tv13.setText(list.get(13));



    }

}
