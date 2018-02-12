package cmovel.ufop.br.capp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Matheus Ikeda on 11/02/2018.
 */

public class CarAdapter extends BaseAdapter {
    private Context context;
    private List<Car> list;

    public CarAdapter(Context context, List<Car> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        Car car = list.get(position);
        return car;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Recovers the Doctor in the current position
        Car car = list.get(position);

        //Creates a View object from the given XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.car_detail, null);

        //Updates TextView’s text to the Doctor’s info
        TextView textName = v.findViewById(R.id.model);
        textName.setText(car.getModel());

        TextView textLicensePlate = v.findViewById(R.id.licensePlate);
        textLicensePlate.setText(car.getLicensePlate());

        return v;
    }
}
