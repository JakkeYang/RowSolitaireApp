package com.miluhe.rowsolitaireapp;

import com.badlogic.gdx.Game;

/**
 * Created by jakke on 15-12-29.
 */
public class SolitaireGame extends Game {

	PlayPokerScreen mPlayScreen;
	
    @Override
    public void create() {
    	mPlayScreen = new PlayPokerScreen();
        this.setScreen(mPlayScreen);
    }

    @Override
    public void dispose() {
    	mPlayScreen.dispose();
    	SolitaireTextureLoader.instance().dispose();
    }

}