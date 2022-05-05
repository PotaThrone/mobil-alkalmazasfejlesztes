package com.example.arfolyaminformalo;

public class CurrencyItem {
    private String name;
    private String shortName;
    private String buyPrice;
    private String sellPrice;
    private int currencyImage;
    private int chartImage;


    public CurrencyItem(String name, String shortName, String buyPrice, String sellPrice, int currencyImage, int chartImage) {
        this.name = name;
        this.shortName = shortName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currencyImage = currencyImage;
        this.chartImage = chartImage;
    }

    public CurrencyItem() {
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
}
