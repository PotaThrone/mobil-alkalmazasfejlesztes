package com.example.arfolyaminformalo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CurrencyDAO {
    @Insert
    void insert(Currency currency);

    @Query("DELETE FROM currency_table")
    void deleteAll();

    @Delete
    void delete(Currency currency);

    @Update
    void update(Currency currency);

    @Query("SELECT * FROM currency_table order by name")
    LiveData<List<Currency>> getCurrencies();
}
