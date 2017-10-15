package com.fancytank.kit.pohejtujse;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fancytank.kit.pohejtujse.api.HateClient;
import com.fancytank.kit.pohejtujse.api.dto.Hate;
import com.fancytank.kit.pohejtujse.holder.CameraViewHolder;

public class SendActivity extends AppCompatActivity {
    private HateClient hateClient;
    private Hate hate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        hateClient = new HateClient();
        hate = (Hate) getIntent().getExtras().getParcelable(MainActivity.SEND_DATA_BUNDLE_CODE);
        hate.images = new String[] {CameraViewHolder.getImage()};

        View sendBtn = findViewById(R.id.send_container);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg(hate);
            }
        });
    }

    private void sendMsg(Hate hateMsg) {
        hateClient.postHate(hateMsg);
    }

}
