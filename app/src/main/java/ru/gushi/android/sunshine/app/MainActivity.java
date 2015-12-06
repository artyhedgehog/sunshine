package ru.gushi.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_map:
                showPreferredLocationOnMap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPreferredLocationOnMap() {
        final String preferredLocation = Settings.getStringSetting(R.string.pref_location_key,
                                                                   R.string.pref_location_default,
                                                                   this);
        final Uri uri = new Uri.Builder().scheme("geo").path("0,0")
                                         .appendQueryParameter("q", preferredLocation)
                                         .build();

        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (null != intent.resolveActivity(getPackageManager())) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, String.format("Could not find receiver for uri: %s", uri.toString()));
        }
    }

}
