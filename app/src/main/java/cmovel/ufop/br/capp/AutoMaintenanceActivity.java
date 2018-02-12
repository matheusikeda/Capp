package cmovel.ufop.br.capp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AutoMaintenanceActivity extends AppCompatActivity {

    private ArrayList<String> autoMaintenance;
    private AutoMaintenanceAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_maintenance);

        autoMaintenance = new ArrayList<>();
        listView = findViewById(R.id.listMaintenance);

        autoMaintenance.add("Alignment");
        autoMaintenance.add("Brakes");
        autoMaintenance.add("Engine Repair Services");
        autoMaintenance.add("Tire Repair");
        autoMaintenance.add("Oil Change");
        autoMaintenance.add("Baterries");
        autoMaintenance.add("Tune-Up");
        autoMaintenance.add("Transmission");
        autoMaintenance.add("AC Service");
        autoMaintenance.add("Radiator");
        autoMaintenance.add("Steering & Suspension");

        adapter = new AutoMaintenanceAdapter(this, autoMaintenance);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String autoMaintenance = (String) adapter.getItem(i);

                //Call maintenance activity passing info about the selected doctor
                Intent it = new Intent(AutoMaintenanceActivity.this, RegisterAutoMaintenanceActivity.class);
                it.putExtra("position", i);
                it.putExtra("automaintenance", autoMaintenance);
                startActivity(it);
            }
        });

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("New Auto Maintenance");
    }
}
