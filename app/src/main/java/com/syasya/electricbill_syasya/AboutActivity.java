package com.syasya.electricbill_syasya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView textView = findViewById(R.id.aboutText);

        String info = "Name: Syasya Nabilah Binti Syalihuddin\n" +
                "Student ID: 2023697608\n" +
                "Course: ICT602 Mobile Technology And Development\n" +
                "© 2025\n" +
                "Click here to visit the GitHub Repository";

        SpannableString spannableString = getSpannableString(info);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @NonNull
    private SpannableString getSpannableString(String info) {
        SpannableString spannableString = new SpannableString(info);

        // Make only the link text clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SyasyaNab/ElectricBill_Syasya"));
                startActivity(browserIntent);
            }
        };

        // Set span for the last line (from "Click here..." onward)
        int start = info.indexOf("Click here");
        int end = info.length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
