package cmovel.ufop.br.capp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditCarRepairShopActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseUser auth;
    private ValueEventListener valueEventListener;

    private CarRepairShop carRepairShop;
    private int position = 0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_repair_shop);
        auth = FirebaseAuth.getInstance().getCurrentUser();

        Intent it = getIntent();
        Bundle params = it.getExtras();
        position = (int) params.get("position");
        carRepairShop = (CarRepairShop) params.get("carRepairShop");

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

        EditText etName = findViewById(R.id.editName);
        etName.setText(carRepairShop.getName());

        EditText etLocation = findViewById(R.id.editLocation);
        etLocation.setText(carRepairShop.getLocation());

        EditText etPhone = findViewById(R.id.editPhone);
        etPhone.setText(carRepairShop.getPhone());

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Edit Car Repair Shop");
    }

    public void editCarRepairShop(View view){
        EditText etName = findViewById(R.id.editName);
        String name = etName.getText().toString();

        EditText etLocation = findViewById(R.id.editLocation);
        String location = etLocation.getText().toString();

        EditText etPhone = findViewById(R.id.editPhone);
        String phone = etPhone.getText().toString();

        CarRepairShop carRepairShop = new CarRepairShop(name,location,phone);
        user.getCarRepairShops().set(position,carRepairShop);
        editUser(user);
        Toast.makeText(this, "Car Repair Shop edited succesfull", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteCarRepairShop(View view) {
        user.getCarRepairShops().remove(position);
        editUser(user);
        Toast.makeText(this, "Car Repair Shop removed successfully!", Toast.LENGTH_SHORT).show();

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
