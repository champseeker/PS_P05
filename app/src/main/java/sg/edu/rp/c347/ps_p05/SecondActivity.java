package sg.edu.rp.c347.ps_p05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    Button btn5Star;
    Spinner spinner;
    ListView lv;
    ArrayList<Songs> al;
    ArrayAdapter aa;
    ArrayList alYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn5Star = findViewById(R.id.btn5Star);
        spinner = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);

        alYear = new ArrayList<>();

        alYear.add("All");

        al = new ArrayList<Songs>();
        DBHelper dbh = new DBHelper(SecondActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        alYear.clear();
        alYear.add("All");
        alYear.addAll(dbh.getYear());
        dbh.close();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_spinner_item, alYear);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        aa = new SongArrayAdapter(SecondActivity.this, R.layout.row, al);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {

                Songs data = al.get(position);
                Intent i = new Intent(SecondActivity.this,
                        ThirdActivity.class);
                i.putExtra("data", data);
                startActivityForResult(i, 9);

            }
        });

        btn5Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbh = new DBHelper(SecondActivity.this);
                al.clear();
                al.addAll(dbh.getAllSongs("star", "5"));
                dbh.close();

                aa.notifyDataSetChanged();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                String year = parent.getItemAtPosition(pos).toString();

                DBHelper dbh = new DBHelper(SecondActivity.this);

                if(year.equals("All")){

                    al.clear();
                    al.addAll(dbh.getAllSongs());

                }else{

                    al.clear();
                    al.addAll(dbh.getAllSongs("year", year));

                }

                dbh.close();
                aa.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){

            DBHelper dbh = new DBHelper(SecondActivity.this);
            al.clear();
            al.addAll(dbh.getAllSongs());
            dbh.close();

            aa.notifyDataSetChanged();

        }
    }

}
