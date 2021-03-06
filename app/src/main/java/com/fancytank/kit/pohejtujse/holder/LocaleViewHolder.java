package com.fancytank.kit.pohejtujse.holder;

import android.view.View;
import android.widget.TextView;

import com.fancytank.kit.pohejtujse.R;
import com.fancytank.kit.pohejtujse.api.dto.Coordinates;

// 10/14/2017.
public class LocaleViewHolder {
    private TextView localeTextView;
    private double x = 0, y = 0;

    public interface LocaleClickedListener {
        void tryUpdateLocale();
    }

    public LocaleViewHolder(View localeView, final LocaleClickedListener listener) {
        localeTextView = localeView.findViewById(R.id.tv_locale);
        listener.tryUpdateLocale();
    }

    public void setLocale(double x, double y) {
        this.x = x;
        this.y = y;

        localeTextView.setVisibility(View.VISIBLE);
        localeTextView.setText(localeTextView.getContext().getString(R.string.locale_string, String.valueOf(x), String.valueOf(y)));
    }

    public Coordinates getCoordinates() {
        return new Coordinates(x, y);
    }

}
