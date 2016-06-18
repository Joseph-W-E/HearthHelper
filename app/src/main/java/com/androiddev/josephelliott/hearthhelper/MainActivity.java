package com.androiddev.josephelliott.hearthhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.androiddev.josephelliott.hearthhelper.AllCardsActivity.AllCardsActivity;
import com.androiddev.josephelliott.hearthhelper.ChallengeActivity.ChallengeActivity;
import com.androiddev.josephelliott.hearthhelper.DeckBuilderActivity.DeckBuilderActivity;
import com.androiddev.josephelliott.hearthhelper.WinTrackerActivity.WinTrackerActivity;


/**
 * Created by JoeyElliott on 6/17/2016.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_base_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnAllCardsActivity = (Button) findViewById(R.id.btn_all_cards_activity);
        btnAllCardsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(AllCardsActivity.class);
            }
        });
        Button btnChallengeActivity = (Button) findViewById(R.id.btn_challenge_activity);
        btnChallengeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(ChallengeActivity.class);
            }
        });
        Button btnDeckBuilderActivity = (Button) findViewById(R.id.btn_deck_builder_activity);
        btnDeckBuilderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(DeckBuilderActivity.class);
            }
        });
        Button btnWinTrackerActivity = (Button) findViewById(R.id.btn_win_tracker_activity);
        btnWinTrackerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(WinTrackerActivity.class);
            }
        });
    }

    private void startNewActivity(Class activity) {
        startActivity(new Intent(this, activity));
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
