package com.example.arfolyaminformalo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CurrencyRepository {
    private CurrencyDAO dao;
    LiveData<List<Currency>> currencies;

    CurrencyRepository(Application application){
        CurrencyRoomDatabase db = CurrencyRoomDatabase.getInstance(application);
        dao = db.currencyDAO();
        currencies = dao.getCurrencies();
    }

    public  LiveData<List<Currency>> getAllCurrencies(){
        return currencies;
    }

    public void insert(Currency currency){
        new Insert(this.dao).execute(currency);
    }

    private static class Insert extends AsyncTask<Currency, Void, Void> {
        private CurrencyDAO mDAO;

        Insert(CurrencyDAO dao){
            this.mDAO = dao;
        }

        @Override
        protected Void doInBackground(Currency... currencies) {
            mDAO.insert(currencies[0]);
            return null;
        }
    }
}
