package com.miluhe.rowsolitaireapp.stages;

import java.util.ArrayList;
import java.util.List;

import utils.SaveUtils;
import android.content.res.Resources.NotFoundException;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;
import com.miluhe.rowsolitaireapp.actors.PokerCard;
import com.miluhe.rowsolitaireapp.actors.PokerCard.TCardStatus;
import com.miluhe.rowsolitaireapp.actors.TextOutput;

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
    private TextOutput mTextView = null;
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

    /**
     * Constructor
     */
    public PokerStage() {
        Texture bgTexture =
        		SolitaireTextureLoader.instance().load(SolitaireTextureLoader.KPokerTable);
        mBackgroundImage = new Image( bgTexture );
        this.addActor( mBackgroundImage );
        mBackgroundImage.setZIndex(0);
        helper = new LogicHelper();

        init();
    }

    /**
     * init card table
     */
    private void init() {
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
            poker.setZIndex(i);
            mListActors.add(poker);
        }

        int firstPlayerInx = helper.getFirstPlayerIndex();
        showFirstCard();

        if (firstPlayerInx == 0) {
            // marco play first, need to show alpha and belle
            showAlphaCard();
            showBelleCard();
            Log.e("*** RS ***", "Marco played first");
        } else if (firstPlayerInx == 1) {
            // alpha play first, just show belle
            showBelleCard();
            Log.e("*** RS ***", "Alpha played first");
        } else if (firstPlayerInx == 2) {
            // belle play first, do nothing
            Log.e("*** RS ***", "Belle played first");
        } else {
            // wrong
        }
    }

    /**
     * set background size
     * @param w
     * @param h
     */
    public void setRegion( int w, int h ) {
        mWidth = w;
        mHeight = h;

        mBackgroundImage.setSize( mWidth, mHeight );
    }

    /**
     * refresh user's card size
     *  once user played or pass a card, it will impact to left cards hit region
     *
     *  xxx seems that libgdx has mechanism to handle event instead
     *
     * @param playedIndex played or passed card's index in user cards list
     */
    private void refreshUserCardsSizes(int playedIndex) {
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

    /**
     * calculating played card (of user and AI) positon, in order to form into a queue
     *
     * @param cardValue card's value
     * @param dest destination of this card
     */
    private void moveToPosition( int cardValue, Vector2 dest ) {
        int suit = (cardValue % 13 == 0)? cardValue / 13 - 1 : cardValue / 13;
        int value = (cardValue % 13 == 0)? 13 : cardValue % 13;

        dest.x = suit * ( KScale * mCardW + mCardOffset ) + mPlayedCardStartX;
        dest.y = ( 13 - value ) * mCardOffset + mPlayedCardStartY;

    }

    /**
     * play Alpha (AI) card
     */
    private void showAlphaCard() {
        int cardValue = helper.getAlphaCard();

        if ( cardValue == 0 ) {
            // 0 means pass
            int[] alphaPoints = helper.getAlphaPoints();
            int count = 0;
            for (int i : alphaPoints) {
                count += i;
            }

            if ( count > 0 ) {
                showText("Alpha passed card");
                Log.e("*** RS ***", "alpha passed");
            }
            return;
        }
        PokerCard poker = new PokerCard( cardValue );
        Vector2 pos = new Vector2();

        poker.setBounds( .0f, mScreenSize.y - KScale*mCardH, KScale*mCardW, KScale*mCardH);
        this.addActor( poker );
        reArrangeZIndex(poker);
        moveToPosition( cardValue, pos );
        MoveToAction moveTo = Actions.moveTo( pos.x, pos.y, 1 );
        ScaleByAction scaleBy = Actions.scaleBy( -0.25f, -0.25f, 1 );
        ParallelAction actions = Actions.parallel( moveTo, scaleBy );
        poker.addAction(actions);
    }

    /**
     * Play Belle (AI) card
     */
    private void showBelleCard() {
        int cardValue = helper.getBelleCard();
        if (cardValue == 0) {
            // 0 means pass
            int[] bellePoints = helper.getBellePoints();
            int count = 0;
            for (int i : bellePoints) {
                count += i;
            }

            if ( count > 0 ) {
                showText("Belle passed card");
                Log.e("*** RS ***", "belle passed");
            }
            return;
        }
        PokerCard poker = new PokerCard( cardValue );
        Vector2 pos = new Vector2();

        poker.setBounds( mScreenSize.x - KScale*mCardW
                , mScreenSize.y - KScale*mCardH, KScale*mCardW, KScale*mCardH);
        this.addActor( poker );
        reArrangeZIndex(poker);

        moveToPosition( cardValue, pos );
        MoveToAction moveTo = Actions.moveTo( pos.x, pos.y, 1 );
        ScaleByAction scaleBy = Actions.scaleBy( -0.25f, -0.25f, 1 );
        ParallelAction actions = Actions.parallel( moveTo, scaleBy );
        poker.addAction(actions);
    }

    /**
     * Play the first card (always spade 7)
     */
    private void showFirstCard() {
        PokerCard poker = new PokerCard( 7 );
        Vector2 pos = new Vector2();
        moveToPosition( 7, pos );
        poker.setBounds( pos.x , pos.y, KScale*mCardW, KScale*mCardH);
        this.addActor(poker);
        reArrangeZIndex(poker);
    }

    /**
     * show indication text (a libgdx actor)
     * @param txt indication text
     */
    private void showText(String txt) {
        if (mTextView == null) {
            mTextView = new TextOutput(txt);
        } else {
            mTextView.setText(txt);
        }
        Vector2 pos = new Vector2(50.0f, 200.0f + mCardH);

        mTextView.setPosition(pos.x, pos.y);

        this.addActor(mTextView);
    }

    /**
     * inserted card will cause the queue to re-index
     *
     * xxx stage doesn't provide a method to "insert" actor but add, however it can be done
     *  via stage's root (Group) method
     *
     * @param insertCard inserted index
     */
    private void reArrangeZIndex(PokerCard insertCard) {
        int cardValue = insertCard.getmValue();
        Array<Actor> actors = this.getActors();
        for (int i = 0; i < actors.size; ++i) {
            if (actors.get(i) instanceof PokerCard) {
                PokerCard card = (PokerCard)actors.get(i);
                if (card.getmValue() > cardValue) {
                    for (int j = actors.size - 1; j <= i; --j) {
                        actors.get(j).setZIndex(actors.get(j).getZIndex()+1);
                    }
                    insertCard.setZIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * release resources
     */
    @Override
    public void dispose() {
    	helper.cleanTable();
        helper = null;
    }

    /**
     * from InputProcessor
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @return
     */
    @Override
    public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
        return false;
    }

    /**
     * from InputProcessor
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     */
    @Override
    public boolean touchDragged(int arg0, int arg1, int arg2) {
        return false;
    }

    /**
     * InputProcessor
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 stageVec = screenToStageCoordinates( new Vector2(screenX, screenY) );

        int i = 0;
        for ( PokerCard card : mListActors ) {
            PokerCard actPoker = (PokerCard)card.hit( stageVec.x, stageVec.y, true );
            if ( actPoker != null ) {
                // hit a card
            	if ( actPoker.getmStatus() == TCardStatus.EReadyToPlay ) {
                    // hit card is ready to play or pass, or just put it back to available
                    if (!helper.canUserPlayCard(actPoker.getmValue())) {
                        // user can't play this hit card, so it might be passed or back to available
                        if (!helper.canUserContinue()) {
                            // user cannot continue, we take it as a "pass card"
                            //  instead of putting back
                            Vector2 dest = new Vector2(0,0);
                            movePoker(actPoker, dest, -0.75f);
                            refreshUserCardsSizes(i);
                            if ( i == mMarcoCardLastIndex ) {
                                // passed last card, last index - 1
                                --mMarcoCardLastIndex;
                            }
                        } else {
                            // user actually can continue, so we take it
                            //  as putting card back to available
                            getReadyCard( actPoker, false );
                        }
                    } else {
                        // hit card can be played, so be it
                        Vector2 dest = new Vector2();
                        moveToPosition(actPoker.getmValue(), dest);
                        movePoker(actPoker, dest, -0.25f );

                        refreshUserCardsSizes(i);
                        if (i == mMarcoCardLastIndex) {
                            // played last card, last index - 1
                            --mMarcoCardLastIndex;
                        }
                    }
                } else if ( actPoker.getmStatus() == TCardStatus.EAvailable ) {
            		// get card ready
                    getReadyCard( actPoker, true );
            	}
                break;
            }
            ++i;

        }
        return false;
    }

    /**
     * move played or passed card
     * @param actPoker source card
     * @param dest moved destination
     * @param scale scale, negative to shrink
     */
    private void movePoker(PokerCard actPoker, Vector2 dest, float scale) {
        MoveToAction moveTo = Actions.moveTo(dest.x, dest.y, 1);
        ScaleByAction scaleBy = Actions.scaleBy(scale, scale, 1);
        ParallelAction actions = Actions.parallel(moveTo, scaleBy);
        actPoker.addAction(actions);

        helper.setMarcoCard(actPoker.getmValue());
        actPoker.setmStatus(TCardStatus.EPlayed);
        updateCardTable();
    }

    /**
     * move card between ready and not-ready
     *
     * @param actPoker card
     * @param bReady true ready, false not ready
     */
    private void getReadyCard(PokerCard actPoker, boolean bReady) {
        if (bReady) {
            // get card ready, move upward
            MoveToAction moveTo = Actions.moveTo( actPoker.getX()
                    , actPoker.getY() + mCardOffset );
            actPoker.addAction( moveTo );
            actPoker.setmStatus( TCardStatus.EReadyToPlay );
        } else {
            // put card back, move downward
            MoveToAction moveTo = Actions.moveTo(actPoker.getX()
                    , actPoker.getY() - mCardOffset);
            actPoker.addAction( moveTo );
            actPoker.setmStatus( TCardStatus.EAvailable );
        }
    }

    /**
     * update card table, after user played or passed a card
     */
    private void updateCardTable() {
        // clear text
        showText(" ");

        // show AI alpha card (played or passed)
        showAlphaCard();

        // show AI belle card (played or passed)
        showBelleCard();

        // check if game is over
        if (helper.isGamesOver()) {
            showText("Game over!");
            // FIXME
            // go to result stage
            return;
        }

        // check if user can continue, if not, notify him
        if (!helper.canUserContinue()) {
            // marco have to pass
            showText("you have to pass a card");
        }
    }

}