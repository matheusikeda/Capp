package cmovel.ufop.br.capp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Matheus Ikeda on 08/02/2018.
 */

public class Car implements Parcelable, Serializable {
    private String licensePlate;
    private String model;
    private String make;
    private String engine;
    private String transmission;
    private String year;

    public Car(){

    }

    public Car(String licensePlate, String model, String make, String engine, String transmission, String year) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.make = make;
        this.engine = engine;
        this.transmission = transmission;
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString());
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(licensePlate);
        dest.writeString(model);
        dest.writeString(make);
        dest.writeString(engine);
        dest.writeString(transmission);
        dest.writeString(year);
    }
}
