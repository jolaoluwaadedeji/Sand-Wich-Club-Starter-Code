package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private  static String TAG = "Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Log.i(TAG,"jsonString:"+json);
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }
            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }
        catch (JSONException e){
            Log.e(TAG, "parseJsonArrayToList:"+e.getMessage());
        }
        catch (Exception ex){
            Log.e(TAG, "errorOccurred:"+ex.getMessage());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alias = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredient = findViewById(R.id.ingredients_tv);

        String aliases = null;
        String ingredients = null;
        StringBuilder aliasBuilder = new StringBuilder();
        StringBuilder ingredientBuilder = new StringBuilder();
        for (String item : sandwich.getAlsoKnownAs()
             ) {
            aliasBuilder.append(item+',');
        }
        for (String value : sandwich.getIngredients()
                ) {
            ingredientBuilder.append(value+',');
        }
        aliases = aliasBuilder.toString();
        ingredients = ingredientBuilder.toString();
        alias.setText(aliases);
        origin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
        ingredient.setText(ingredients);
    }
}
