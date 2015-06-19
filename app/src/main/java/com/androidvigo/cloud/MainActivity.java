package com.androidvigo.cloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;


public class MainActivity extends ActionBarActivity
implements GetMemesCallback {

    private ListView mMemesListView;
    private ProgressBar mLoadingProgressBar;

    // FAB-Button
    private ImageButton infoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMemesListView      = (ListView) findViewById(R.id.activity_main_memes_listview);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.activity_main_loading_indicator);
        infoButton = (ImageButton) findViewById(R.id.infoButton);

        // Añadimos una ventana de diálogo al hacer click en el fab button

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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);
        }

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
