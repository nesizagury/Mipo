package com.example.mipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends Activity {

    private String array_spinner_Looking_for[];
    private String array_spinner_Body_type[];
    private String array_spinner_Origine[];
    private String array_spinner_Status[];
    static private String optionSelectedLookingFor = "";
    static private String optionSelectedBodyType = "";
    static private String optionSelectedOrigine = "";
    static private String optionSelectedStatus = "";

    EditText age_minET;
    EditText age_maxET;
    TextView ageTo_TV;
    static String minAge = "-1";
    static String maxAge = "9999";


    TextView height_minTV;
    TextView height_maxTV;
    EditText height_minET;
    EditText height_maxET;
    static String minHeight = "-1";
    static String maxHeight = "9999";

    TextView weight_minTV;
    TextView weight_maxTV;
    EditText weight_minET;
    EditText weight_maxET;
    static String minWeight = "-1";
    static String maxWeight = "9999";

    Spinner spinner_Looking_for;
    Spinner spinner_Body_type;
    Spinner spinner_Origine;
    Spinner spinner_Status;

    boolean age_flag = false;
    boolean looking_flag = false;
    boolean height_flag = false;
    boolean weight_flag = false;
    boolean body_flag = false;
    boolean origin_flag = false;
    boolean relationship_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_filter);

        ageTo_TV = (TextView) findViewById (R.id.ageTo_TV);
        age_minET = (EditText) findViewById (R.id.age_minET);
        age_maxET = (EditText) findViewById (R.id.age_maxET);

        height_minTV = (TextView) findViewById (R.id.height_minTV);
        height_maxTV = (TextView) findViewById (R.id.height_maxTV);
        height_minET = (EditText) findViewById (R.id.height_minET);
        height_maxET = (EditText) findViewById (R.id.height_maxET);

        weight_minTV = (TextView) findViewById (R.id.weight_minTV);
        weight_maxTV = (TextView) findViewById (R.id.weight_maxTV);
        weight_minET = (EditText) findViewById (R.id.weight_minET);
        weight_maxET = (EditText) findViewById (R.id.weight_maxET);


        setSpinners ();

        if (!minAge.equals ("-1") || !maxAge.equals ("9999")) {
            ageTo_TV.setVisibility (View.VISIBLE);
            age_minET.setVisibility (View.VISIBLE);
            age_maxET.setVisibility (View.VISIBLE);
            age_flag = true;
            if (!minAge.equals ("-1")) {
                age_minET.setText (minAge);
            }
            if (!maxAge.equals ("9999")) {
                age_maxET.setText (maxAge);
            }
        }

        if (!minHeight.equals ("-1") || !maxHeight.equals ("9999")) {
            height_minTV.setVisibility (View.VISIBLE);
            height_maxTV.setVisibility (View.VISIBLE);
            height_maxET.setVisibility (View.VISIBLE);
            height_minET.setVisibility (View.VISIBLE);
            height_flag = true;
            if (!minHeight.equals ("-1")) {
                height_minET.setText (minHeight);
            }
            if (!maxHeight.equals ("9999")) {
                height_maxET.setText (maxHeight);
            }
        }

        if (!minWeight.equals ("-1") || !maxWeight.equals ("9999")) {
            weight_minTV.setVisibility (View.VISIBLE);
            weight_maxTV.setVisibility (View.VISIBLE);
            weight_maxET.setVisibility (View.VISIBLE);
            weight_minET.setVisibility (View.VISIBLE);
            weight_flag = true;
            if (!minWeight.equals ("-1")) {
                weight_minET.setText (minWeight);
            }
            if (!maxWeight.equals ("9999")) {
                weight_maxET.setText (maxWeight);
            }
        }

        if (!optionSelectedLookingFor.equals ("")) {
            looking_flag = true;
            for (int i = 0; i < array_spinner_Looking_for.length; i++) {
                if (optionSelectedLookingFor.equals (array_spinner_Looking_for[i])) {
                    spinner_Looking_for.setSelection (i);
                    spinner_Looking_for.setVisibility (View.VISIBLE);
                    break;
                }
            }
        }

        if (!optionSelectedBodyType.equals ("")) {
            body_flag = true;
            for (int i = 0; i < array_spinner_Body_type.length; i++) {
                if (optionSelectedBodyType.equals (array_spinner_Body_type[i])) {
                    spinner_Body_type.setSelection (i);
                    spinner_Body_type.setVisibility (View.VISIBLE);
                    break;
                }
            }
        }

        if (!optionSelectedOrigine.equals ("")) {
            origin_flag = true;
            for (int i = 0; i < array_spinner_Origine.length; i++) {
                if (optionSelectedOrigine.equals (array_spinner_Origine[i])) {
                    spinner_Origine.setSelection (i);
                    spinner_Origine.setVisibility (View.VISIBLE);
                    break;
                }
            }
        }

        if (!optionSelectedStatus.equals ("")) {
            relationship_flag = true;
            for (int i = 0; i < array_spinner_Status.length; i++) {
                if (optionSelectedStatus.equals (array_spinner_Status[i])) {
                    spinner_Status.setSelection (i);
                    spinner_Status.setVisibility (View.VISIBLE);
                    break;
                }
            }
        }
    }

    private void setSpinners() {

        array_spinner_Looking_for = new String[4];
        array_spinner_Looking_for[0] = "All";
        array_spinner_Looking_for[1] = "Dates";
        array_spinner_Looking_for[2] = "Friends";
        array_spinner_Looking_for[3] = "Chat";
        spinner_Looking_for = (Spinner) findViewById (R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter (this,
                                                        android.R.layout.simple_spinner_item, array_spinner_Looking_for);
        spinner_Looking_for.setAdapter (adapter);

        array_spinner_Body_type = new String[4];
        array_spinner_Body_type[0] = "All";
        array_spinner_Body_type[1] = "Slim";
        array_spinner_Body_type[2] = "Toned";
        array_spinner_Body_type[3] = "Stocky";
        spinner_Body_type = (Spinner) findViewById (R.id.spinner2);
        ArrayAdapter adapter2 = new ArrayAdapter (this,
                                                         android.R.layout.simple_spinner_item, array_spinner_Body_type);
        spinner_Body_type.setAdapter (adapter2);

        array_spinner_Origine = new String[4];
        array_spinner_Origine[0] = "All";
        array_spinner_Origine[1] = "Middle Eastern";
        array_spinner_Origine[2] = "Native American";
        array_spinner_Origine[3] = "South Asian";
        spinner_Origine = (Spinner) findViewById (R.id.spinner3);
        ArrayAdapter adapter3 = new ArrayAdapter (this,
                                                         android.R.layout.simple_spinner_item, array_spinner_Origine);
        spinner_Origine.setAdapter (adapter3);

        array_spinner_Status = new String[4];
        array_spinner_Status[0] = "All";
        array_spinner_Status[1] = "Single";
        array_spinner_Status[2] = "Divorced";
        array_spinner_Status[3] = "Open Relationship";
        spinner_Status = (Spinner) findViewById (R.id.spinner4);
        ArrayAdapter adapter4 = new ArrayAdapter (this,
                                                         android.R.layout.simple_spinner_item, array_spinner_Status);
        spinner_Status.setAdapter (adapter4);

    }


    public void showAgeEditors(View v) {

        if (!age_flag) {
            ageTo_TV.setVisibility (View.VISIBLE);
            age_minET.setVisibility (View.VISIBLE);
            age_maxET.setVisibility (View.VISIBLE);
            age_flag = true;
        } else {
            ageTo_TV.setVisibility (View.INVISIBLE);
            age_minET.setVisibility (View.INVISIBLE);
            age_maxET.setVisibility (View.INVISIBLE);
            age_flag = false;
            minAge = "-1";
            maxAge = "9999";
        }

    }

    public void showHeightEditors(View v) {

        if (!height_flag) {
            height_minTV.setVisibility (View.VISIBLE);
            height_maxTV.setVisibility (View.VISIBLE);
            height_minET.setVisibility (View.VISIBLE);
            height_maxET.setVisibility (View.VISIBLE);
            height_flag = true;
        } else {
            height_minTV.setVisibility (View.INVISIBLE);
            height_maxTV.setVisibility (View.INVISIBLE);
            height_minET.setVisibility (View.INVISIBLE);
            height_maxET.setVisibility (View.INVISIBLE);
            height_flag = false;
            minHeight = "-1";
            maxHeight = "9999";
        }

    }

    public void showWeightEditors(View v) {

        if (!weight_flag) {
            weight_minTV.setVisibility (View.VISIBLE);
            weight_maxTV.setVisibility (View.VISIBLE);
            weight_minET.setVisibility (View.VISIBLE);
            weight_maxET.setVisibility (View.VISIBLE);
            weight_flag = true;
        } else {
            weight_minTV.setVisibility (View.INVISIBLE);
            weight_maxTV.setVisibility (View.INVISIBLE);
            weight_minET.setVisibility (View.INVISIBLE);
            weight_maxET.setVisibility (View.INVISIBLE);
            weight_flag = false;
            minWeight = "-1";
            maxWeight = "9999";
        }

    }

    public void showLookingForEditors(View v) {
        if (!looking_flag) {
            spinner_Looking_for.setVisibility (View.VISIBLE);
            looking_flag = true;
        } else {
            spinner_Looking_for.setVisibility (View.INVISIBLE);
            optionSelectedLookingFor = "";
            looking_flag = false;
        }
    }

    public void showBodyTypeEditors(View v) {
        if (!body_flag) {
            spinner_Body_type.setVisibility (View.VISIBLE);
            body_flag = true;
        } else {
            spinner_Body_type.setVisibility (View.INVISIBLE);
            optionSelectedBodyType = "";
            body_flag = false;
        }
    }


    public void showOriginEditors(View v) {
        if (!origin_flag) {
            spinner_Origine.setVisibility (View.VISIBLE);
            origin_flag = true;
        } else {
            optionSelectedOrigine = "";
            spinner_Origine.setVisibility (View.INVISIBLE);
            origin_flag = false;
        }
    }

    public void showRelationshipStatusEditors(View v) {
        if (!relationship_flag) {
            spinner_Status.setVisibility (View.VISIBLE);
            relationship_flag = true;
        } else {
            optionSelectedStatus = "";
            spinner_Status.setVisibility (View.INVISIBLE);
            relationship_flag = false;
        }
    }

    public int getMinAge() {
        if (!(age_minET.getText ().toString ().equals (""))) {
            minAge = age_minET.getText ().toString ();
            return Integer.parseInt (age_minET.getText ().toString ());
        } else
            return 0;
    }

    public int getMaxAge() {
        if (!(age_maxET.getText ().toString ().equals (""))) {
            maxAge = age_maxET.getText ().toString ();
            return Integer.parseInt (age_maxET.getText ().toString ());
        } else
            return 9999;
    }

    public double getMinHeight() {
        if (!(height_minET.getText ().toString ().equals (""))) {
            minHeight = height_minET.getText ().toString ();
            return Double.parseDouble (height_minET.getText ().toString ());
        } else
            return 0;
    }

    public double getMaxHeight() {
        if (!(height_maxET.getText ().toString ().equals (""))) {
            maxHeight = height_maxET.getText ().toString ();
            return Double.parseDouble (height_maxET.getText ().toString ());
        } else
            return 0;
    }

    public int getMinWeight() {
        if (!(weight_minET.getText ().toString ().equals (""))) {
            minWeight = weight_minET.getText ().toString ();
            return Integer.parseInt (weight_minET.getText ().toString ());
        } else
            return 0;
    }

    public int getMaxWeight() {
        if (!(weight_maxET.getText ().toString ().equals (""))) {
            maxWeight = weight_maxET.getText ().toString ();
            return Integer.parseInt (weight_maxET.getText ().toString ());
        } else
            return 0;
    }

    public String getLookingFor() {
        if (spinner_Looking_for.getVisibility () == View.INVISIBLE)
            return "";
        else {
            optionSelectedLookingFor = spinner_Looking_for.getSelectedItem ().toString ();
            return optionSelectedLookingFor;
        }
    }

    public String getBodyType() {
        if (spinner_Body_type.getVisibility () == View.INVISIBLE)
            return "";
        else {
            optionSelectedBodyType = spinner_Body_type.getSelectedItem ().toString ();
            return optionSelectedBodyType;
        }
    }

    public String getOrigin() {
        if (spinner_Origine.getVisibility () == View.INVISIBLE)
            return "";
        else {
            optionSelectedOrigine = spinner_Origine.getSelectedItem ().toString ();
            return optionSelectedOrigine;
        }
    }


    public String getRelationshipStatus() {
        if (spinner_Status.getVisibility () == View.INVISIBLE)
            return "";
        else {
            optionSelectedStatus = spinner_Status.getSelectedItem ().toString ();
            return optionSelectedStatus;
        }
    }

    public void Filter(View view) {
        for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
            UserDetails user = GlobalVariables.userDataList.get (i);
            user.setIsFilteredOK (true);
        }
        // check for age filter request
        if (age_minET.getVisibility () == View.VISIBLE) {
            int minAge = getMinAge ();
            int maxAge = getMaxAge ();
            if (minAge >= 0 && maxAge >= 0 && maxAge >= minAge) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    try {
                        int age = Integer.parseInt (user.getAge ());
                        if (!(age >= minAge && age <= maxAge)) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            } else {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    user.setIsFilteredOK (false);
                }
            }
        }

        // check for Height filter request
        if (height_minET.getVisibility () == View.VISIBLE) {
            double minHight = getMinHeight ();
            double maxHight = getMaxHeight ();
            if (minHight >= 0 && maxHight >= 0 && maxHight >= minHight) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    try {
                        double height = Double.parseDouble (user.getHeight ());
                        if (!(height >= getMinHeight () && height <= getMaxHeight ())) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            } else {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    user.setIsFilteredOK (false);
                }
            }
        }

        // check for Weight filter request
        if (weight_minET.getVisibility () == View.VISIBLE) {
            int minWeight = getMinWeight ();
            int maxWeight = getMaxWeight ();
            if (minWeight >= 0 && maxWeight >= 0 && maxWeight >= minWeight) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    try {
                        int weight = Integer.parseInt (user.getWeight ());
                        if (!(weight >= getMinWeight () && weight <= getMaxWeight ())) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            } else {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    user.setIsFilteredOK (false);
                }
            }
        }

        // check for lookingFor filter request
        if (spinner_Looking_for.getVisibility () == View.VISIBLE) {
            String lookingFor = getLookingFor ();
            if (!(lookingFor.equals ("All"))) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    try {
                        String looking_for_user = user.getLooking_for ();
                        if (!(looking_for_user.equals (lookingFor))) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            }
        }

        // check for body type filter request
        if (spinner_Body_type.getVisibility () == View.VISIBLE) {
            String bodyType = getBodyType ();
            if (!(bodyType.equals ("All"))) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    String body_type_user = user.getBody_type ();
                    try {
                        if (!(body_type_user.equals (bodyType))) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            }
        }

        // check for origin filter request
        if (spinner_Origine.getVisibility () == View.VISIBLE) {
            String origine = getOrigin ();
            if (!(origine.equals ("All"))) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    String origine_user = user.getNation ();
                    try {
                        if (!(origine_user.equals (origine))) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            }
        }

        // check for relationship status filter request
        if (spinner_Status.getVisibility () == View.VISIBLE) {
            String status = getRelationshipStatus ();
            if (!(status.equals ("All"))) {
                for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
                    UserDetails user = GlobalVariables.userDataList.get (i);
                    String relationship_status_user = user.getRelationship_status ();
                    try {
                        if (!(relationship_status_user.equals (status))) {
                            user.setIsFilteredOK (false);
                        }
                    } catch (Exception e) {
                        user.setIsFilteredOK (false);
                    }
                }
            }
        }

        List<UserDetails> filteredUsersListNew = new ArrayList<UserDetails> ();
        filteredUsersListNew.add (GlobalVariables.currentUser);

        for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
            UserDetails user = GlobalVariables.userDataList.get (i);
            if (!user.equals (GlobalVariables.currentUser) && user.isFilteredOK ()) {
                filteredUsersListNew.add (user);
            }
        }
        MainPageActivity.filteredUsersList.clear ();
        MainPageActivity.filteredUsersList.addAll (filteredUsersListNew);

        Intent intent = new Intent (this, MainPageActivity.class);
        startActivity (intent);
    }

}
