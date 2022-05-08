package com.example.arfolyaminformalo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class CurrencyItemAdapter extends RecyclerView.Adapter<CurrencyItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<CurrencyItem> mCurrenyItemsData;
    private ArrayList<CurrencyItem> mCurrenyItemsDataAll;
    private Context mContext;
    private int lastPosition = -1;

    CurrencyItemAdapter(Context context, ArrayList<CurrencyItem> itemsData) {
        this.mCurrenyItemsData = itemsData;
        this.mCurrenyItemsDataAll = itemsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CurrencyItemAdapter.ViewHolder holder, int position) {
        CurrencyItem currentItem = mCurrenyItemsData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            if(lastPosition / 2 == 0){
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_from_other_direction);
                holder.itemView.startAnimation(animation);
                lastPosition = holder.getAdapterPosition();
            }else{
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
                holder.itemView.startAnimation(animation);
                lastPosition = holder.getAdapterPosition();
            }

        }
    }

    @Override
    public int getItemCount() {
        return mCurrenyItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return currencyFilter;
    }

    private Filter currencyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<CurrencyItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = mCurrenyItemsDataAll.size();
                results.values = mCurrenyItemsDataAll;
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (CurrencyItem item : mCurrenyItemsDataAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mCurrenyItemsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameText;
        private TextView mShortNameText;
        private TextView mBuyPriceText;
        private TextView mSellPriceText;
        private ImageView mCurrencyChartImage;
        private ImageView mItemImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            mItemNameText = itemView.findViewById(R.id.itemNameText);
            mShortNameText = itemView.findViewById(R.id.shortNameText) ;
            mBuyPriceText = itemView.findViewById(R.id.buyPriceText);
            mSellPriceText = itemView.findViewById(R.id.sellPriceText);
            mCurrencyChartImage = itemView.findViewById(R.id.currencyChartImage);
            mItemImage = itemView.findViewById(R.id.itemImage);



        }

        public void bindTo(CurrencyItem currentItem) {
            mItemNameText.setText(currentItem.getName());
            mShortNameText.setText(currentItem.getShortName());
            mBuyPriceText.setText(currentItem.getBuyPrice());
            mSellPriceText.setText(currentItem.getSellPrice());


            Glide.with(mContext).load(currentItem.getCurrencyImage()).into(mItemImage);
            Glide.with(mContext).load(currentItem.getChartImage()).into(mCurrencyChartImage);
            itemView.findViewById(R.id.subscribeButton).setOnClickListener(view -> { ;
                ((ExchangeRateListActivity)mContext).updateAlertIcon(currentItem);
            });
            itemView.findViewById(R.id.deleteButton).setOnClickListener(view -> {
                ((ExchangeRateListActivity)mContext).deleteItem(currentItem);
            });

            itemView.findViewById(R.id.updateButton).setOnClickListener(view -> {
                ((ExchangeRateListActivity)mContext).updateItem(currentItem);
            });
        }
    }
}


