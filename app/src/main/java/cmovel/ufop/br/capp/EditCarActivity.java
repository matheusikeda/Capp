package cmovel.ufop.br.capp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCarActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseUser auth;
    private ValueEventListener valueEventListener;

    private Car car;
    private int position = 0;
    private User user;
    private static final String[] ENGINE = {"1.0","1.2","1.4","1.8","2.0"};
    private static final String[] TRANSMISSIONS = {"Automanual","Automatic"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        auth = FirebaseAuth.getInstance().getCurrentUser();

        Intent it = getIntent();
        Bundle params = it.getExtras();
        position = (int) params.get("position");
        car = (Car) params.get("car");

        firebase = FirebaseDatabase.getInstance().getReference()
                .child("user").child(auth.getUid().toString()).child("users");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    user = data.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };

        EditText etModel = findViewById(R.id.editCarModel);
        etModel.setText(car.getModel());

        EditText etLicensePlate = findViewById(R.id.editLicensePlate);
        etLicensePlate.setText(car.getLicensePlate());

        EditText etMake = findViewById(R.id.editMake);
        etMake.setText(car.getMake());

        EditText etYear = findViewById(R.id.editYear);
        etYear.setText(car.getYear());

        initSpinner();

        Spinner engines = findViewById(R.id.eSpinner);
        selectValue(engines,car.getEngine());
        Spinner transmissions = findViewById(R.id.tSpinner);
        selectValue(transmissions,car.getTransmission());

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Edit Car");

    }

    private void initSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,ENGINE);
        Spinner engine = findViewById(R.id.eSpinner);
        engine.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,TRANSMISSIONS);
        Spinner transmission = findViewById(R.id.tSpinner);
        transmission.setAdapter(adapter2);
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void editCar(View view){
        EditText etModel = findViewById(R.id.editCarModel);
        EditText etLicense = findViewById(R.id.editLicensePlate);
        EditText etMake = findViewById(R.id.editMake);
        EditText etYear = findViewById(R.id.editYear);
        Spinner engines = findViewById(R.id.eSpinner);
        Spinner transmissions = findViewById(R.id.tSpinner);

        final String model = etModel.getText().toString();
        final String license = etLicense.getText().toString();
        final String make = etMake.getText().toString();
        final String year = etYear.getText().toString();
        final String engine = (String) engines.getSelectedItem();
        final String transmission = (String) transmissions.getSelectedItem();

        Car car = new Car(license, model, make,engine, transmission,year);
        user.getCars().set(position,car);
        editUser(user);
        Toast.makeText(this, "Car edited succesfull", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteCar(View view) {
        user.getCars().remove(position);
        editUser(user);
        Toast.makeText(this, "Car removed successfully!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean editUser(User user){
        try{
            firebase = FirebaseDatabase.getInstance().getReference()
                    .child("user").child(auth.getUid().toString()).child("users");
            firebase.child("" + user.getCpf()).setValue(user);
            return true;
        } catch (Exception e){
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
