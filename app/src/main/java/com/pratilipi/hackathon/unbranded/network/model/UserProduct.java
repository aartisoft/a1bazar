package com.pratilipi.hackathon.unbranded.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserProduct implements Serializable, Parcelable {

    public final static Creator<UserProduct> CREATOR = new Creator<UserProduct>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserProduct createFromParcel(Parcel in) {
            return new UserProduct(in);
        }

        public UserProduct[] newArray(int size) {
            return (new UserProduct[size]);
        }

    };
    private final static long serialVersionUID = -4453803372616764830L;
    @SerializedName("userList")
    @Expose
    private ArrayList<User> userList;

    @SerializedName("productList")
    @Expose
    private ArrayList<Product> productList;

    public UserProduct(Parcel in) {
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
