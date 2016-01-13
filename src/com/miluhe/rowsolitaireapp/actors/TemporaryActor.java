package com.miluhe.rowsolitaireapp.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;

public class TemporaryActor extends Actor {
	private String mPassby;
	private Texture mTexture;

    public TemporaryActor(String aPassBy) {
    	mPassby = aPassBy;

        mTexture = SolitaireTextureLoader.instance().load( mPassby );
        this.setWidth(mTexture.getWidth());
        this.setHeight(mTexture.getHeight());
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
    	batch.draw(mTexture, getX(), getY());
    }
}
