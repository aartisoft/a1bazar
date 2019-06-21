package com.pratilipi.hackathon.unbranded.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable, Parcelable {

    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };
    private final static long serialVersionUID = -7046477923578082424L;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("followers")
    @Expose
    private long followers;
    @SerializedName("viewCount")
    @Expose
    private long viewCount;
    @SerializedName("pageUrl")
    @Expose
    private String pageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private long price;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("email")
    @Expose
    private String email;
    protected User(Parcel in) {
        this.id = ((long) in.readValue((long.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.followers = ((long) in.readValue((long.class.getClassLoader())));
        this.viewCount = ((long) in.readValue((long.class.getClassLoader())));
        this.pageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((long) in.readValue((long.class.getClassLoader())));
        this.rating = ((double) in.readValue((double.class.getClassLoader())));
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(imageUrl);
        dest.writeValue(followers);
        dest.writeValue(viewCount);
        dest.writeValue(pageUrl);
        dest.writeValue(description);
        dest.writeValue(price);
        dest.writeValue(rating);
    }

    public int describeContents() {
        return 0;
    }

}
