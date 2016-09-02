package money.shivam.com.money;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;

public class AddExpence extends AppCompatActivity {

    SQLiteDatabase db;
    SharedPreferences sp;
    LinearLayout layout;
    Resources rs;
    int screenH,screenW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expence);

        createDb();

        screenH= Resources.getSystem().getDisplayMetrics().heightPixels;
        screenW= Resources.getSystem().getDisplayMetrics().widthPixels;

        layout= (LinearLayout) findViewById(R.id.llayout);
        rs= this.getResources();


        dbToDisplay();



    }//onCreateEND


    public void dbToDisplay(){


        //Adding Items
        String qr="SELECT * FROM things";
        Cursor cur= db.rawQuery(qr,null);
        while (cur.moveToNext()){
            String name=cur.getString(0);
            String cat=cur.getString(1);
            double price=cur.getDouble(3);
            addItemView(name,price,cat);
        }

        //Adding space at bottom
        TextView space= new TextView(this);
        space.setHeight(50);
        layout.addView(space);

    }





    public void addItemView(String nm, double pric, String cat){
        int paddingLR=6;
        int paddingTB=12;
        int textS=15;

        LinearLayout.LayoutParams paramsName= new LinearLayout.LayoutParams(LinearLayoutCompat
                .LayoutParams
                .MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT,0.5f);
        LinearLayout.LayoutParams paramsElse= new LinearLayout.LayoutParams(LinearLayoutCompat
                .LayoutParams
                .MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT,0.8f);
        LinearLayout.LayoutParams paramMin= new LinearLayout.LayoutParams(LinearLayoutCompat
                .LayoutParams
                .MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT,0.96f);


        LinearLayout newLayout=new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        //newLayout.setBackground(rs.getDrawable(R.drawable.layout_bg));
        newLayout.setBackgroundColor(Color.rgb(74,20,109));
        newLayout.setPadding(paddingLR,paddingTB-3,paddingLR,paddingTB-3);

//        ImageView imageView= new ImageView(this);
//        Drawable d= rs.getDrawable(R.drawable.trash);
//        imageView.setBackground(d);
//        newLayout.addView(imageView);

        TextView name= new TextView(this);
        name.setBackgroundColor(Color.CYAN);
        name.setTextColor(Color.BLACK);
        name.setLayoutParams(paramsName);
        name.setPadding(5,paddingTB,0,paddingTB);
        name.setTextSize(textS);
        name.setText(nm);
        newLayout.addView(name);


        final TextView rsSy= new TextView(this);
        rsSy.setText("\u20B9");
        rsSy.setTextSize(textS);
        rsSy.setLayoutParams(paramMin);
        rsSy.setBackgroundColor(Color.GREEN);
        rsSy.setPadding(5,paddingTB,0,paddingTB);
        newLayout.addView(rsSy);


        final EditText price= new EditText(this);
        price.setText(""+pric);
        price.setTextSize(textS);
        price.setLayoutParams(paramsElse);
        price.setBackgroundColor(Color.GREEN);
        price.setPadding(5,paddingTB,0,paddingTB);
        newLayout.addView(price);



        Button butn= new Button(this);
        butn.setText("ADD");
        butn.setTextSize(textS);
        butn.setLayoutParams(paramsElse);
        butn.setHeight(price.getHeight());
        butn.setBackgroundColor(Color.YELLOW);
        butn.setPadding(5,paddingTB,0,paddingTB);
        butn.setTextColor(Color.BLACK);
        newLayout.addView(butn);


        layout.addView(newLayout);

        //Event Handling
        final String fname= name.getText().toString();
        final String fcat= cat;



        butn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    double fprice=  Double.parseDouble(price.getText().toString());
                    price.setBackgroundColor(Color.GREEN);
                    rsSy.setBackgroundColor(Color.GREEN);
                    addInExpence(fname,fprice,fcat);
                }catch (Exception e){
                    price.setBackgroundColor(Color.RED);
                    rsSy.setBackgroundColor(Color.RED);
                    display("Incorrect price");
                }

            }
        });

    }




    public void addNewItem( View v){
        AddItem adi= new AddItem(this, this);
        adi.add();
    }




    private void addInExpence(String name, double price, String cat){


        String qr="INSERT INTO expence (name,cat,price,note) VALUES ('"+name+"','"+cat+"'," +
                ""+price+",null)";
        try{
            db.execSQL(qr);
            displayCustom(name+" Added");
        }catch (Exception e){display("addInExpence "+e+"");}

    }






    public void createDb(){
        db= this.openOrCreateDatabase("moneyDb",SQLiteDatabase.CREATE_IF_NECESSARY,null);

        String qr="CREATE  TABLE  IF NOT EXISTS \"expence\" (\"dat\" DATETIME PRIMARY KEY  NOT NULL , \"name\" VARCHAR NOT NULL , \"cat\" VARCHAR, \"price\" DOUBLE NOT NULL , \"note\" VARCHAR)";
        db.execSQL(qr);

        qr="CREATE  TABLE  IF NOT EXISTS \"things\" (\"name\" VARCHAR NOT NULL , \"necessity\" BOOL, \"price\" DOUBLE NOT NULL , \"maxPrice\" DOUBLE, \"note\" VARCHAR, \"warn\" VARCHAR)";
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
