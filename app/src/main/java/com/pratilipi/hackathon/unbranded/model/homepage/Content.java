
package com.pratilipi.hackathon.unbranded.model.homepage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content implements Parcelable
{

    @SerializedName("content_heading")
    @Expose
    private String contentHeading;
    @SerializedName("content_author")
    @Expose
    private String contentAuthor;
    @SerializedName("content_view")
    @Expose
    private String contentView;
    @SerializedName("content_duration")
    @Expose
    private String contentDuration;
    @SerializedName("content_date")
    @Expose
    private String contentDate;
    @SerializedName("content_rate")
    @Expose
    private String contentRate;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("content_rate_count")
    @Expose
    private String contentRateCount;
    @SerializedName("content_img")
    @Expose
    private String contentImg;
    public final static Creator<Content> CREATOR = new Creator<Content>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        public Content[] newArray(int size) {
            return (new Content[size]);
        }

    }
    ;

    protected Content(Parcel in) {
        this.contentHeading = ((String) in.readValue((String.class.getClassLoader())));
        this.contentAuthor = ((String) in.readValue((String.class.getClassLoader())));
        this.contentView = ((String) in.readValue((String.class.getClassLoader())));
        this.contentDuration = ((String) in.readValue((String.class.getClassLoader())));
        this.contentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.contentRate = ((String) in.readValue((String.class.getClassLoader())));
        this.contentType = ((String) in.readValue((String.class.getClassLoader())));
        this.contentRateCount = ((String) in.readValue((String.class.getClassLoader())));
        this.contentImg = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Content() {
    }

    public String getContentHeading() {
        return contentHeading;
    }

    public void setContentHeading(String contentHeading) {
        this.contentHeading = contentHeading;
    }

    public String getContentAuthor() {
        return contentAuthor;
    }

    public void setContentAuthor(String contentAuthor) {
        this.contentAuthor = contentAuthor;
    }

    public String getContentView() {
        return contentView;
    }

    public void setContentView(String contentView) {
        this.contentView = contentView;
    }

    public String getContentDuration() {
        return contentDuration;
    }

    public void setContentDuration(String contentDuration) {
        this.contentDuration = contentDuration;
    }

    public String getContentDate() {
        return contentDate;
    }

    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public String getContentRate() {
        return contentRate;
    }

    public void setContentRate(String contentRate) {
        this.contentRate = contentRate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentRateCount() {
        return contentRateCount;
    }

    public void setContentRateCount(String contentRateCount) {
        this.contentRateCount = contentRateCount;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(contentHeading);
        dest.writeValue(contentAuthor);
        dest.writeValue(contentView);
        dest.writeValue(contentDuration);
        dest.writeValue(contentDate);
        dest.writeValue(contentRate);
        dest.writeValue(contentType);
        dest.writeValue(contentRateCount);
        dest.writeValue(contentImg);
    }

    public int describeContents() {
        return  0;
    }

}
