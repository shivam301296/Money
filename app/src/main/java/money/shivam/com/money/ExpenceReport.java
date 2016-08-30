package money.shivam.com.money;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class ExpenceReport extends AppCompatActivity {

    Spinner rtSpi,stSpi;
    LinearLayout playout;

    String reportType;
    String sortType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_report);

        createDb();

        rtSpi= (Spinner) findViewById(R.id.reportType);
        stSpi= (Spinner) findViewById(R.id.sortType);
        playout= (LinearLayout) findViewById(R.id.playout);



        rtSpi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type= adapterView.getItemAtPosition(i).toString();
                setReportType(type);
                display(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        stSpi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type= adapterView.getItemAtPosition(i).toString();
                setSortType(type);
                display(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }//onCreateEND







    private void showReport(){

        String tqr="SELECT SUM(price) FROM expence";
        Cursor tcur=db.rawQuery(tqr,null);
        double total=0.0;
        if(tcur.moveToNext()) {
            total= tcur.getDouble(0);
            displayCustom(total + "");
        }



        int paddingTB=10,paddingRL=20;
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayoutCompat
                .LayoutParams
                .MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT,1.0f);

        MyDate cd= new MyDate(new java.util.Date());


        ((ViewGroup)playout).removeAllViews();

        String qr= "SELECT * FROM expence";
        Cursor cur= db.rawQuery(qr, null);

        LinearLayout tv[]=new LinearLayout[cur.getCount()];
        int k=0;

        while (cur.moveToNext()){

            Timestamp ts= Timestamp.valueOf(cur.getString(0));
            MyDate dt=new MyDate(new Date(ts.getTime()));

            //if(cd.getDay().equals(dt.getDay())&&cd.getMonth().equals(dt.getMonth())&&cd.getYear()
             //       .equals(dt.getYear())) {
            if(true){
                try {
                    tv[k]= new LinearLayout(this);
                    tv[k].setOrientation(LinearLayout.HORIZONTAL);


                    TextView datetv= new TextView(this);
                    datetv.setText(dt.getDay()+"/"+dt.getMonth()+"/"+dt.getYear()+" "+dt.getHours
                            ()+":"+dt.getMinutes());
                    datetv.setPadding(paddingRL,paddingTB,paddingRL,paddingTB);
                    datetv.setBackgroundColor(Color.rgb(17,171,214));
                    tv[k].addView(datetv);

                    TextView nametv= new TextView(this);
                    nametv.setText(cur.getString(1));
                    nametv.setPadding(paddingRL,paddingTB,paddingRL,paddingTB);
                    nametv.setBackgroundColor(Color.rgb(121,232,92));
                    nametv.setLayoutParams(params);
                    tv[k].addView(nametv);

                    TextView pricetv= new TextView(this);
                    pricetv.setText("\u20B9"+cur.getDouble(3));
                    pricetv.setPadding(paddingRL,paddingTB,paddingRL,paddingTB);
                    pricetv.setBackgroundColor(Color.rgb(236,164,230));
                    pricetv.setWidth(110);
                    //nametv.setLayoutParams(params);
                    tv[k].addView(pricetv);






                    playout.addView(tv[k]);
                    k++;
                } catch (Exception e) {
                    display(e + "");
                }
            }
            else{

            }

        }
        LinearLayout tl=new LinearLayout(this);
        tl.setOrientation(LinearLayout.HORIZONTAL);
        TextView aa= new TextView(this);
        aa.setText("Total");
        aa.setPadding(paddingRL,paddingTB+10,paddingRL,paddingTB);
        aa.setBackgroundColor(Color.rgb(19,50,97));
        aa.setTextColor(Color.YELLOW);
        tl.addView(aa);

        TextView bb= new TextView(this);
        bb.setText("");
        bb.setPadding(paddingRL,paddingTB+10,paddingRL,paddingTB);
        bb.setBackgroundColor(Color.rgb(19,50,97));
        bb.setTextColor(Color.YELLOW);
        bb.setLayoutParams(params);
        tl.addView(bb);

        TextView cc= new TextView(this);
        cc.setText("\u20B9"+total);
        cc.setPadding(paddingRL,paddingTB+10,paddingRL,paddingTB);
        cc.setBackgroundColor(Color.rgb(19,50,97));
        cc.setTextColor(Color.YELLOW);
        cc.setWidth(100);
        tl.addView(cc);






        playout.addView(tl);

    }




    {
        TableLayout tl= new TableLayout(this);
    }













    SQLiteDatabase db;
    public void createDb(){
        db= this.openOrCreateDatabase("moneyDb",SQLiteDatabase.CREATE_IF_NECESSARY,null);
    }




    private void setReportType(String s){
        reportType=s;
        showReport();
    }

    private String getReportType(){
        return reportType;
    }
    private void setSortType(String s){
        sortType=s;
    }

    private String getSortType(){
        return sortType;
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
