package com.miluhe.rowsolitaireapp;

import com.badlogic.gdx.Game;

/**
 * Created by jakke on 15-12-29.
 */
public class SolitaireGame extends Game {

    @Override
    public void create() {
        PlayPokerScreen playScreen = new PlayPokerScreen();
        this.setScreen(playScreen);
    }


}