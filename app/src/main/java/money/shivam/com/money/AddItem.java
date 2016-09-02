package money.shivam.com.money;


import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
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

public class AddItem {

    Context context=null;
    Activity activity;


    public AddItem(Activity ac, Context newContext){

        context= newContext;
        this.activity= ac;
        createDb();
    }

    public void add(){
        final AlertDialog.Builder builder= new AlertDialog.Builder(context);

        LayoutInflater inflater= activity.getLayoutInflater();

        View layout= inflater.inflate(R.layout.add_new_item, null);
        builder.setView(layout);

        final AlertDialog ad= builder.create();
        ad.show();


        final EditText inameEt= (EditText) layout.findViewById(R.id.itemName);
        final Spinner icatSpi= (Spinner) layout.findViewById(R.id.itemCat);
        final EditText priceEt= (EditText) layout.findViewById(R.id.price);
        final EditText maxPriceEt= (EditText) layout.findViewById(R.id.maxPrice);
        final TextView error= (TextView) layout.findViewById(R.id.error);
        final CheckBox cb= (CheckBox) layout.findViewById(R.id.necessity);
        Button save=(Button) layout.findViewById(R.id.saveItem);
        Button cancle=(Button) layout.findViewById(R.id.cancle);


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });




        final String cat[]= new String[1];
        icatSpi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String catag= adapterView.getItemAtPosition(i).toString();
                cat[0]=catag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            String name,catagary;
            double price, maxPrice;
            boolean isNece;
            @Override
            public void onClick(View view) {
                isNece= cb.isChecked();
                name=inameEt.getText().toString();
                if(name.equals("")){
                    error.setText("Please enter name");
                    return;
                }
                if(cat[0].equals("Select Catagery")){
                    error.setText("Please select Catagary");
                    return;
                }
                catagary= cat[0];
                try{
                    price= Double.parseDouble(priceEt.getText().toString());
                }catch (Exception e){error.setText("Invalid price"); return;}

                if(!maxPriceEt.getText().toString().equals("")){
                    try{
                        maxPrice= Double.parseDouble(maxPriceEt.getText().toString());
                    }catch (Exception e){error.setText("Invalid Max Price"); return;}
                }
                //All data is correct. Now save it in DB
                error.setText("");

                String qr="INSERT INTO things VALUES('"+name+"','"+catagary+"',"+(isNece?1:0)+"," +
                        ""+price+"," +
                        ""+maxPrice+",null,null)";
                if(db==null){
                    Toast.makeText(context, "db is null", Toast.LENGTH_SHORT).show();
                }
                try{
                    db.execSQL(qr);
                    ad.dismiss();
                    displayCustom("Item Saved");
                }catch (Exception e){displayCustom("Errors "+e);}



            }//onClickEND
        });
    }







    SQLiteDatabase db;
    public void createDb(){
        db= context.openOrCreateDatabase("moneyDb",SQLiteDatabase.CREATE_IF_NECESSARY,null);

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


    public void displayCustom(String msg){
        LayoutInflater inflater= activity.getLayoutInflater();
        View layout= inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id
                .customToast));
        TextView tv= (TextView) layout.findViewById(R.id.customText);
        tv.setText(msg);
        Toast t=new Toast(context);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setView(layout);
        //t.setGravity(Gravity.CENTER_VERTICAL,0,0);
        t.show();
    }

}
