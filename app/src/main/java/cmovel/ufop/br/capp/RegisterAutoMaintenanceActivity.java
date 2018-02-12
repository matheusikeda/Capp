package cmovel.ufop.br.capp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RegisterAutoMaintenanceActivity extends AppCompatActivity {


    private DatabaseReference firebase;
    private FirebaseUser auth;
    private FirebaseAuth mAuth;
    private ValueEventListener valueEventListener;

    private User user;
    private EditText etDate;
    private EditText etExpiration;
    private EditText etPrice;

    Calendar date;
    Calendar expiration;

    Calendars cDate;
    Calendars cExpiration;

    DatePickerDialog dpd;
    DatePickerDialog dpd2;

    private String maintenance;
    private int position;

    int day, year, month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_auto_maintenance);
        mAuth = FirebaseAuth.getInstance();
        auth = mAuth.getCurrentUser();

        firebase = FirebaseDatabase.getInstance().getReference()
                .child("user").child(auth.getUid().toString()).child("users");

        Intent it = getIntent();
        Bundle params = it.getExtras();
        position = (int) params.get("position");
        maintenance = (String) params.get("automaintenance");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    user = data.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Register Auto Maintenance");

        TextView name = findViewById(R.id.name);
        name.setText(maintenance);

        date = Calendar.getInstance();
        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR );

        etDate = findViewById(R.id.editDate);
        etExpiration = findViewById(R.id.editExpiration);

        etDate.setText(day + "/" + (month+1) + "/" + year);
        etExpiration.setText(day + "/" + (month+1) + "/" + year);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR );

                dpd = new DatePickerDialog(RegisterAutoMaintenanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        cDate = new Calendars(mYear,mMonth, mDay);
                        etDate.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        etExpiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expiration = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR );

                dpd2 = new DatePickerDialog(RegisterAutoMaintenanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        cExpiration = new Calendars(mYear,mMonth, mDay);
                        etExpiration.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd2.show();
            }
        });

    }

    public void registerMaintenance(View view) {
//        etDate = findViewById(R.id.editDate);
//        etExpiration = findViewById(R.id.editExpiration);
        etPrice = findViewById(R.id.editPrice);

        final Calendars mDate = cDate;
        final Calendars mExpiration = cExpiration;
        final float price = Float.parseFloat(etPrice.getText().toString());

        AutoMaintenance autoMaintenance = new AutoMaintenance(maintenance, price, mDate, mExpiration);

        user.getAutoMaintenances().add(autoMaintenance);
        saveMaintenance(user);

        finish();
    }

    private boolean saveMaintenance(User user) {
        try {
            firebase = FirebaseDatabase.getInstance().getReference()
                    .child("user").child(auth.getUid().toString()).child("users");

            firebase.child("" + user.getCpf()).setValue(user);
            Toast.makeText(this, "Auto Maintenance registered succesfull", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }
}
