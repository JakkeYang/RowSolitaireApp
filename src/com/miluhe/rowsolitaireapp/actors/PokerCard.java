package com.miluhe.rowsolitaireapp.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by jakke on 15-12-29.
 */
public class PokerCard extends Actor {
    // Spade
    public static final int KSpades = 1;

    // Heart
    public static final int KHearts = 2;

    // Club
    public static final int KClubs = 3;

    // Diamond
    public static final int KDiamonds = 4;

    public static final int KWidth = 120;
    public static final int KHeight = 160;

    private int mValue = 0;
    private TextureRegion mTextureRegion;
    private float mPositionX = .0f;
    private float mPositionY = .0f;

    public PokerCard( int pokerValue ) {
        mValue = pokerValue;

        String fileName = "poker/" + mValue + ".jpg";
        Texture texture = new Texture( Gdx.files.internal( fileName ) );
        mTextureRegion = new TextureRegion( texture, 0, 0, 169, 256 );
    }

    public int getmValue() {
        return mValue;
    }

    public void setmValue(int mValue) {
        this.mValue = mValue;
    }

    public void setPosition( float x, float y ) {
        mPositionX = x;
        mPositionY = y;
    }

    @Override
    public void draw( SpriteBatch batch, float parentAlpha ) {
        super.draw(batch, parentAlpha);

        batch.draw( mTextureRegion, mPositionX, mPositionY, KWidth, KHeight );
    }

    /**
     * 为将来动画做好准备
     */
    private void update() {

    }

    public void show() {

    }
}