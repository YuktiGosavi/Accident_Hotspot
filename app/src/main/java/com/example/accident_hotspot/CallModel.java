package com.example.accident_hotspot;

public class CallModel {

    String name;
    String time;
    int callTypeIcon;
    String number;

    public CallModel(String name, String time, int callTypeIcon, String number) {
        this.name = name;
        this.time = time;
        this.callTypeIcon = callTypeIcon;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getCallTypeIcon() {
        return callTypeIcon;
    }

    public String getNumber() {
        return number;
    }
}

