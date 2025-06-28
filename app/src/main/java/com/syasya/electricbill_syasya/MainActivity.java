package com.syasya.electricbill_syasya;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editTextUnits, editTextRebate;
    Spinner spinnerMonth;
    TextView textViewTotal, textViewFinal;
    Button btnCalculate, btnSave, btnViewList, btnAbout;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextUnits = findViewById(R.id.editTextUnits);
        editTextRebate = findViewById(R.id.editTextRebate);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        textViewTotal = findViewById(R.id.textViewTotal);
        textViewFinal = findViewById(R.id.textViewFinal);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnSave = findViewById(R.id.btnSave);
        btnViewList = findViewById(R.id.btnViewList);
        btnAbout = findViewById(R.id.btnAbout);

        dbHelper = new DatabaseHelper(this);

        // Set up month spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        // Initially disable save button
        btnSave.setEnabled(false);

        // Calculate button logic
        btnCalculate.setOnClickListener(this::onClick);

        // Save button logic
        btnSave.setOnClickListener(view -> {
            String unitsStr = editTextUnits.getText().toString().trim();
            String rebateStr = editTextRebate.getText().toString().trim();

            if (unitsStr.isEmpty()) {
                editTextUnits.setError("Please enter units used");
                return;
            }

            if (rebateStr.isEmpty()) {
                editTextRebate.setError("Please enter rebate (0–5%)");
                return;
            }

            int units = Integer.parseInt(unitsStr);
            double rebate = Double.parseDouble(rebateStr);

            if (rebate < 0 || rebate > 5) {
                editTextRebate.setError("Rebate must be between 0% and 5%");
                return;
            }

            String month = spinnerMonth.getSelectedItem().toString();
            double total = calculateTotalCharges(units);
            double finalCost = total - (total * (rebate / 100));

            // Save to database
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("month", month);
            values.put("units", units);
            values.put("charges", total);
            values.put("rebate", rebate);
            values.put("final_cost", finalCost);

            db.insert("bills", null, values);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        });

        // View list logic
        btnViewList.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BillListActivity.class);
            startActivity(intent);
        });

        // About page logic
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    // Calculate total charges based on block tariff
    private double calculateTotalCharges(int kWh) {
        double total;
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

    // Calculate button action
    private void onClick(View view) {
        String unitsStr = editTextUnits.getText().toString().trim();
        String rebateStr = editTextRebate.getText().toString().trim();

        if (unitsStr.isEmpty()) {
            editTextUnits.setError("Please enter units used");
            return;
        }

        if (rebateStr.isEmpty()) {
            editTextRebate.setError("Please enter rebate (0–5%)");
            return;
        }

        int units = Integer.parseInt(unitsStr);
        double rebate = Double.parseDouble(rebateStr);

        if (rebate < 0 || rebate > 5) {
            editTextRebate.setError("Rebate must be between 0% and 5%");
            return;
        }

        double total = calculateTotalCharges(units);
        double finalCost = total - (total * (rebate / 100));

        textViewTotal.setText(String.format(Locale.US, "Total: RM %.2f", total));
        textViewFinal.setText(String.format(Locale.US, "Final: RM %.2f", finalCost));

        btnSave.setEnabled(true); // enable save after successful calculation
        Toast.makeText(this, "Calculation complete. You may now save.", Toast.LENGTH_SHORT).show();
    }
}
