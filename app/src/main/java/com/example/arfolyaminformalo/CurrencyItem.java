package com.example.arfolyaminformalo;

public class CurrencyItem {
    private String id;
    private String name;
    private String shortName;
    private String buyPrice;
    private String sellPrice;
    private int currencyImage;
    private int chartImage;
    private boolean subscribed;


    public CurrencyItem(String name, String shortName, String buyPrice, String sellPrice) {
        this.name = name;
        this.shortName = shortName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.subscribed = false;
    }

    public CurrencyItem() {
    }

    public CurrencyItem(String name, String shortName, String buyPrice, String sellPrice, int currencyImage, int chartImage, boolean subscribed) {
        this.name = name;
        this.shortName = shortName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currencyImage = currencyImage;
        this.chartImage = chartImage;
        this.subscribed = subscribed;
    }


    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
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

    public String _getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
