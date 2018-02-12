package cmovel.ufop.br.capp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Matheus Ikeda on 11/02/2018.
 */

public class CarRepairShop implements Parcelable, Serializable {
    private String name;
    private String location;
    private String phone;

    public CarRepairShop(){

    }

    public CarRepairShop(String name, String location, String phone) {
        this.name = name;
        this.location = location;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarRepairShop> CREATOR = new Creator<CarRepairShop>() {
        @Override
        public CarRepairShop createFromParcel(Parcel in) {
            return new CarRepairShop(in.readString(), in.readString(), in.readString());
        }

        @Override
        public CarRepairShop[] newArray(int size) {
            return new CarRepairShop[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(phone);
    }
}
