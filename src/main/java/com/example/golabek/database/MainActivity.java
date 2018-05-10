package com.example.golabek.database;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    Button ProdAdd ;
    Button ProdDel ;
    Button AllClear;
    EditText ProdName;
    EditText ProdAmount;
    TextView list;

    DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProdAdd = findViewById(R.id.AddProd);
        ProdDel = findViewById(R.id.DelProd);
        AllClear = findViewById(R.id.ClearAll);
        ProdName = findViewById(R.id.NameProduct);
        ProdAmount = findViewById(R.id.AmountProd);
        list = findViewById(R.id.DataList);

        final DataBaseManager manager = new DataBaseManager(this);

        Cursor cursor1 = manager.getAllRecords();
        list.setText("Num/Prod Name/Amount/weight:\n");
        int i=1;
        while(cursor1.moveToNext()){
            int id=cursor1.getInt(0);
            String name =cursor1.getString(1);
            String password = cursor1.getString(2);

            list.setText(list.getText()+"\n" + i + ". " + name + " - " + password);
            i++;
        }

        ProdName.setText("");
        ProdAmount.setText("");


        ProdAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProdName.getText().toString().equals("") || ProdAmount.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(),"Set Name and the amount/weight of product",Toast.LENGTH_LONG).show();

                } else
                    {


                        manager.addRecord(ProdName.getText().toString(), ProdAmount.getText().toString());
                        Cursor cursor2 = manager.getAllRecords();
                        list.setText("Num/Prod Name/Amount/weight:\n");
                        int i = 1;
                        while(cursor2.moveToNext()){
                            int id=cursor2.getInt(0);
                            String name =cursor2.getString(1);
                            String password = cursor2.getString(2);
                            list.setText(list.getText()+"\n" + i + ". " + name + " - " + password);
                            i++;


                        }


                        ProdName.setText("");
                        ProdAmount.setText("");
                }

            }
        });


        AllClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.delAllRecords();
                Cursor cursor3 = manager.getAllRecords();
                list.setText("Num/Prod Name/Amount/weight:\n");
                int i=1;
                while(cursor3.moveToNext()){
                    int id=cursor3.getInt(0);
                    String name =cursor3.getString(1);
                    String password = cursor3.getString(2);
                    list.setText(list.getText()+"\n" + i + ". " + name + " - " + password);
                    i++;

                }


                ProdName.setText("");
                ProdAmount.setText("");
            }
        });





    }
}
