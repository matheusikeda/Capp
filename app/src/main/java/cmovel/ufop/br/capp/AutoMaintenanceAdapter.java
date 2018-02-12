package cmovel.ufop.br.capp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Matheus Ikeda on 12/02/2018.
 */

public class AutoMaintenanceAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public AutoMaintenanceAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        String autoMaintenance = list.get(position);
        return autoMaintenance;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Recovers the Doctor in the current position
        String autoMaintenance = list.get(position);

        //Creates a View object from the given XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.auto_maintenance_detail, null);

        //Updates TextView’s text to the Auto Maintenance’s info
        TextView textName = v.findViewById(R.id.name);
        textName.setText(autoMaintenance);

        return v;
    }
}
