package com.example.anagrambuster;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.LinearLayout.LayoutParams;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final Map<String,Integer> alphaCodes = new HashMap<>();
    private static JSONObject dictionary = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchView searchView = findViewById( R.id.code );
        searchView.setActivated( true );
        searchView.onActionViewExpanded();
        searchView.setIconified( false );
        searchView.clearFocus();

        boolean flag = true;
        int num = 1;
        char ch = 'a';
        while( alphaCodes.size() < 26 ) {

            num = num + 1;
            int limit = (int) Math.sqrt(num);
            for( int i = 2; i <= limit; i++) {
                if( num % i == 0 && i != num ) {
                    flag = false;
                    break;
                }
                else {
                    flag = true;
                }
            }

               if( flag ){
                   alphaCodes.put(ch+"",num);
                   ch++;
               }

        }
        try {
            dictionary = new JSONObject(loadFastDictionary());
        }
        catch (JSONException exp) {
            exp.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search( query );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Log.e("MainActivity","Error World");
    }

    public void search( String query ){
        Log.e( "MainActivity", alphaCodes.toString());
        try {
            String  val = calculateKey(query);
            System.out.println(val);
            JSONArray result = dictionary.getJSONArray(val);
            createCards(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private  String calculateKey( String anagram) throws Exception {
        double value = anagram.length();
        for ( int i = 0; i < anagram.length(); i++ )
        {
            String key = (anagram.charAt(i)+"").toLowerCase();
            if( alphaCodes.get( key ) == null) {
                throw new Exception("Illegal Word");
            }
            else {
                value = value * alphaCodes.get( key );
            }
        }
        String val = String.valueOf( value );
        val = val.substring(0, val.indexOf("."));
        return val;
    }

    private String loadFastDictionary() {
        String dictionary = null;
        try {
            InputStream is = getAssets().open("fastDictionary.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            dictionary = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return dictionary;
    }

    private void createCards( JSONArray result ) {
        try {
            RecyclerView resultLayout =  findViewById( R.id.results );
            resultLayout.removeAllViews();
            LinearLayoutManager llm = new LinearLayoutManager( getApplicationContext() );
            resultLayout.setLayoutManager( llm );
            ResultsAdapter adapter = new ResultsAdapter( MainActivity.this, result);
            resultLayout.setAdapter( adapter );


//            Arrays.asList(result);
//
//            for ( int i = 0; i<result.length();i++ ) {
//                CardView card = new CardView( getApplicationContext() );
//
//                ViewGroup.LayoutParams params = new LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.WRAP_CONTENT
//                );
//                card.setLayoutParams(params);
//
//                // Set CardView corner radius
//                card.setRadius(9);
//
//                // Set cardView content padding
//                card.setContentPadding(15, 15, 15, 15);
//
//                // Set a background color for CardView
//                card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));
//
//                // Set the CardView maximum elevation
//                card.setMaxCardElevation(15);
//
//                // Set CardView elevation
//                card.setCardElevation(9);
//
//                TextView header = new TextView( this );
//                header.setLayoutParams( params );
//                header.setText( result.getString(i) );
//                header.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
//                card.addView(header);
//
//                resultLayout.addView(card);

    //        }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
