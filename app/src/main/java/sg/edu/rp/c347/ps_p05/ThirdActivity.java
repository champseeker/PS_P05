package sg.edu.rp.c347.ps_p05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    TextView tvID;
    EditText etTitle, etSinger, etYear;
    RadioButton rb1, rb2, rb3, rb4, rb5, rbSelected;
    RadioGroup rg;
    Button btnCancel, btnUpdate, btnDelete;
    Songs data;

    int star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvID = findViewById(R.id.tvShowID);
        etTitle = findViewById(R.id.etTitle3);
        etSinger = findViewById(R.id.etSingers3);
        etYear = findViewById(R.id.etYear3);
        rg = findViewById(R.id.rgStar3);
        rb1 = findViewById(R.id.rb13);
        rb2 = findViewById(R.id.rb23);
        rb3 = findViewById(R.id.rb33);
        rb4 = findViewById(R.id.rb43);
        rb5 = findViewById(R.id.rb53);
        btnUpdate = findViewById(R.id.btnUpdate3);
        btnDelete = findViewById(R.id.btnDelete3);
        btnCancel = findViewById(R.id.btnCancel3);

        Intent i = getIntent();
        data = (Songs) i.getSerializableExtra("data");

        tvID.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(String.valueOf(data.getYear()));

        if(data.getStars() == 1){
            rb1.setChecked(true);
        }else if(data.getStars() == 2){
            rb2.setChecked(true);
        }else if(data.getStars() == 3){
            rb3.setChecked(true);
        }else if(data.getStars() == 4){
            rb4.setChecked(true);
        }else if (data.getStars() == 5){
            rb5.setChecked(true);
        }

            btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                int selectedRB = rg.getCheckedRadioButtonId();
                rbSelected = (RadioButton) findViewById(selectedRB);
                star = Integer.parseInt(rbSelected.getText().toString());

                Songs newSong = new Songs(data.getId(), title, singer, year, star);
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.updateSong(newSong);
                dbh.close();
                setResult(RESULT_OK);
                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteSong(data.getId());
                dbh.close();
                setResult(RESULT_OK);
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }
}
