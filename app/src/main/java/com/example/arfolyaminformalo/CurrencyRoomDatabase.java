package com.example.arfolyaminformalo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Currency.class}, version = 1, exportSchema = false)
public abstract class CurrencyRoomDatabase extends RoomDatabase {
    public abstract CurrencyDAO currencyDAO();

    private static CurrencyRoomDatabase instance;

    public static CurrencyRoomDatabase getInstance(Context context){
        if(instance == null){
            synchronized (CurrencyRoomDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            CurrencyRoomDatabase.class,
                            "currency_database").fallbackToDestructiveMigration().
                            addCallback(populationCallback).build();
                }
            }
        }

        return instance;
    }

    private static RoomDatabase.Callback populationCallback = new RoomDatabase.Callback(){
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            new InitDb(instance).execute();
        }
    };

    private static class InitDb extends AsyncTask<Void, Void, Void>{
        private CurrencyDAO dao;
        String[] names = {"Euró", "Dollár"};
        String[] shortNames = {"EUR", "USD"};
        String[] buyPrice = {"250", "230"};
        String[] sellPrice = {"220", "210"};
        int[] image = {1, 2};
        int[] imageChart = {3, 4};

        InitDb(CurrencyRoomDatabase db){
            this.dao = db.currencyDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.dao.deleteAll();

            for (int i = 0; i < names.length ; i++) {
                this.dao.insert(new Currency(names[i],shortNames[i],buyPrice[i],sellPrice[i],image[i],imageChart[i]));
            }
            return null;
        }
    }
}
