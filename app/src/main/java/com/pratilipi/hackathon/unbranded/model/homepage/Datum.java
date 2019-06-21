
package com.pratilipi.hackathon.unbranded.model.homepage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum implements Parcelable
{

    @SerializedName("header")
    @Expose
    private String header;
    @SerializedName("header_img")
    @Expose
    private String headerImg;
    @SerializedName("content")
    @Expose
    private List<Content> content = null;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    protected Datum(Parcel in) {
        this.header = ((String) in.readValue((String.class.getClassLoader())));
        this.headerImg = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.content, (Content.class.getClassLoader()));
    }

    public Datum() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(header);
        dest.writeValue(headerImg);
        dest.writeList(content);
    }

    public int describeContents() {
        return  0;
    }

}
