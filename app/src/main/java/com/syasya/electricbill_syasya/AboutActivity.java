package com.syasya.electricbill_syasya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView textView = findViewById(R.id.aboutText);
        textView.setText("Name: Syasya Nabilah Binti Syalihuddin\n" +
                "ID: YOUR_STUDENT_ID\n" +
                "Course: Mobile Technology\n" +
                "© 2025\n" +
                "GitHub: https://github.com/yourusername/yourapp\n");

        textView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/yourusername/yourapp"));
            startActivity(browserIntent);
        });
    }
}
