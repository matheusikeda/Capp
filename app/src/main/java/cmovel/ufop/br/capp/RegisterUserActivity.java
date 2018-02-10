package cmovel.ufop.br.capp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseUser auth;
    private FirebaseAuth mAuth;
    private User user;

    /**
     * Checks if the user is opening the app for the first time.
     * Note that this method should be placed inside an activity and it can be called multiple times.
     * @return boolean
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        auth = mAuth.getCurrentUser();

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Register Account");
    }


    public void registerUser(View view){
        EditText etName = findViewById(R.id.editFullName);
        String name = etName.getText().toString();

        EditText etCpf = findViewById(R.id.editCpf);
        String cpf = etCpf.getText().toString();

        user = new User(cpf,name);



        saveUser(user);

        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    private boolean saveUser(User user){
        try{
            firebase = FirebaseDatabase.getInstance().getReference()
                    .child("user").child(auth.getUid().toString()).child("users");

            firebase.child("" + user.getCpf()).setValue(user);
            Toast.makeText(this, "User registered succesfull", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}