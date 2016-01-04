package com.miluhe.rowsolitaireapp.stages;

import java.util.ArrayList;
import java.util.List;

import utils.SaveUtils;
import android.content.res.Resources.NotFoundException;
import android.graphics.Point;
import android.util.TypedValue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;
import com.miluhe.rowsolitaireapp.actors.PokerCard;
import com.miluhe.rowsolitaireapp.actors.PokerCard.TCardStatus;

/**
 * Created by jakke on 15-12-29.
 */
public class PokerStage extends Stage {
    private Image mBackgroundImage;
    private int mHeight;
    private int mWidth;
    private LogicHelper helper;
    private int[] mCardsA = new int[17];
    private int[] mCardsB = new int[17];
    private int[] mCardsM = new int[17];
    private static int mCardOffset = 0;
    private static float mCardW = 0;
    private static float mCardH = 0;
    private Point mScreenSize;
    private float mStartX;
    private float mStartY;
    private float mPlayedCardStartX;
    private float mPlayedCardStartY;
    private int mMarcoCardLastIndex = 16;
    private static final float KScale = 0.75f;

    List<PokerCard> mListActors = new ArrayList<PokerCard>();

    static {
        mCardOffset = SolitaireApplication.getContextObject()
				.getResources().getInteger(R.dimen.cardoffset);
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
    
    public PokerStage() {
        Texture bgTexture =
        		SolitaireTextureLoader.instance().load(SolitaireTextureLoader.KPokerTable);
        mBackgroundImage = new Image( bgTexture );

        this.addActor( mBackgroundImage );
        helper = new LogicHelper();

        // FIXME
        //  Need to display who's the first player, spade 7
        helper.startTable();
        mCardsA = helper.showAlphaCards();
        mCardsB = helper.showBelleCards();
        mCardsM = helper.showMarcoCards();
        mMarcoCardLastIndex = mCardsM.length - 1;
        mScreenSize = new Point();

        SaveUtils.getScreenSize(mScreenSize);
        mStartX = (mScreenSize.x - 16*mCardOffset - mCardW) / 2;
        mStartY = 100.0f;
        mPlayedCardStartX = (mScreenSize.x - 4*KScale*mCardW - 3*mCardOffset) / 2;
        mPlayedCardStartY = 300.f + mCardH;

        float x = mStartX;
        float y = mStartY;
        int i = 0;

        for ( int value : mCardsM ) {
            PokerCard poker = new PokerCard( value );
            poker.setPosition( x, y );
            if ( i == mCardsM.length - 1 ) {
                poker.setSize( new Vector2(mCardW, mCardH ));
            } else {
                poker.setSize( new Vector2(mCardOffset, mCardH) );
            }

            x += mCardOffset;
            ++i;
            this.addActor( poker );
            mListActors.add(poker);
        }

    }

    public void setRegion( int w, int h ) {
        mWidth = w;
        mHeight = h;

        mBackgroundImage.setSize( mWidth, mHeight );
    }

    private void refreshMarcoCardsSizes( int playedIndex ) {
        if ( playedIndex > 0 && playedIndex < mMarcoCardLastIndex ) {
            // play card in middle, fresh previous card's size + played card's width
            Vector2 prevSize = new Vector2();
            Vector2 currSize = new Vector2();

            int i = playedIndex - 1;
            for (; i >= 0; --i ) {
                // find previous un-played or un-passed card
                if (mListActors.get(i).getmStatus() == PokerCard.TCardStatus.EAvailable
                		|| mListActors.get(i).getmStatus() == PokerCard.TCardStatus.EReadyToPlay ) {
                    mListActors.get(i).getSize( prevSize );
                    break;
                }
            }

            if ( -1 == i ) {
                // not found
                return;
            }
            mListActors.get(playedIndex).getSize( currSize );

            float tmpSizeW = prevSize.x + currSize.x;
            if ( tmpSizeW > mCardW ) {
                tmpSizeW = mCardW;
            }
            mListActors.get(i).setSize(new Vector2(tmpSizeW, mCardH));
        } else if ( playedIndex == mMarcoCardLastIndex ) {
            // play card in the last, fresh previous card's size to card width
            if (playedIndex == 0) {
                return;
            }
            mListActors.get(playedIndex-1).setSize(new Vector2(mCardW, mCardH));
        } else {
            // do nothing
        }
    }

    private void moveToPosition( int cardValue, Vector2 dest ) {
        int suit = (cardValue % 13 == 0)? cardValue / 13 - 1 : cardValue / 13;
        int value = (cardValue % 13 == 0)? 13 : cardValue % 13;

        dest.x = suit * ( KScale * mCardW + mCardOffset ) + mPlayedCardStartX;
        dest.y = ( 13 - value ) * mCardOffset + mPlayedCardStartY;

    }

    // FIXME
    //  need to add pass logic
    private void showAlphaCard() {
        int cardValue = helper.getAlphaCard();
        PokerCard poker = new PokerCard( cardValue );
        Vector2 pos = new Vector2();

        poker.setBounds( .0f, mScreenSize.y - KScale*mCardH, KScale*mCardW, KScale*mCardH);
        this.addActor( poker );

        moveToPosition( cardValue, pos );
        MoveToAction moveTo = Actions.moveTo( pos.x, pos.y, 1 );
        ScaleByAction scaleBy = Actions.scaleBy( -0.25f, -0.25f, 1 );
        ParallelAction actions = Actions.parallel( moveTo, scaleBy );
        poker.addAction(actions);
    }

    private void showBelleCard() {
        int cardValue = helper.getBelleCard();
        PokerCard poker = new PokerCard( cardValue );
        Vector2 pos = new Vector2();

        poker.setBounds( mScreenSize.x - KScale*mCardW
                , mScreenSize.y - KScale*mCardH, KScale*mCardW, KScale*mCardH);
        this.addActor( poker );

        moveToPosition( cardValue, pos );
        MoveToAction moveTo = Actions.moveTo( pos.x, pos.y, 1 );
        ScaleByAction scaleBy = Actions.scaleBy( -0.25f, -0.25f, 1 );
        ParallelAction actions = Actions.parallel( moveTo, scaleBy );
        poker.addAction(actions);
    }
    @Override
    public void dispose() {
    	helper.cleanTable();
    }
    
    // --------------------------------------------------------------------------------
    //  InputProcessor
    @Override
    public boolean keyDown(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 stageVec = screenToStageCoordinates( new Vector2(screenX, screenY) );

        int i = 0;
        for ( PokerCard card : mListActors ) {
            PokerCard actPoker = (PokerCard)card.hit( stageVec.x, stageVec.y, true );
            if ( actPoker != null ) {
            	if ( actPoker.getmStatus() == TCardStatus.EReadyToPlay ) {
                    // FIXME
                    //  need to check whether the card is available
                    Vector2 dest = new Vector2();
                    moveToPosition( actPoker.getmValue(), dest );
	                MoveToAction moveTo = Actions.moveTo( dest.x, dest.y, 1 );
	                ScaleByAction scaleBy = Actions.scaleBy( -0.25f, -0.25f, 1 );
	                ParallelAction actions = Actions.parallel( moveTo, scaleBy );
	                actPoker.addAction(actions);
	
	                refreshMarcoCardsSizes( i );
	                if ( i == mMarcoCardLastIndex ) {
	                    --mMarcoCardLastIndex;
	                }
	
	                helper.setMarcoCard( actPoker.getmValue() );
	                actPoker.setmStatus( TCardStatus.EPlayed );

                    showAlphaCard();

                    showBelleCard();
                } else if ( actPoker.getmStatus() == TCardStatus.EAvailable ) {
            		// get card ready
            		MoveToAction moveTo = Actions.moveTo( actPoker.getX()
	                        , actPoker.getY() + mCardOffset );
            		actPoker.addAction( moveTo );
            		actPoker.setmStatus( TCardStatus.EReadyToPlay );
            	}
                break;
            }
            ++i;

        }
        return false;
    }

}