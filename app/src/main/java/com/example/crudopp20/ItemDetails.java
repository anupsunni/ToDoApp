package com.example.crudopp20;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemDetails implements Parcelable {
    String image,name,info;

    public ItemDetails(String name, String info, String image) {
        this.image = image;

        this.name = name;

        this.info = info;
    }

    public ItemDetails(Parcel in) {
        image = in.readString();
        name = in.readString();
        info = in.readString();
    }

    public static final Creator<ItemDetails> CREATOR = new Creator<ItemDetails>() {
        @Override
        public ItemDetails createFromParcel(Parcel in) {
            return new ItemDetails(in);
        }

        @Override
        public ItemDetails[] newArray(int size) {
            return new ItemDetails[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(image);
    }


}
