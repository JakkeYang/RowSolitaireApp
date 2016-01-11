package com.miluhe.rowsolitaireapp.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;

/**
 * Created by jakke on 16-1-12.
 */
public class PlayerAvatar extends Actor {
    private String mName;
    private Texture mTexture;
    public static final int KPlayerTextureX = 128;
    public static final int KPlayerTextureY = 128;

    public PlayerAvatar(String name) {
        mName = name;

        mTexture = SolitaireTextureLoader.instance().load( name );
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw( mTexture, getX(), getY()
                , getOriginX(), getOriginY(), getWidth()
                , getHeight(), getScaleX(), getScaleY());
    }
}
