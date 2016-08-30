package money.shivam.com.money;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTime extends AppCompatActivity {


    String []profession_male={"Select your profession","Student","Regular person","Buisnessman",
            "Shopkeeper"};
    String []profession_female={"Select your profession","Student","Housewife","Buisnesswoman"};
    int height,width;
    String gendr=null;
    String prof=null;


    Spinner profession;
    RadioGroup gender;
    RadioButton male,female;
    ImageView imageView;
    SharedPreferences sp;
    EditText et;
    TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        sp= PreferenceManager.getDefaultSharedPreferences(this);


        height= Resources.getSystem().getDisplayMetrics().heightPixels;
        width= Resources.getSystem().getDisplayMetrics().widthPixels;



        profession= (Spinner) findViewById(R.id.profession);
        gender= (RadioGroup) findViewById(R.id.gender);
        male= (RadioButton) findViewById(R.id.male);
        female= (RadioButton) findViewById(R.id.female);
        imageView= (ImageView) findViewById(R.id.money);
        et= (EditText) findViewById(R.id.name);
        error= (TextView) findViewById(R.id.error);

        imageView.getLayoutParams().height=height/4;
        imageView.getLayoutParams().width=height/3;




        male.setOnCheckedChangeListener(professionListener);
        female.setOnCheckedChangeListener(professionListener);

        profession.setOnItemSelectedListener(profListener);



    }//onCreateEND






    public void start(View v){
        if(et.getText().toString().length()==0){
            error.setText("Please Enter your name");
        }
        else if(gendr==null){
            error.setText("Select gender");
        }
        else if(prof.equals("Select your profession")){
            error.setText("Select your profession");
        }
        else {
            SharedPreferences.Editor ed= sp.edit();
            ed.putString("name",et.getText().toString());
            ed.putString("gender",gendr);
            ed.putString("profession",prof);
            ed.putBoolean("isFirstTime",false);
            ed.commit();
            display("Data Saved");
            display("Shivam is "+prof);
            error.setText("");
            Intent i= new Intent(this, MainActivity.class);
            this.startActivity(i);
        }

    }




    //For gender RadioButtons
    CompoundButton.OnCheckedChangeListener professionListener= new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            ArrayAdapter<String> adap;
            switch (compoundButton.getId()){
                case R.id.male:
                    if(b) {
                        gendr="male";
                        adap = new ArrayAdapter<String>(FirstTime.this,
                                android.R
                                        .layout
                                        .simple_spinner_item, profession_male);
                        adap.notifyDataSetChanged();
                        profession.setAdapter(adap);
                    }
                    break;
                case R.id.female:
                    if (b) {
                        gendr="female";
                        adap= new ArrayAdapter<String>(FirstTime.this,
                            android.R
                                    .layout
                                    .simple_spinner_item, profession_female);
                        adap.notifyDataSetChanged();
                        profession.setAdapter(adap);
                    }
                    break;
            }
        }
    };


    //For Profession Spinner
    AdapterView.OnItemSelectedListener profListener= new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            prof=adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    public void display(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}//classEND
