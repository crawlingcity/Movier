package com.crawlingcity.movier;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crawlingcity.movier.Fragment.MovieFragmentBuilder;

import java.util.Locale;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preencherListaFilmes();
        }

    private void preencherListaFilmes() {

        String endpoint=getString(R.string.pref_units_default);

        String language;
        if (getString(R.string.pref_units_default) !=Locale.getDefault().getLanguage()){
            language=Locale.getDefault().getLanguage();
        } else {
            language=getString(R.string.pref_units_default);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditr = prefs.edit();
        prefsEditr.putString("language", language);
        prefsEditr.commit();

        Fragment fragment = new MovieFragmentBuilder(endpoint,language).build();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.teste, fragment, "fragmentCrawling")
                .commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_grid) {

        } else if (id == R.id.nav_list) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            MovieFragment fragmentDemo = MovieFragment.newInstance("upcoming","pt");
//            ft.replace(R.id.teste, fragmentDemo);
//            ft.commit();

        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent(this,SettingsActivity.class);
            startActivityForResult(intent,2);

        } else if (id == R.id.nav_author) {
            Toast.makeText(getApplicationContext(),"Opening Facebook",Toast.LENGTH_LONG).show();
            openFacebookURL("http://www.facebook.com/crawlingcity");
        } else if (id == R.id.nav_mail) {
            sendEmail();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            actualizarPrefs();
        }
    }

    protected  void actualizarPrefs(){
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String units = sharedPrefs.getString("units", getString(R.string.pref_units_key));
        String language = sharedPrefs.getString("language", getString(R.string.pref_language_key));

        Fragment fragmenta = new MovieFragmentBuilder(units,language).build();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.teste, fragmenta, "fragmentCrawling")
                .commit();
    }

    protected void openFacebookURL(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/crawlingcity")));
        }
    }

    protected void sendEmail() {
        //Log.i("Send email", "");

        String[] TO = {"crawlingcity@gmail.com"};
        //String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Movier feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Movier feedback by:");


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}