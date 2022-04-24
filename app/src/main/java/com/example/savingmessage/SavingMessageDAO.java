package com.example.savingmessage;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SavingMessageDAO {

    @Query("SELECT * FROM prefabs ORDER BY title, rowid")
    LiveData<List<SavingMessageEntity>> getAll();


    @Query("SELECT * FROM prefabs WHERE rowid = :userID")
    SavingMessageEntity getById(int userID);


    @Insert
    void insert(SavingMessageEntity... prefabs);


    @Update
    void update(SavingMessageEntity... prefabs);


    @Delete
    void delete(SavingMessageEntity... prefabs);


    @Query("DELETE FROM prefabs WHERE rowid = :userID")
    void delete(int userID);
}


