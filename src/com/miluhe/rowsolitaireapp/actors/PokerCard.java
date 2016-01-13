package com.miluhe.rowsolitaireapp.actors;

import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.util.TypedValue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;

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

    public enum TCardStatus {
        EAvailable,
        EReadyToPlay,
        EPassed,
        EPlayed
    }

    public static final int KCardTextureX = 180;
    public static final int KCardTextureY = 250;
    public static final int KGapH = 7;
    public static final int KGapW = 23;

    private int mValue = 0;
    private TCardStatus mStatus = TCardStatus.EAvailable;
    private TextureRegion mTextureRegion;
    private float mPositionX = .0f;
    private float mPositionY = .0f;
    private float mSizeX = .0f;
    private float mSizeY = .0f;
    private static float mCardW = 0;
    private static float mCardH = 0;

    static {
    	try {
	    	TypedValue v = new TypedValue();
	    	SolitaireApplication.getContextObject()
	    			.getResources().getValue(R.dimen.cardx, v, true);
	    	
	    	mCardW = v.getFloat();
	    	SolitaireApplication.getContextObject()
	    			.getResources().getValue(R.dimen.cardy, v, true);
	    	mCardH = v.getFloat();
    	} catch (NotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    public PokerCard( int pokerValue ) {
        mValue = pokerValue;
        int suit = (mValue % 13 == 0)? mValue / 13 - 1 : mValue / 13;
        int value = (mValue % 13 == 0)? 13 : mValue % 13;

        Texture texture =
            SolitaireTextureLoader.instance().load(SolitaireTextureLoader.KPokerTexture);

        mTextureRegion = new TextureRegion( texture
        		, (value - 1)*( KCardTextureX + KGapW)
        		, suit * (KCardTextureY + KGapH)
        		, KCardTextureX, KCardTextureY );
    }

    public int getmValue() {
        return mValue;
    }

    public void setmValue(int mValue) {
        this.mValue = mValue;
    }

    public TCardStatus getmStatus() {
        return mStatus;
    }

    public void setmStatus(TCardStatus mStatus) {
        this.mStatus = mStatus;
    }

    public void setPosition( float x, float y ) {
        mPositionX = x;
        mPositionY = y;
        setBounds(x, y, mCardW, mCardH);
    }

    public void setSize( Vector2 size) {
        mSizeX = size.x;
        mSizeY = size.y;
    }

    public void getSize( Vector2 size ) {
        size.x = mSizeX;
        size.y = mSizeY;
    }

    @Override
    public void draw( SpriteBatch batch, float parentAlpha ) {
        super.draw(batch, parentAlpha);

        batch.draw( mTextureRegion, getX(), getY()
                , getOriginX(), getOriginY(), getWidth()
                , getHeight(), getScaleX(), getScaleY()
                , getRotation());
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
    	if (mStatus != TCardStatus.EAvailable 
    			&& mStatus != TCardStatus.EReadyToPlay )
    		return null;
    	
        if ( x > mPositionX && x <=mPositionX + mSizeX
                && y > mPositionY && y <= mPositionY + mSizeY ) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * future animation
     */
    private void update() {

    }

    public void show() {

    }
}