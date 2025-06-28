package com.syasya.electricbill_syasya;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView textDetails;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textDetails = findViewById(R.id.textDetails);
        dbHelper = new DatabaseHelper(this);

        int billId = getIntent().getIntExtra("bill_id", -1);
        if (billId != -1) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM bills WHERE id = ?", new String[]{String.valueOf(billId)});
            if (cursor.moveToFirst()) {
                String month = cursor.getString(1);
                int units = cursor.getInt(2);
                double charges = cursor.getDouble(3);
                double rebate = cursor.getDouble(4);
                double finalCost = cursor.getDouble(5);
                textDetails.setText("Month: " + month +
                        "\nUnits: " + units +
                        "\nCharges: RM " + String.format("%.2f", charges) +
                        "\nRebate: " + rebate + "%" +
                        "\nFinal Cost: RM " + String.format("%.2f", finalCost));
            }
            cursor.close();
        }
    }
}
