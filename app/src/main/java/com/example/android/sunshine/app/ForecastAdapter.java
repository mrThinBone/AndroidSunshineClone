package com.example.android.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {
    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.
        TextView tvDate = (TextView) view.findViewById(R.id.list_item_date_textview);
        TextView tvForecast = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        TextView tvHighTemp = (TextView) view.findViewById(R.id.list_item_high_textview);
        TextView tvLowTemp = (TextView) view.findViewById(R.id.list_item_low_textview);

        boolean isMetric = Utility.isMetric(context);
        tvDate.setText(Utility.getFriendlyDayString(context, cursor.getLong(ForecastFragment.COL_WEATHER_DATE)));
        tvForecast.setText(cursor.getString(ForecastFragment.COL_WEATHER_DESC));
        tvHighTemp.setText(Utility.formatTemperature(cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP), isMetric));
        tvLowTemp.setText(Utility.formatTemperature(cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP), isMetric));
    }
}
