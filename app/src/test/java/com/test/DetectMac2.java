package com.test;

/**
 * Created by Lyndon.Li on 2018/10/10.
 */


import android.os.Parcel;
import android.os.Parcelable;

public class DetectMac2 implements Parcelable {

    private String probeMac; // probe mac
    private String detectMac2; // bssid

    public DetectMac2() {
        probeMac = "";
        detectMac2 = "";
    }

    public DetectMac2(Parcel source) {
        probeMac = source.readString();
        detectMac2 = source.readString();
    }

    public String getProbeMac() {
        return probeMac;
    }

    public void setProbeMac(String probeMac) {
        this.probeMac = probeMac;
    }

    public String getDetectMac2() {
        return detectMac2;
    }

    public void setDetectMac2(String detectMac2) {
        this.detectMac2 = detectMac2;
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Creator() {

        @Override
        public DetectMac2 createFromParcel(Parcel source) {
            return new DetectMac2(source);
        }

        @Override
        public DetectMac2[] newArray(int size) {
            return new DetectMac2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(probeMac);
        dest.writeString(detectMac2);
    }

    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof DetectMac2) {
            DetectMac2 d = (DetectMac2) o;
            if (d.getProbeMac().equals(this.getProbeMac()) && d.getDetectMac2().equals(this.getDetectMac2())) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public int hashCode(){
        int result = 17;
        result = result*37 + probeMac.hashCode();
        result = result*37 + detectMac2.hashCode();
        return result;
    }

}