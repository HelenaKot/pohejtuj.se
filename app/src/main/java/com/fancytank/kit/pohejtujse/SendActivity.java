package com.fancytank.kit.pohejtujse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fancytank.kit.pohejtujse.api.HateClient;
import com.fancytank.kit.pohejtujse.api.OkActivity;
import com.fancytank.kit.pohejtujse.api.dto.Hate;
import com.fancytank.kit.pohejtujse.api.event.HttpOkEvent;
import com.fancytank.kit.pohejtujse.holder.CameraViewHolder;
import com.fancytank.kit.pohejtujse.holder.RageViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SendActivity extends AppCompatActivity {
    private HateClient hateClient;
    private RageViewHolder rageViewHolder;
    private Hate hate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        hateClient = new HateClient();
        rageViewHolder = new RageViewHolder(findViewById(R.id.rage_container), this);
        hate = getIntent().getExtras().getParcelable(MainActivity.SEND_DATA_BUNDLE_CODE);
        hate.images = new String[]{CameraViewHolder.getImage()};

        View sendBtn = findViewById(R.id.send_container);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg(hate);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HttpOkEvent event) {
        Intent intent = new Intent(this, OkActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void sendMsg(Hate hateMsg) {
        hate.wkurw = rageViewHolder.getRage();
        hateClient.postHate(hateMsg);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                onMessageEvent(null);
            }
        }, 600);
    }

    @Override
    public void onBackPressed() {
        rageViewHolder.unregisterLitener();
        super.onBackPressed();
    }
}
