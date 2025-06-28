package com.syasya.electricbill_syasya;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextUnits, editTextRebate;
    Spinner spinnerMonth;
    TextView textViewTotal, textViewFinal;
    Button btnCalculate, btnSave, btnViewList;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUnits = findViewById(R.id.editTextUnits);
        editTextRebate = findViewById(R.id.editTextRebate);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        textViewTotal = findViewById(R.id.textViewTotal);
        textViewFinal = findViewById(R.id.textViewFinal);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnSave = findViewById(R.id.btnSave);
        btnViewList = findViewById(R.id.btnViewList);

        dbHelper = new DatabaseHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        btnCalculate.setOnClickListener(view -> {
            int units = Integer.parseInt(editTextUnits.getText().toString());
            double rebate = Double.parseDouble(editTextRebate.getText().toString());

            double total = calculateTotalCharges(units);
            double finalCost = total - (total * (rebate / 100));

            textViewTotal.setText(String.format("Total: RM %.2f", total));
            textViewFinal.setText(String.format("Final: RM %.2f", finalCost));
        });

        btnSave.setOnClickListener(view -> {
            String month = spinnerMonth.getSelectedItem().toString();
            int units = Integer.parseInt(editTextUnits.getText().toString());
            double rebate = Double.parseDouble(editTextRebate.getText().toString());
            double total = calculateTotalCharges(units);
            double finalCost = total - (total * (rebate / 100));

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("month", month);
            values.put("units", units);
            values.put("charges", total);
            values.put("rebate", rebate);
            values.put("final_cost", finalCost);

            long newRowId = db.insert("bills", null, values);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        });

        btnViewList.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BillListActivity.class);
            startActivity(intent);
        });
    }

    private double calculateTotalCharges(int kWh) {
        double total = 0;
        if (kWh <= 200) {
            total = kWh * 0.218;
        } else if (kWh <= 300) {
            total = 200 * 0.218 + (kWh - 200) * 0.334;
        } else if (kWh <= 600) {
            total = 200 * 0.218 + 100 * 0.334 + (kWh - 300) * 0.516;
        } else {
            total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (kWh - 600) * 0.546;
        }
        return total;
    }
}