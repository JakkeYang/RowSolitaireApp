package com.miluhe.rowsolitaireapp;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SolitaireActivity extends AndroidApplication {
	
	@Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        initialize( new SolitaireGame(), false );
	}

}
