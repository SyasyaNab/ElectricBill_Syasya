package com.syasya.electricbill_syasya;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BillListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> displayList;
    ArrayList<Integer> idList;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);
        displayList = new ArrayList<>();
        idList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, month, final_cost FROM bills", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String month = cursor.getString(1);
                double finalCost = cursor.getDouble(2);
                displayList.add(month + " - RM " + String.format("%.2f", finalCost));
                idList.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            int billId = idList.get(position);
            Intent intent = new Intent(BillListActivity.this, DetailActivity.class);
            intent.putExtra("bill_id", billId);
            startActivity(intent);
        });
    }
}