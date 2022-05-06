package com.example.arfolyaminformalo;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Currency;

public class ExchangeRateListActivity extends AppCompatActivity {
    private static final String LOG_TAG = ExchangeRateListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private TextView sourceTextView;

    private RecyclerView mRecyclerView;
    private ArrayList<CurrencyItem> mItemList;
    private CurrencyItemAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private FrameLayout redCircle;
    private TextView contentTextView;

    private NotificationHandler mNotificationHandler;

    private int grindNumber = 1;
    private int subscriptionCount = 0;
    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate_list);
        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, grindNumber));
        mItemList = new ArrayList<>();

        mAdapter = new CurrencyItemAdapter(this, mItemList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");

        queryData();

        mNotificationHandler = new NotificationHandler(this);

    }

    private void queryData() {
        mItemList.clear();

        mItems.orderBy("subscribed", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    CurrencyItem item = document.toObject(CurrencyItem.class);
                    item.setId(document.getId());
                    mItemList.add(item);
                }
                if (mItemList.size() == 0) {
                    initalizeData();
                    queryData();
                }

                mAdapter.notifyDataSetChanged();
            }
        });


    }

    public void deleteItem(CurrencyItem item) {
        DocumentReference ref = mItems.document(item._getId());

        ref.delete().addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "Item is deleted: " + item._getId());
        }).addOnFailureListener(failure -> {
            Toast.makeText(this, "Item " + item._getId() + " cannot be deleted.", Toast.LENGTH_LONG).show();
        });

        queryData();
        mNotificationHandler.cancel();
    }


    private void initalizeData() {
        String[] itemsName = getResources().getStringArray(R.array.currency_item_names);
        String[] itemsShortName = getResources().getStringArray(R.array.curreny_item_shortnames);
        String[] itemsBuyPrice = getResources().getStringArray(R.array.currency_item_buyprice);
        String[] itemsSellPrice = getResources().getStringArray(R.array.currency_item_sellprice);
        TypedArray itemsImage = getResources().obtainTypedArray(R.array.currency_item_images);
        TypedArray itemsChartImage = getResources().obtainTypedArray(R.array.currency_item_chartimages);


        for (int i = 0; i < itemsName.length; i++) {
            mItems.add(new CurrencyItem(itemsName[i],
                    itemsShortName[i],
                    itemsBuyPrice[i],
                    itemsSellPrice[i],
                    itemsImage.getResourceId(i, 0),
                    itemsChartImage.getResourceId(i, 0),
                    false));
        }

        itemsImage.recycle();
        itemsChartImage.recycle();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.currency_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button:
                Log.d(LOG_TAG, "Log out clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.setting_button:
                Log.d(LOG_TAG, "Settings clicked!");
                return true;
            case R.id.source_button:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.napiarfolyam.hu"));
                startActivity(browserIntent);
                return true;
            case R.id.subcriptions:
                Log.d(LOG_TAG, "Subcriptionst clicked!");
                return true;
            case R.id.view_selector:
                Log.d(LOG_TAG, "View selector clicked!");
                if (viewRow) {
                    changeSpanCount(item, R.drawable.ic_view_grid, 1);
                } else {
                    changeSpanCount(item, R.drawable.ic_view, 2);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.subcriptions);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        contentTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(alertMenuItem);
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon(CurrencyItem item) {
        if(!item.isSubscribed()){
            subscriptionCount = (subscriptionCount + 1);
        }
        if (0 < subscriptionCount) {
            contentTextView.setText(String.valueOf(subscriptionCount));
        } else {
            contentTextView.setText("");
        }

        redCircle.setVisibility((subscriptionCount > 0) ? VISIBLE : GONE);

        mItems.document(item._getId()).update("subscribed", true).addOnFailureListener(failure -> {
            Toast.makeText(this, "Item " + item._getId() + " cannot be changed.", Toast.LENGTH_LONG).show();
        });

        mNotificationHandler.send(item.getName());
        queryData();
    }
}