
package com.edfora.sunny.edfora.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable {

    @SerializedName("artists")
    private String mArtists;
    @SerializedName("cover_image")
    private String mCoverImage;
    @SerializedName("song")
    private String mSong;
    @SerializedName("url")
    private String mUrl;

    public String getArtists() {
        return mArtists;
    }

    public void setArtists(String artists) {
        mArtists = artists;
    }

    public String getCoverImage() {
        return mCoverImage;
    }

    public void setCoverImage(String coverImage) {
        mCoverImage = coverImage;
    }

    public String getSong() {
        return mSong;
    }

    public void setSong(String song) {
        mSong = song;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mArtists);
        dest.writeString(this.mCoverImage);
        dest.writeString(this.mSong);
        dest.writeString(this.mUrl);
    }

    public Track() {
    }

    protected Track(Parcel in) {
        this.mArtists = in.readString();
        this.mCoverImage = in.readString();
        this.mSong = in.readString();
        this.mUrl = in.readString();
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
