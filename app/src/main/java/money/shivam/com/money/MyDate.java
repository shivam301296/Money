package money.shivam.com.money;

import android.icu.util.Calendar;

import java.util.Date;
import java.util.InputMismatchException;

/**
 * Created by SHIVAM AGRAWAL on 29-08-2016.
 */
public class MyDate {

    Date cdt;
    String yyyy,mm,dd,bar,HH,MM,SS;

    public MyDate(Date nDt){
        cdt=nDt;
    }





    public String getYear() {
        yyyy=cdt.toString().substring(30,34);
        return yyyy;
    }

    public String getMonth() {
        mm=cdt.toString().substring(4,7);
        switch (mm){
            case "Jan":
                return "01";
            case "Fab":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Auc":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return mm;
        }
    }

    public String getDay() {
        dd=cdt.toString().substring(8,10);
        return dd;
    }

    public String getBar() {
        bar=cdt.toString().substring(0,3);
        return bar;
    }

    public String getHours() {
        HH=cdt.toString().substring(11,13);
        return HH;
    }

    public String getMinutes() {
        MM=cdt.toString().substring(14,16);
        return MM;
    }

    public String getSeconds() {
        SS=cdt.toString().substring(17,19);
        return SS;
    }





    public MyDate getBefore(Date dt){

        Date nd= new Date(dt.getTime()-(1000*60*60*24));
        return new MyDate(nd);

     }



    public static boolean isToday(Date dt){
        MyDate md= new MyDate(dt);
        MyDate cd= new MyDate(new Date());
        if(cd.getDay().equals(md.getDay())&&cd.getMonth().equals(md.getMonth())&&cd.getYear()
                .equals(md.getYear())){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isYesterday(Date dt){
        MyDate md= new MyDate(dt);
        MyDate cd= new MyDate(new Date(new Date().getTime()-(1000*60*60*24)));
        if(cd.getDay().equals(md.getDay())&&cd.getMonth().equals(md.getMonth())&&cd.getYear()
                .equals(md.getYear())){
            return true;
        }else {
            return false;
        }
    }


    public static boolean isOfCurrentWeek(Date dt){
        MyDate md= new MyDate(dt);
        MyDate cdd= new MyDate(new Date());

        String cbar= cdd.getBar();
        int ul=7;
        switch (cbar){
            case "Mon":
                ul=1;
                break;
            case "Tue":
                ul=2;
                break;
            case "Wed":
                ul=3;
                break;
            case "Thu":
                ul=4;
                break;
            case "Fri":
                ul=5;
                break;
            case "Sat":
                ul=6;
                break;
            case "Sun":
                ul=7;
                break;
        }

        for(int i=0; i<ul; i++){
            MyDate cd= new MyDate(new Date(new Date().getTime()-((1000*60*60*24)*i)));
            if(cd.getDay().equals(md.getDay())&&cd.getMonth().equals(md.getMonth())&&cd.getYear()
                    .equals(md.getYear())){
                return true;
            }
        }
        return false;
    }



    public static boolean isOfLastWeek( Date dt){
        MyDate md= new MyDate(dt);
        MyDate cdd= new MyDate(new Date());

        String cbar= cdd.getBar();
        int ul=7;
        switch (cbar){
            case "Mon":
                ul=1;
                break;
            case "Tue":
                ul=2;
                break;
            case "Wed":
                ul=3;
                break;
            case "Thu":
                ul=4;
                break;
            case "Fri":
                ul=5;
                break;
            case "Sat":
                ul=6;
                break;
            case "Sun":
                ul=7;
                break;
        }

        for(int i=0; i<7; i++){
            MyDate cd= new MyDate(new Date(new Date().getTime()-((1000*60*60*24)*(ul+i))));
            if(cd.getDay().equals(md.getDay())&&cd.getMonth().equals(md.getMonth())&&cd.getYear()
                    .equals(md.getYear())){
                return true;
            }
        }
        return false;

    }












}
