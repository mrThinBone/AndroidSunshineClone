package com.example.android.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {

    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /* Remember that these views are reused as needed.*/
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = (viewType == VIEW_TYPE_TODAY) ? R.layout.list_item_forecast_today : R.layout.list_item_forecast;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.
        boolean isMetric = Utility.isMetric(context);
        viewHolder.iconView.setImageResource(R.drawable.ic_launcher);
        viewHolder.tvDate.setText(Utility.getFriendlyDayString(context, cursor.getLong(ForecastFragment.COL_WEATHER_DATE)));
        viewHolder.tvForecast.setText(cursor.getString(ForecastFragment.COL_WEATHER_DESC));
        viewHolder.tvHighTemp.setText(Utility.formatTemperature(cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP), isMetric));
        viewHolder.tvLowTemp.setText(Utility.formatTemperature(cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP), isMetric));
    }

    static class ViewHolder {
        public final ImageView iconView;
        public final TextView tvDate;
        public final TextView tvForecast;
        public final TextView tvHighTemp;
        public final TextView tvLowTemp;

        public ViewHolder(View root) {
            iconView = (ImageView) root.findViewById(R.id.list_item_icon);
            tvDate = (TextView) root.findViewById(R.id.list_item_date_textview);
            tvForecast = (TextView) root.findViewById(R.id.list_item_forecast_textview);
            tvHighTemp = (TextView) root.findViewById(R.id.list_item_high_textview);
            tvLowTemp = (TextView) root.findViewById(R.id.list_item_low_textview);
        }
    }
}
