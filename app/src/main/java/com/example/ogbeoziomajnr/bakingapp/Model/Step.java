package com.example.ogbeoziomajnr.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SQ-OGBE PC on 27/06/2017.
 * The steps to take in making the recipe
 */

public class Step implements Parcelable {
    public static final Parcelable.Creator<Step> CREATOR = new Creator<Step>() {

        @Override
        public Step createFromParcel(Parcel source) {
            Step step = new Step();
            step.setId(source.readInt());
            step.setShortDescription(source.readString());
            step.setDescription(source.readString());
            step.setVideoURL(source.readString());
            step.setThumbnailURL(source.readString());
            return step;
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[0];
        }
    };
    // the id of the step
    @SerializedName("id")
    private Integer id;
    // description of the steps
    @SerializedName("shortDescription")
    private String shortDescription;
    // description of the steps
    @SerializedName("description")
    private String description;
    // a link to the video url if any
    @SerializedName("videoURL")
    private String videoURL;
    // the thumbnail url of the step if any
    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
