package ru.gushi.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public static final String TAG = ForecastFragment.class.toString();
    private ArrayAdapter<String> mForecastDataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);

        mForecastDataAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast,
                                                  R.id.list_item_forecast_textview,
                                                  new ArrayList<String>());
        refreshForecast();
        forecastListView.setAdapter(mForecastDataAdapter);
        forecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = mForecastDataAdapter.getItem(position);
                final Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                detailIntent.putExtra(Intent.EXTRA_TEXT, item);
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

    private void refreshForecast() {
        new FetchWeatherForecastTask() {
            @Override protected void onPostExecute(String[] result) {
                if (null == result) {
                    final int error = R.string.toast_error_fetch_forecast;
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    return;
                }
                mForecastDataAdapter.setNotifyOnChange(false);
                mForecastDataAdapter.clear();
                for (String dayWeather : result) {
                    mForecastDataAdapter.add(dayWeather);
                }
                mForecastDataAdapter.notifyDataSetChanged();
                mForecastDataAdapter.setNotifyOnChange(true);
            }
        }.execute("94043");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refreshForecast();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }
}

