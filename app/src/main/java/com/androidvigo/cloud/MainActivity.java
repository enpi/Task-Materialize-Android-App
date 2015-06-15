package com.androidvigo.cloud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;


public class MainActivity extends Activity
implements GetMemesCallback {

    private ListView mMemesListView;
    private ProgressBar mLoadingProgressBar;
    private ImageButton infoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMemesListView      = (ListView) findViewById(R.id.activity_main_memes_listview);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.activity_main_loading_indicator);
        infoButton = (ImageButton) findViewById(R.id.infoButton);

        infoButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Materializado por Hans-Manuel Grenner Noguerón y Julio Martinez Martinez-Checa.")
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create().show();

            }
        });

    }

    public void startGetMemesRequest(View requestButton) {

        requestButton.setVisibility(View.GONE);
        mLoadingProgressBar.setVisibility(View.VISIBLE);

        GetMemesHelper.getInstance().loadMemesWithIon(this, this);
    }

    @Override
    public void onMemesResult(List<MemeEntity> memesList) {

        mLoadingProgressBar.setVisibility(View.GONE);

        String [] memesNames = new String[memesList.size()];

        for (int i = 0; i < memesList.size(); i++)
            memesNames[i] = memesList.get(i).getTitle();

        MemeAdapter memesAdapter = new MemeAdapter(this, memesList);

        mMemesListView.setAdapter(memesAdapter);
    }

    @Override
    public void onMemesError() {

    }
}
