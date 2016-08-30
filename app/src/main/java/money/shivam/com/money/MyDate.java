package money.shivam.com.money;

import java.util.Date;

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
        yyyy=cdt.toString().substring(24,28);
        return yyyy;
    }

    public String getMonth() {
        mm=cdt.toString().substring(4,7);
        return mm;
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


    public boolean isOfCurrentWeek(Date d){
        MyDate dd= new MyDate(d);
        MyDate cd= new MyDate(new Date());
        boolean what=false;

        String cbar= cd.getBar();
        switch (cbar){
            case "Mon":
                break;
            case "Tue":
                break;
            case "Wed":
                break;
            case "Thu":
                break;
            case "Fri":
                break;
            case "Sat":
                break;
            case "Sun":
                break;
        }


        return true;
    }


}
