package com.example.dognutritionapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EducationalContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_content);

        LinearLayout contentLayout = findViewById(R.id.contentLayout);

        // Hardcoded educational content
        addContentItem(contentLayout, "Article: Dog Breeds", "Information about different dog breeds", "https://example.com/dog-breeds");
        addContentItem(contentLayout, "Video: Dog Nutrition", "A video guide on dog nutrition", "https://example.com/dog-nutrition-video");
        addContentItem(contentLayout, "Guide: Puppy Care", "Tips and advice for taking care of puppies", "https://example.com/puppy-care-guide");
        addContentItem(contentLayout, "Article: Senior Dogs", "Nutritional needs of senior dogs", "https://example.com/senior-dogs");
        addContentItem(contentLayout, "Video: Raw Diet for Dogs", "Pros and cons of raw diet for dogs", "https://example.com/raw-diet-video");
        addContentItem(contentLayout, "Guide: Common Health Issues", "Guide on common health issues in dogs", "https://example.com/health-issues-guide");
    }

    private void addContentItem(LinearLayout parent, String title, String description, String url) {
        View contentItem = getLayoutInflater().inflate(R.layout.activity_educational_content, parent, false);

        TextView titleTextView = contentItem.findViewById(R.id.titleTextView);
        TextView descriptionTextView = contentItem.findViewById(R.id.descriptionTextView);
        Button viewButton = contentItem.findViewById(R.id.viewButton);

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        viewButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        parent.addView(contentItem);
    }
}
