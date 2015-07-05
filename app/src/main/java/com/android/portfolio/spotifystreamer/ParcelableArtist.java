package com.android.portfolio.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Smitesh on 7/4/2015.
 * Parcel to store artist data.
 */
public class ParcelableArtist implements Parcelable {
    private String artistName;
    private String artistImageUrl;
    private String artistId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(artistImageUrl);
        dest.writeString(artistId);
    }

    public static final Parcelable.Creator<ParcelableArtist> CREATOR
            = new Parcelable.Creator<ParcelableArtist>() {

        public ParcelableArtist createFromParcel(Parcel in) {
            return new ParcelableArtist(in);
        }

        public ParcelableArtist[] newArray(int size) {
            return new ParcelableArtist[size];
        }
    };

    public ParcelableArtist() {
    }

    public ParcelableArtist(Parcel in) {
        artistName = in.readString();
        artistImageUrl = in.readString();
        artistId = in.readString();
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImageUrl() {
        return artistImageUrl;
    }

    public void setArtistImageUrl(String artistImageUrl) {
        this.artistImageUrl = artistImageUrl;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
}
