package com.example.arfolyaminformalo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateItemActivity extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    EditText currencyNameEditText;
    EditText shortNameEditText;
    EditText buyPriceEditText;
    EditText sellPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        currencyNameEditText = findViewById(R.id.currencyNameEditText);
        shortNameEditText = findViewById(R.id.shortNameEditText);
        buyPriceEditText = findViewById(R.id.buyPriceEditText);
        sellPriceEditText = findViewById(R.id.sellPriceEditText);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");


    }


    public void createCurrency(View view) {
        String currencyName = currencyNameEditText.getText().toString();
        String shortName = shortNameEditText.getText().toString();
        String buyPrice = buyPriceEditText.getText().toString();
        String sellPrice = sellPriceEditText.getText().toString();

        mItems.add(new CurrencyItem(currencyName,
                shortName,
                buyPrice,
                sellPrice
        ));
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}