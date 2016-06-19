package com.androiddev.josephelliott.hearthhelper.ActivityAllCards;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Interfaces.HearthstoneAPIEndPointInterface;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.Card;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.CardSetWrapper;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Storage.CardDataSource;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Utility.CardViewAdapter;
import com.androiddev.josephelliott.hearthhelper.R;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllCardsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_cards_activity_base_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // If this is the first time the user has opened the app, download the cards
        final GridView gridView = (GridView) findViewById(R.id.all_cards_gridview);
        ArrayList<Card> cards = new ArrayList<Card>();

        // Check if the user has already retrieved the list of cards
        CardDataSource dataSource = new CardDataSource(this);
        boolean cardsAlreadyExist = false;
        try {
            dataSource.open();
            cardsAlreadyExist = dataSource.hasCardsTable();
        } catch (SQLException e) {
            Toast.makeText(this, "Failed to open datasource.", Toast.LENGTH_LONG).show();
        } finally {
            dataSource.close();
        }

        if (cardsAlreadyExist) {
            // Get all the cards from JSON, will update grid view on success
            Toast.makeText(this, "First time? Downloading all cards.", Toast.LENGTH_LONG).show();
            cards = JSONCallToGetAllCards(gridView, this);
        } else {
            // Get all the cards from Local Storage, will update grid view manually
            cards = new ArrayList<>();
            updateGridViewAdapter(gridView, cards, this);
        }

    }

    private static void updateGridViewAdapter(final GridView gridView, ArrayList<Card> cards, Context context) {
        gridView.setAdapter(new CardViewAdapter(context, cards));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardViewAdapter adapter = (CardViewAdapter) gridView.getAdapter();
                Toast.makeText(parent.getContext(),
                        adapter.getCards().get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Card> JSONCallToGetAllCards(final GridView gridView, final Context context) {
        // List of cards to return
        final ArrayList<Card> cards = new ArrayList<>();

        // Make the API call
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HearthstoneAPIEndPointInterface.URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HearthstoneAPIEndPointInterface hAPI = retrofit.create(HearthstoneAPIEndPointInterface.class);
        Call<CardSetWrapper> call = hAPI.loadCardSets();
        call.enqueue(new Callback<CardSetWrapper>() {
            @Override
            public void onResponse(Call<CardSetWrapper> call, Response<CardSetWrapper> response) {
                if (response.isSuccessful()) {
                    // Get the list of cards from the CardSetWrapper
                    cards.addAll(response.body().getAllCards());
                    // Update the database so the user doesn't have to do this again
                    CardDataSource dataSource = new CardDataSource(context);
                    try {
                        dataSource.open();
                        for (Card card : cards) {
                            dataSource.createCard(card);
                        }
                        dataSource.close();
                    } catch (SQLException | JSONException e) {
                        e.printStackTrace();
                    }
                    // Update our grid view with the new list of cards
                    updateGridViewAdapter(gridView, cards, context);
                } else {
                    Toast.makeText(AllCardsActivity.this, "Unsuccessful callback",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CardSetWrapper> call, Throwable t) {
                Toast.makeText(AllCardsActivity.this, "Failure. " + t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return cards;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all_cards) {

        } else if (id == R.id.nav_deck_builder) {

        } else if (id == R.id.nav_tournament) {

        } else if (id == R.id.nav_win_tracker) {

        } else if (id == R.id.nav_share_decks) {

        } else if (id == R.id.nav_share_cards) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
