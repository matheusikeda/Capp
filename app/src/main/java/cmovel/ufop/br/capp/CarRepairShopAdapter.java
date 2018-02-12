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

public class CarRepairShopAdapter extends BaseAdapter {
    private Context context;
    private List<CarRepairShop> list;

    public CarRepairShopAdapter(Context context, List<CarRepairShop> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        CarRepairShop carRepairShop = list.get(position);
        return carRepairShop;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Recovers the Car Repair Shop in the current position
        CarRepairShop carRepairShop = list.get(position);

        //Creates a View object from the given XML layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.car_repair_shop_detail, null);

        //Updates TextView’s text to the Car Repair Shop’s info
        TextView textName = v.findViewById(R.id.name);
        textName.setText(carRepairShop.getName());
        TextView textLocation = v.findViewById(R.id.location);
        textLocation.setText(carRepairShop.getLocation());
        TextView textPhone = v.findViewById(R.id.phone);
        textPhone.setText(carRepairShop.getPhone());


        return v;
    }
}
