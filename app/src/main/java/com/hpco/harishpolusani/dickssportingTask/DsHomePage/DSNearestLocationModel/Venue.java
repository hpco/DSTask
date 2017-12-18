package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue implements  Comparable<Venue>, Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("ratingColor")
    @Expose
    private String ratingColor;
    @SerializedName("ratingSignals")
    @Expose
    private Integer ratingSignals;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts = null;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("canonicalUrl")
    @Expose
    private String canonicalUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("shortUrl")
    @Expose
    private String shortUrl;
    @SerializedName("timeZone")
    @Expose
    private String timeZone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public Integer getRatingSignals() {
        return ratingSignals;
    }

    public void setRatingSignals(Integer ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public int compareTo(@NonNull Venue venue) {
        return this.getStoreId().compareTo(venue.getStoreId());
    }
    public Venue(){

     }
    protected Venue(Parcel in) {
        id = in.readString();
        name = in.readString();
        byte verifiedVal = in.readByte();
        verified = verifiedVal == 0x02 ? null : verifiedVal != 0x00;
        url = in.readString();
        ratingColor = in.readString();
        ratingSignals = in.readByte() == 0x00 ? null : in.readInt();
        rating = in.readByte() == 0x00 ? null : in.readDouble();
        storeId = in.readString();
        location = (Location) in.readValue(Location.class.getClassLoader());
        if (in.readByte() == 0x01) {
            contacts = new ArrayList<Contact>();
            in.readList(contacts, Contact.class.getClassLoader());
        } else {
            contacts = null;
        }
        if (in.readByte() == 0x01) {
            photos = new ArrayList<Photo>();
            in.readList(photos, Photo.class.getClassLoader());
        } else {
            photos = null;
        }
        canonicalUrl = in.readString();
        description = in.readString();
        shortUrl = in.readString();
        timeZone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        if (verified == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (verified ? 0x01 : 0x00));
        }
        dest.writeString(url);
        dest.writeString(ratingColor);
        if (ratingSignals == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(ratingSignals);
        }
        if (rating == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(rating);
        }
        dest.writeString(storeId);
        dest.writeValue(location);
        if (contacts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(contacts);
        }
        if (photos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(photos);
        }
        dest.writeString(canonicalUrl);
        dest.writeString(description);
        dest.writeString(shortUrl);
        dest.writeString(timeZone);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}