package cmovel.ufop.br.capp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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
    private EditText etBrand;

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
    }

    public void registerCar(View view) {
        etModel = findViewById(R.id.editCarModel);
        etLicense = findViewById(R.id.editLicensePlate);
        etBrand = findViewById(R.id.editBrand);

        final String model = etModel.getText().toString();
        final String license = etLicense.getText().toString();
        final String brand = etBrand.getText().toString();

        Car car = new Car(license, model, brand);
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