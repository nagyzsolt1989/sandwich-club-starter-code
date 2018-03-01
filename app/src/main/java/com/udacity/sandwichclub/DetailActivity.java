package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.databinding.DataBindingUtil;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ActivityDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        TextView mAlsoKnown = findViewById(R.id.also_known_tv);
        TextView mIngredients = findViewById(R.id.ingredients_tv);
        TextView mDescription = findViewById(R.id.description_tv);
        TextView mOrigin = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        } else {
            boolean first = true;
            StringBuilder aliases = new StringBuilder();
            for (String alias : sandwich.getAlsoKnownAs()) {
                if (first) {
                    aliases.append(alias);
                    first = false;
                }else {
                    aliases.append("\n" + alias);
                }
            }
            first = true;
            mAlsoKnown.setText(aliases.toString());
            StringBuilder ingredients = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {
                if (first) {
                    ingredients.append(ingredient);
                    first = false;
                }else {
                    ingredients.append("\n" + ingredient);
                }
            }
            mIngredients.setText(ingredients.toString());
            mDescription.setText(sandwich.getDescription());
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        }


        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
