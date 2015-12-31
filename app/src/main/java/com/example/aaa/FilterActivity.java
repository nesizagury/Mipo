package com.example.aaa;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FilterActivity extends Activity {

    private String array_spinner[];
    private String array_spinner2[];
    private String array_spinner3[];
    private String array_spinner4[];

    EditText age_minET;
    EditText age_maxET;
    TextView ageTo_TV;


    TextView height_minTV;
    TextView height_maxTV;
    EditText height_minET;
    EditText height_maxET;

    TextView weight_minTV;
    TextView weight_maxTV;
    EditText weight_minET;
    EditText weight_maxET;

    Spinner s;
    Spinner s2;
    Spinner s3;
    Spinner s4;

    boolean age_flag = false;
    boolean looking_flag = false;
    boolean height_flag = false;
    boolean weight_flag = false;
    boolean body_flag = false;
    boolean origin_flag = false;
    boolean relationship_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ageTo_TV = (TextView) findViewById(R.id.ageTo_TV);
        age_minET = (EditText) findViewById(R.id.age_minET);
        age_maxET = (EditText) findViewById(R.id.age_maxET);

        height_minTV = (TextView) findViewById(R.id.height_minTV);
        height_maxTV = (TextView) findViewById(R.id.height_maxTV);
        height_minET = (EditText) findViewById(R.id.height_minET);
        height_maxET = (EditText) findViewById(R.id.height_maxET);

        weight_minTV = (TextView) findViewById(R.id.weight_minTV);
        weight_maxTV = (TextView) findViewById(R.id.weight_maxTV);
        weight_minET = (EditText) findViewById(R.id.weight_minET);
        weight_maxET = (EditText) findViewById(R.id.weight_maxET);


        setSpinners();

    }

    private void setSpinners() {

        array_spinner=new String[4];
        array_spinner[0]="All";
        array_spinner[1]="Dates";
        array_spinner[2]="Friends";
        array_spinner[3]="Chat";
        s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        array_spinner2=new String[4];
        array_spinner2[0]="All";
        array_spinner2[1]="Slim";
        array_spinner2[2]="Toned";
        array_spinner2[3]="Stocky";
        s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner2);
        s2.setAdapter(adapter2);

        array_spinner3=new String[4];
        array_spinner3[0]="All";
        array_spinner3[1]="Middle Eastern";
        array_spinner3[2]="Native American";
        array_spinner3[3]="South Asian";
        s3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter adapter3 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner3);
        s3.setAdapter(adapter3);

        array_spinner4=new String[4];
        array_spinner4[0]="All";
        array_spinner4[1]="Single";
        array_spinner4[2]="Divorced";
        array_spinner4[3]="Open Relationship";
        s4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter adapter4 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner4);
        s4.setAdapter(adapter4);

    }


    public void showAgeEditors(View v){

        if(!age_flag)
        {
            ageTo_TV.setVisibility(View.VISIBLE);
            age_minET.setVisibility(View.VISIBLE);
            age_maxET.setVisibility(View.VISIBLE);
            age_flag = true;
        }
        else
        {
            ageTo_TV.setVisibility(View.INVISIBLE);
            age_minET.setVisibility(View.INVISIBLE);
            age_maxET.setVisibility(View.INVISIBLE);
            age_flag = false;
        }

    }


    public void showLookingForEditors(View v) {

        if(!looking_flag)
        {
            s.setVisibility(View.VISIBLE);
            looking_flag = true;
        }
        else
        {
            s.setVisibility(View.INVISIBLE);
            looking_flag = false;
        }

    }

    public void showHeightEditors(View v) {

         if(!height_flag)
         {
             height_minTV.setVisibility(View.VISIBLE);
             height_maxTV.setVisibility(View.VISIBLE);
             height_minET.setVisibility(View.VISIBLE);
             height_maxET.setVisibility(View.VISIBLE);
             height_flag = true;
         }
        else
         {
             height_minTV.setVisibility(View.INVISIBLE);
             height_maxTV.setVisibility(View.INVISIBLE);
             height_minET.setVisibility(View.INVISIBLE);
             height_maxET.setVisibility(View.INVISIBLE);
             height_flag = false;
         }

    }

    public void showWeightEditors(View v) {

        if(!weight_flag)
        {
            weight_minTV.setVisibility(View.VISIBLE);
            weight_maxTV.setVisibility(View.VISIBLE);
            weight_minET.setVisibility(View.VISIBLE);
            weight_maxET.setVisibility(View.VISIBLE);
            weight_flag = true;
        }
        else
        {
            weight_minTV.setVisibility(View.INVISIBLE);
            weight_maxTV.setVisibility(View.INVISIBLE);
            weight_minET.setVisibility(View.INVISIBLE);
            weight_maxET.setVisibility(View.INVISIBLE);
            weight_flag = false;
        }

    }


    public void showBodyTypeEditors(View v) {


        if(!body_flag)
        {
            s2.setVisibility(View.VISIBLE);
            body_flag = true;
        }
        else
        {
            s2.setVisibility(View.INVISIBLE);
            body_flag = false;
        }

    }


    public void showOriginEditors(View v) {

        if(!origin_flag)
        {
            s3.setVisibility(View.VISIBLE);
            origin_flag = true;
        }
        else
        {
            s3.setVisibility(View.INVISIBLE);
            origin_flag = false;
        }

    }

    public void showRelationshipStatusEditors(View v) {

        if(!relationship_flag)
        {
            s4.setVisibility(View.VISIBLE);
            relationship_flag = true;
        }
        else
        {
            s4.setVisibility(View.INVISIBLE);
            relationship_flag = false;
        }

    }

    public int getMinAge(){

        if(!(age_minET.getText().toString().equals("")))
             return Integer.parseInt(age_minET.getText().toString());
        else
            return 0;

    }

    public int getMaxAge(){

        if(!(age_maxET.getText().toString().equals("")))
            return Integer.parseInt(age_maxET.getText().toString());
        else
            return 0;

    }


    public String getLookingFor(){

        if(s.getVisibility() == View.INVISIBLE)
            return "";
        else
        return s.getSelectedItem().toString();

    }


    public double getMinHeight(){

        if(!(height_minET.getText().toString().equals("")))
            return Double.parseDouble(height_minET.getText().toString());
        else
            return 0;

    }

    public double getMaxHeight(){

        if(!(height_maxET.getText().toString().equals("")))
            return Double.parseDouble(height_maxET.getText().toString());
        else
            return 0;

    }

    public int getMinWeight(){

        if(!(weight_minET.getText().toString().equals("")))
            return Integer.parseInt(weight_minET.getText().toString());
        else
            return 0;

    }

    public int getMaxWeight(){

        if(!(weight_maxET.getText().toString().equals("")))
            return Integer.parseInt(weight_maxET.getText().toString());
        else
            return 0;

    }


    public String getBodyType(){

        if(s2.getVisibility() == View.INVISIBLE)
            return "";
        else
            return s2.getSelectedItem().toString();

    }

    public String getOrigin(){

        if(s3.getVisibility() == View.INVISIBLE)
            return "";
        else
            return s3.getSelectedItem().toString();

    }


    public String getRelationshipStatus(){

        if(s4.getVisibility() == View.INVISIBLE)
            return "";
        else
            return s4.getSelectedItem().toString();

    }


    public void Filter(View view) {


        // check for age filter request
        if (age_minET.getVisibility() == View.VISIBLE)
        {
            if (!(getMinAge() == 0) && !(getMaxAge() == 0) && getMaxAge() >= getMinAge()) {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    int age = Integer.parseInt(MainPageActivity.ud.get(i).getAge());
                    if (!(age >= getMinAge() && age <= getMaxAge())) {
                        MainPageActivity.list.remove(i - j);
                        j++;
                    }
                }
            }
    }


        // check for lookingFor filter request
        if(s.getVisibility() == View.VISIBLE)
        {
            if(!(getLookingFor().equals("All")))
            {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    String looking_for = MainPageActivity.ud.get(i).getLooking_for();
                    if (!(looking_for.equals(getLookingFor()))) {
                        MainPageActivity.list.remove(i - j);
                        Toast.makeText(getApplicationContext(), looking_for + "", Toast.LENGTH_SHORT).show();

                        j++;
                    }

                }
            }
        }

        // check for Height filter request
        if (height_minET.getVisibility() == View.VISIBLE)
        {
            if (!(getMinHeight() == 0) && !(getMaxHeight() == 0) && getMaxHeight() >= getMinHeight()) {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    double height = Double.parseDouble(MainPageActivity.ud.get(i).getHeight());
                    if (!(height >= getMinHeight() && height <= getMaxHeight())) {
                        MainPageActivity.list.remove(i - j);
                        j++;
                    }
                }
            }
        }

        // check for Weight filter request
        if (weight_minET.getVisibility() == View.VISIBLE)
        {
            if (!(getMinWeight() == 0) && !(getMaxWeight() == 0) && getMaxWeight() >= getMinWeight()) {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    int weight = Integer.parseInt(MainPageActivity.ud.get(i).getWeight());
                    if (!(weight >= getMinWeight() && weight <= getMaxWeight())) {
                        MainPageActivity.list.remove(i - j);
                        j++;
                    }
                }
            }
        }


        // check for body type filter request
        if(s2.getVisibility() == View.VISIBLE)
        {
            if(!(getBodyType().equals("All")))
            {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    String body_type = MainPageActivity.ud.get(i).getBody_type();
                    if (!(body_type.equals(getBodyType()))) {
                        MainPageActivity.list.remove(i - j);
                        Toast.makeText(getApplicationContext(), body_type + "", Toast.LENGTH_SHORT).show();

                        j++;
                    }

                }
            }
        }


        // check for origin filter request
        if(s3.getVisibility() == View.VISIBLE)
        {
            if(!(getOrigin().equals("All")))
            {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    String origin = MainPageActivity.ud.get(i).getNation();
                    if (!(origin.equals(getOrigin()))) {
                        MainPageActivity.list.remove(i - j);
                        Toast.makeText(getApplicationContext(), origin + "", Toast.LENGTH_SHORT).show();

                        j++;
                    }

                }
            }
        }

        // check for relationship status filter request
        if(s4.getVisibility() == View.VISIBLE)
        {
            if(!(getRelationshipStatus().equals("All")))
            {
                int j = 0;
                for (int i = 0; i < MainPageActivity.ud.size(); i++) {
                    String relationship_status = MainPageActivity.ud.get(i).getRelationship_status();
                    if (!(relationship_status.equals(getRelationshipStatus()))) {
                        MainPageActivity.list.remove(i - j);
                        Toast.makeText(getApplicationContext(), relationship_status + "", Toast.LENGTH_SHORT).show();

                        j++;
                    }

                }
            }
        }

        MainPageActivity.ref();

    }

}
