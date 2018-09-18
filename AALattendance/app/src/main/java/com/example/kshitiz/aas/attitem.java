package com.example.kshitiz.aas;

public class attitem {
    String name;
    boolean status;
    public attitem(String s,boolean b){
        this.name=s;
        this.status=b;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
