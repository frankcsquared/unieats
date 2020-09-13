package com.example.unieats.models;

public class FoodList {
    private String name2;
    private Integer cals;
    private Integer amt;

    public FoodList(String name2, Integer cals, Integer amt) {
        this.name2 = name2;
        this.cals = cals;
        this.amt = amt;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name) {
        this.name2 = name;
    }

    public Integer getCals() {
        return cals;
    }

    public void setCals(Integer cals) {
        this.cals = cals;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }
}
