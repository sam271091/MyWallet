package com.example.mywallet;

import com.example.mywallet.converters.UUIDConverter;

import java.io.Serializable;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "valueitems")
public class ValueItem implements Serializable {
    @PrimaryKey
    @TypeConverters(value = UUIDConverter.class)
    @NonNull
    private UUID id;
    private String name;
    private String comment;
    private String pictureID;

    public ValueItem() {
        this.id = UUID.randomUUID();
    }

    @Ignore
    public ValueItem(UUID id, String name, String comment,String pictureID) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.pictureID = pictureID;
    }

    @Ignore
    public ValueItem(String name, String comment,String pictureID) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.comment = comment;
        this.pictureID = pictureID;
    }

    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
