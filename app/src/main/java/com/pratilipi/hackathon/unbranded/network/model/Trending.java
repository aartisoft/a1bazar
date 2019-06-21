
package com.pratilipi.hackathon.unbranded.network.model;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trending implements Serializable, Parcelable
{

    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    public final static Creator<Trending> CREATOR = new Creator<Trending>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Trending createFromParcel(Parcel in) {
            return new Trending(in);
        }

        public Trending[] newArray(int size) {
            return (new Trending[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7740399288965781128L;

    protected Trending(Parcel in) {
        this.statusCode = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (Data.class.getClassLoader()));
    }

    public Trending() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(statusCode);
        dest.writeValue(message);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
