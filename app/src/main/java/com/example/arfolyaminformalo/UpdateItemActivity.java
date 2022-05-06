package com.example.arfolyaminformalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UpdateItemActivity extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private String id = "";
    private static final String LOG_TAG = UpdateItemActivity.class.getName();

    EditText currencyNameUpdateEditText;
    EditText shortNameUpdateEditText;
    EditText buyPriceUpdateEditText;
    EditText sellPriceUpdateEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        Bundle b = getIntent().getExtras();
        if(b != null){
            id = b.getString("id");
        }

        currencyNameUpdateEditText = findViewById(R.id.currencyNameUpdateEditText);
        shortNameUpdateEditText = findViewById(R.id.shortNameUpdateEditText);
        buyPriceUpdateEditText = findViewById(R.id.buyPriceUpdateEditText);
        sellPriceUpdateEditText = findViewById(R.id.sellPriceUpdateEditText);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");

        DocumentReference ref = mItems.document(id);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                         Map<String, Object> data = document.getData();
                         String name = (String) data.get("name");
                         String shortName = (String) data.get("shortName");
                         String buyPrice = (String) data.get("buyPrice");
                         String sellPrice = (String) data.get("sellPrice");

                        currencyNameUpdateEditText.setText(name);
                        shortNameUpdateEditText.setText(shortName);
                        buyPriceUpdateEditText.setText(buyPrice);
                        sellPriceUpdateEditText.setText(sellPrice);

                    } else {
                        Log.d(LOG_TAG, "No such document");
                    }
                } else {
                    Log.d(LOG_TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public void updateCurrency(View view) {
        String currencyName = currencyNameUpdateEditText.getText().toString();
        String shortName = shortNameUpdateEditText.getText().toString();
        String buyPrice = buyPriceUpdateEditText.getText().toString();
        String sellPrice = sellPriceUpdateEditText.getText().toString();

        mItems.document(id).update("name", currencyName).addOnFailureListener(failure -> {
            Toast.makeText(this, "Item " + id + " cannot be changed.", Toast.LENGTH_LONG).show();
        });
        mItems.document(id).update("shortName", shortName).addOnFailureListener(failure -> {
            Toast.makeText(this, "Item " + id + " cannot be changed.", Toast.LENGTH_LONG).show();
        });
        mItems.document(id).update("buyPrice", buyPrice).addOnFailureListener(failure -> {
            Toast.makeText(this, "Item " + id + " cannot be changed.", Toast.LENGTH_LONG).show();
        });
        mItems.document(id).update("sellPrice", sellPrice).addOnFailureListener(failure -> {
            Toast.makeText(this, "Item " + id + " cannot be changed.", Toast.LENGTH_LONG).show();
        });
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}