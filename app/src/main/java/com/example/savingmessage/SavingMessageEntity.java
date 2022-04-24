package com.example.savingmessage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="prefabs")
public class SavingMessageEntity {

    public SavingMessageEntity(int userID, String title, String caller) {
        this.userID = userID;
        this.title = title;
        this.caller = caller;
    }


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int userID;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "caller")
    public String caller;

}
