package com.kpbird;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.kpbird.SeekBarWithTwoThumb.SeekBarChangeListener;

public class MySeekBar extends Activity implements SeekBarChangeListener{

	TextView tv1,tv2;
	SeekBarWithTwoThumb swtt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        swtt = (SeekBarWithTwoThumb) findViewById(R.id.myseekbar);
        swtt.setSeekBarChangeListener(this);
      
        
     
    }
    
	public void SeekBarValueChanged(int Thumb1Value, int Thumb2Value) {
		tv1.setText("Thumb 1 Value :" +Thumb1Value + " %" );
		tv2.setText("Thumb 2 Value :"+Thumb2Value + " %") ;
	}

}