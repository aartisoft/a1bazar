package com.pratilipi.hackathon.unbranded.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable, Parcelable {

    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };
    private final static long serialVersionUID = -19985265855229240L;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("user_products")
    @Expose
    private List<UserProduct> userProducts = null;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    protected Data(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.products, (Product.class.getClassLoader()));
        in.readList(this.users, (User.class.getClassLoader()));
    }

    public Data() {
    }

    public List<UserProduct> getUserProducts() {
        return userProducts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeList(products);
        dest.writeList(users);
    }

    public int describeContents() {
        return 0;
    }

}
