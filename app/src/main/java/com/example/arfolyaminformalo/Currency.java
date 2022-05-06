package com.example.arfolyaminformalo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency_table")
public class Currency {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "shortName")
    private String shortName;
    @NonNull
    @ColumnInfo(name = "buyPrice")
    private String buyPrice;
    @NonNull
    @ColumnInfo(name = "sellPrice")
    private String sellPrice;
    @ColumnInfo(name = "currencyImage")
    private int currencyImage;
    @ColumnInfo(name = "chartImage")
    private int chartImage;
    @ColumnInfo(name = "subscribed")
    private boolean subscribed;

    public Currency(@NonNull String name,
                    @NonNull String shortName,
                    @NonNull String buyPrice,
                    @NonNull String sellPrice,
                    int currencyImage,
                    int chartImage) {
        this.name = name;
        this.shortName = shortName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currencyImage = currencyImage;
        this.chartImage = chartImage;
        this.subscribed = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public int getCurrencyImage() {
        return currencyImage;
    }

    public int getChartImage() {
        return chartImage;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrencyImage(int currencyImage) {
        this.currencyImage = currencyImage;
    }

    public void setChartImage(int chartImage) {
        this.chartImage = chartImage;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
