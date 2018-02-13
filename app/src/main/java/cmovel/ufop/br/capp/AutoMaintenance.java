package cmovel.ufop.br.capp;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Matheus Ikeda on 12/02/2018.
 */

public class AutoMaintenance {
    private String name;
    private Float price;
    private Calendars date;
    private Calendars expiration;

    public AutoMaintenance(){

    }

    public AutoMaintenance(String name, Float price, Calendars date, Calendars expiration) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.expiration = expiration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendars getDate() {
        return date;
    }

    public void setDate(Calendars date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Calendars getExpiration() {
        return expiration;
    }

    public void setExpiration(Calendars expiration) {
        this.expiration = expiration;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Parcelable.Creator<AutoMaintenance> CREATOR = new Parcelable.Creator<AutoMaintenance>() {
//        @Override
//        public AutoMaintenance createFromParcel(Parcel in) {
//            return new AutoMaintenance(in.readString(), in.readFloat());
//        }
//
//        @Override
//        public AutoMaintenance[] newArray(int size) {
//            return new AutoMaintenance[size];
//        }
//    };
//
//    @Override
//    public void writeToParcel(Parcel dest, int i) {
//        dest.writeString(name);
//        dest.writeFloat(price);
//    }
}
