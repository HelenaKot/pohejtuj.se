package com.fancytank.kit.pohejtujse.api.locale;

import android.view.View;
import android.widget.EditText;

import com.fancytank.kit.pohejtujse.R;

// 10/14/2017.
public class TextViewHolder {
    private EditText editText;

    public TextViewHolder(final View localeView) {
        editText = localeView.findViewById(R.id.tv_text);
        localeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localeView.setOnClickListener(null);
                updateView();
            }
        });
    }

    private void updateView() {
        editText.setVisibility(View.VISIBLE);
    }

    public String getText() {
        return editText.getText().toString();
    }

}
