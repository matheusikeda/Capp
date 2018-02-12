package cmovel.ufop.br.capp;

import java.util.ArrayList;

/**
 * Created by Matheus Ikeda on 08/02/2018.
 */

public class User {
    private String cpf;
    private String name;
    private String gender;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<CarRepairShop> carRepairShops = new ArrayList<>();
    private ArrayList<AutoMaintenance> autoMaintenances = new ArrayList<>();

    public User(){

    }

    public User(String cpf, String name) {
        this.cpf = cpf;
        this.name = name;
        this.gender = "";
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<CarRepairShop> getCarRepairShops() {
        return carRepairShops;
    }

    public void setCarRepairShops(ArrayList<CarRepairShop> carRepairShops) {
        this.carRepairShops = carRepairShops;
    }

    public ArrayList<AutoMaintenance> getAutoMaintenances() {
        return autoMaintenances;
    }

    public void setAutoMaintenances(ArrayList<AutoMaintenance> autoMaintenances) {
        this.autoMaintenances = autoMaintenances;
    }
}
