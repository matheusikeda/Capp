package cmovel.ufop.br.capp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterCarActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseUser auth;
    private FirebaseAuth mAuth;
    private ValueEventListener valueEventListener;

    private User user;
    private EditText etModel;
    private EditText etLicense;
    private EditText etMake;
    private EditText etYear;
    private Spinner engines;
    private Spinner transmissions;

    private static final String[] ENGINE = {"1.0","1.2","1.4","1.8","2.0"};
    private static final String[] TRANSMISSIONS = {"Automanual","Automatic"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);
        mAuth = FirebaseAuth.getInstance();
        auth = mAuth.getCurrentUser();

        firebase = FirebaseDatabase.getInstance().getReference()
                .child("user").child(auth.getUid().toString()).child("users");

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
        supportActionBar.setTitle("Register Car");

        initSpinner();
    }

    private void initSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,ENGINE);
        Spinner engine = findViewById(R.id.eSpinner);
        engine.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,TRANSMISSIONS);
        Spinner transmission = findViewById(R.id.tSpinner);
        transmission.setAdapter(adapter2);
    }


    public void registerCar(View view) {
        etModel = findViewById(R.id.editCarModel);
        etLicense = findViewById(R.id.editLicensePlate);
        etMake = findViewById(R.id.editMake);
        etYear = findViewById(R.id.editYear);
        engines = findViewById(R.id.eSpinner);
        transmissions = findViewById(R.id.tSpinner);

        final String model = etModel.getText().toString();
        final String license = etLicense.getText().toString();
        final String make = etMake.getText().toString();
        final String year = etYear.getText().toString();
        final String engine = (String) engines.getSelectedItem();
        final String transmission = (String) transmissions.getSelectedItem();

        Car car = new Car(license, model, make,engine, transmission,year);

        user.getCars().add(car);
        saveCar(user);

        finish();
    }

    private boolean saveCar(User user) {
        try {
            firebase = FirebaseDatabase.getInstance().getReference()
                    .child("user").child(auth.getUid().toString()).child("users");

            firebase.child("" + user.getCpf()).setValue(user);
            Toast.makeText(this, "Car registered succesfull", Toast.LENGTH_SHORT).show();
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