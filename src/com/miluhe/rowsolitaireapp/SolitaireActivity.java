package com.miluhe.rowsolitaireapp;

import utils.SaveUtils;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SolitaireActivity extends AndroidApplication {
	
	@Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        initialize( new SolitaireGame(), false );
        
        WindowManager wm = (WindowManager) this
        		.getSystemService(Context.WINDOW_SERVICE);

        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);

    	SaveUtils.setScreenSize( screenSize );
	}

}
