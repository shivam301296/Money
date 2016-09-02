package money.shivam.com.money;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.renderscript.Double2;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    String name,gender,profrssion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst= sp.getBoolean("isFirstTime",true);


        if(isFirst){
            display("First Time");
            Intent i= new Intent(this, FirstTime.class);
            this.startActivity(i);
        }
        else{
            name= sp.getString("name","User");
            gender= sp.getString("gender","Male");
            profrssion= sp.getString("profession","Regular person");

            String fnm=name;
            try{fnm=name.substring(0,name.indexOf(" "));}catch(Exception e){}
            displayCustom("Welcome "+fnm);
        }






        createDb();

    }//onCreateEND



    public void addItem(View v){

        AddItem adi= new AddItem(this, this);
        adi.add();

    }



    public void viewReport( View v){
        Intent i= new Intent(this, ExpenceReport.class);
        this.startActivity(i);
    }




    public void addExpence(View v){
        Intent i= new Intent(this, AddExpence.class);
        this.startActivity(i);
    }

    public void setting( View v){
        //Intent i= new Intent(this, Setting.class);
        //this.startActivity(i);
    }



























    SQLiteDatabase db;
    public void createDb(){
        db= this.openOrCreateDatabase("moneyDb",SQLiteDatabase.CREATE_IF_NECESSARY,null);

        String qr="CREATE  TABLE  IF NOT EXISTS \"expence\" (\"dat\" DATETIME PRIMARY KEY  NOT " +
                "NULL DEFAULT CURRENT_TIMESTAMP , \"name\" VARCHAR NOT NULL , \"cat\" VARCHAR, " +
                "\"price\" DOUBLE NOT NULL " +
                ", \"note\" VARCHAR)";
        db.execSQL(qr);

        qr="CREATE  TABLE  IF NOT EXISTS \"things\" (\"name\" VARCHAR NOT NULL , \"cat\" VARCHAR" +
                " NOT NULL , \"necessity\" " +
                "BOOL, \"price\" DOUBLE NOT NULL , \"maxPrice\" DOUBLE, \"note\" VARCHAR, \"warn\" VARCHAR)";
        db.execSQL(qr);
    }








    public void display(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void displayCustom(String msg){
        LayoutInflater inflater= this.getLayoutInflater();
        View layout= inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id
                .customToast));
        TextView tv= (TextView) layout.findViewById(R.id.customText);
        tv.setText(msg);
        Toast t=new Toast(this);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setView(layout);
        //t.setGravity(Gravity.CENTER_VERTICAL,0,0);
        t.show();
    }
}//classEND

