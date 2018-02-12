package cmovel.ufop.br.capp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUserActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseUser auth;
    private ValueEventListener valueEventListener;

    private User user;
    private EditText etName;
    private RadioButton rbFemale;
    private RadioButton rbMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        auth = FirebaseAuth.getInstance().getCurrentUser();

        firebase = FirebaseDatabase.getInstance().getReference()
                .child("user").child(auth.getUid().toString()).child("users");

        etName = findViewById(R.id.editFullName);
        rbFemale = findViewById(R.id.rBFemale);
        rbMale = findViewById(R.id.rBMale);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    user = data.getValue(User.class);
                    etName.setText(user.getName());
                    if (user.getGender().equals("Female"))
                        rbFemale.setChecked(true);
                    else
                        rbMale.setChecked(true);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Edit User");
    }

    public void editUserProfile(View view){
        EditText etName = findViewById(R.id.editFullName);
        String name = etName.getText().toString();

        user.setName(name);

        RadioButton  rbFemale = findViewById(R.id.rBFemale);

        RadioButton rbMale = findViewById(R.id.rBMale);

        if(rbFemale.isChecked()){

            user.setGender("Female");
        }else if(rbMale.isChecked()){

            user.setGender("Male");
        }

        editUser(user);
        Toast.makeText(this, "User edited succesfull", Toast.LENGTH_SHORT).show();
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
