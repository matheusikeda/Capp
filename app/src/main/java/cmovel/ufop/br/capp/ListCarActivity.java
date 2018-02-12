package cmovel.ufop.br.capp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListCarActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseUser user;
    private ValueEventListener valueEventListener;

    private ArrayList<Car> cars;
    private CarAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);

        View fabDoc = findViewById(R.id.registerCar);
        fabDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListCarActivity.this, RegisterCarActivity.class);
                startActivity(it);

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();

        cars = new ArrayList<>();
        listView = findViewById(R.id.listCar);
        adapter = new CarAdapter(this, cars);
        listView.setAdapter(adapter);

        firebase = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid().toString()).child("users");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cars.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    User newUser = data.getValue(User.class);
                    cars.addAll(newUser.getCars());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car = (Car) adapter.getItem(i);
                Toast.makeText(ListCarActivity.this, "Car selected: " + car.getModel(), Toast.LENGTH_SHORT).show();

                //Call Car activity passing info about the selected doctor
                Intent it = new Intent(ListCarActivity.this, EditCarActivity.class);
                it.putExtra("position", i);
                it.putExtra("car", (Parcelable) car);
                startActivity(it);
            }
        });

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Cars");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
