package money.shivam.com.money;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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



    private boolean shouldShow(Date dt){

        switch (reportType){
            case "Today":
                return MyDate.isToday(dt);
            case "Yesterday":
                return MyDate.isYesterday(dt);
            case "This Week":
                return MyDate.isOfCurrentWeek(dt);
            case "Last Week":
                return MyDate.isOfLastWeek(dt);
            case "This Month":
                break;
            case "Last Month":
                break;
            default:
                break;
        }
        return true;
    }





    private void showReport(){

        double total=0.0;

        int paddingTB=12,paddingRL=15;
        int fontSize=12;

        //MyDate cd= new MyDate(new java.util.Date());


        ((ViewGroup)playout).removeAllViews();

        String qr= "SELECT * FROM expence";
        Cursor cur= db.rawQuery(qr, null);


        TableLayout tl= new TableLayout(this);


        while (cur.moveToNext()){

            Timestamp ts= Timestamp.valueOf(cur.getString(0));
            Date jd= new Date(ts.getTime());
            MyDate dt=new MyDate(jd);
            if(shouldShow(jd)){
                try {


                    TextView datetv= new TextView(this);
                    datetv.setText(dt.getDay()+"/"+dt.getMonth()+"/"+dt.getYear()+" "+dt.getHours
                            ()+":"+dt.getMinutes());
                    datetv.setPadding(paddingRL,paddingTB,paddingRL,paddingTB);
                    datetv.setBackgroundColor(Color.rgb(17,171,214));
                    datetv.setTextSize(fontSize);

                    TextView nametv= new TextView(this);
                    nametv.setText(cur.getString(1));
                    nametv.setPadding(paddingRL,paddingTB,paddingRL,paddingTB);
                    nametv.setBackgroundColor(Color.rgb(121,232,92));
                    nametv.setTextSize(fontSize);

                    TextView pricetv= new TextView(this);
                    double pri=cur.getDouble(3);
                    total+=pri;
                    pricetv.setText("\u20B9"+pri);
                    pricetv.setPadding(paddingRL,paddingTB,paddingRL,paddingTB);
                    pricetv.setBackgroundColor(Color.rgb(236,164,230));
                    pricetv.setTextSize(fontSize);
                    pricetv.setWidth(110);


                    TableRow tr= new TableRow(this);
                    tr.addView(datetv);
                    tr.addView(nametv);
                    tr.addView(pricetv);
                    tl.addView(tr);






                    //------------------------------------------------

                } catch (Exception e) {
                    display(e + "");
                }
            }
            else{

            }

        }


        TextView aa= new TextView(this);
        aa.setText("Total");
        aa.setPadding(paddingRL,paddingTB+10,paddingRL,paddingTB);
        aa.setBackgroundColor(Color.rgb(19,50,97));
        aa.setTextColor(Color.YELLOW);
        aa.setTextSize(fontSize);

        TextView bb= new TextView(this);
        bb.setText("");
        bb.setPadding(paddingRL,paddingTB+10,paddingRL,paddingTB);
        bb.setBackgroundColor(Color.rgb(19,50,97));
        bb.setTextColor(Color.YELLOW);
        bb.setTextSize(fontSize);

        TextView cc= new TextView(this);
        cc.setText("\u20B9"+total);
        cc.setPadding(paddingRL,paddingTB+10,paddingRL,paddingTB);
        cc.setBackgroundColor(Color.rgb(19,50,97));
        cc.setTextColor(Color.YELLOW);
        cc.setWidth(100);
        cc.setTextSize(fontSize);

        TableRow totRow= new TableRow(this);
        totRow.addView(aa);
        totRow.addView(bb);
        totRow.addView(cc);
        tl.addView(totRow);


        playout.addView(tl);

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
