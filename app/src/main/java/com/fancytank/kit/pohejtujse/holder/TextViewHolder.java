package com.fancytank.kit.pohejtujse.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.fancytank.kit.pohejtujse.R;

// 10/14/2017.
public class TextViewHolder {
    private EditText editText;
    private final TextListener listenr;

    public interface TextListener {
        void onTextChanged(boolean notEmpty);
    }

    public TextViewHolder(final View localeView, TextListener textListener) {
        this.listenr = textListener;
        editText = localeView.findViewById(R.id.tv_text);
        localeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localeView.setOnClickListener(null);
                updateView();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listenr.onTextChanged(editable.length() > 0);
            }
        });
    }

    //TODO on enter done

    private void updateView() {
        editText.setVisibility(View.VISIBLE);
    }

    public String getText() {
        return editText.getText().toString();
    }

}
