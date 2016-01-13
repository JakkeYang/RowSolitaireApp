package com.miluhe.rowsolitaireapp.stages;

import com.miluhe.rowsolitaireapp.utils.IResultObserver;
import com.miluhe.rowsolitaireapp.utils.SaveUtils;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;
import com.miluhe.rowsolitaireapp.actors.PlayerAvatar;
import com.miluhe.rowsolitaireapp.actors.PokerCard;
import com.miluhe.rowsolitaireapp.actors.TemporaryActor;
import com.miluhe.rowsolitaireapp.actors.TextOutput;

/**
 * Created by jakke on 16-1-8.
 */
public class ResultStage extends Stage {
    private Point mScreenSize;
    private Image mBackgroundImage;
	private PlayerAvatar mActorAlpha;
    private PlayerAvatar mActorBelle;
    private PlayerAvatar mActorMarco;
    private LogicHelper mHelper;
    private IResultObserver mResultOberser;
    private int mAlphaPoints = 0;
    private int mBellePoints = 0;
    private int mMarcoPoints = 0;
    private int mHeight;
    private int mWidth;
    private float mABaseLine;
    private float mBBaseLine;
    private float mMBaseLine;
    private static float mCardW;
    private static float mCardH;
    private static int mCardGap;
    private static final int KAvatarStartX = 100;
    private static final int KCardStartX = 120;

    static {
        try {
            mCardGap = SolitaireApplication.getContextObject()
                    .getResources().getInteger(R.dimen.cardresultoffset);

            TypedValue v = new TypedValue();
            SolitaireApplication.getContextObject()
                    .getResources().getValue(R.dimen.cardresultx, v, true);
            mCardW = v.getFloat();
            SolitaireApplication.getContextObject()
                    .getResources().getValue(R.dimen.cardresulty, v, true);
            mCardH = v.getFloat();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultStage(LogicHelper helper) {
        mScreenSize = new Point();
        mHelper = helper;
        SaveUtils.getScreenSize(mScreenSize);
        init();
    }

    public void setmResultOberser(IResultObserver mResultOberser) {
        this.mResultOberser = mResultOberser;
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

	private void init() {
        Texture bgTexture =
                SolitaireTextureLoader.instance().load(SolitaireTextureLoader.KPokerTable);
        mBackgroundImage = new Image( bgTexture );
        this.addActor( mBackgroundImage );
        mBackgroundImage.setZIndex(0);

		mActorAlpha = new PlayerAvatar(SolitaireTextureLoader.KPlayerAlpha);
        mActorBelle = new PlayerAvatar(SolitaireTextureLoader.KPlayerBelle);
        mActorMarco = new PlayerAvatar(SolitaireTextureLoader.KPlayerMarco);
        
        int midY = mScreenSize.y / 2;
        float baseLineA = midY - PlayerAvatar.KPlayerTextureY/2;
        float baseLineB = midY - PlayerAvatar.KPlayerTextureY/2 - (mCardH+mCardGap)*2;
        float baseLineM = midY + PlayerAvatar.KPlayerTextureY/2 + mCardH + 2*mCardGap;
        mActorAlpha.setBounds( KAvatarStartX, baseLineA
        		, PlayerAvatar.KPlayerTextureX, PlayerAvatar.KPlayerTextureY);
        mActorBelle.setBounds( KAvatarStartX, baseLineB
        		, PlayerAvatar.KPlayerTextureX, PlayerAvatar.KPlayerTextureY);
        mActorMarco.setBounds(KAvatarStartX, baseLineM
        		, PlayerAvatar.KPlayerTextureX, PlayerAvatar.KPlayerTextureY);
        
        this.addActor(mActorAlpha);
        this.addActor(mActorBelle);
        this.addActor(mActorMarco);

        mABaseLine = baseLineA - mCardH - mCardGap;
        mBBaseLine = baseLineB - mCardH - mCardGap;
        mMBaseLine = baseLineM - mCardH - mCardGap;
        
        // set again button
        TemporaryActor again = new TemporaryActor(SolitaireTextureLoader.KButtonRetry);
        again.setPosition( (mScreenSize.x - again.getWidth()) /2 
        		, mScreenSize.y - again.getHeight() -mCardGap );
        this.addActor(again);

        again.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				mResultOberser.handlePlayAgain();
			}
        	
        });
	}

    private void freshPassedPointCard(float baseLineA, float baseLineB, float baselineM ) {
        int [] alphaPoints = mHelper.getAlphaPoints();

        if (alphaPoints != null && alphaPoints.length > 0 ) {
            int xOffset = KCardStartX;
            for (int value : alphaPoints) {
            	if (value == 0) {
            		continue;
            	}
            	int perValue = (value % 13 == 0)? 13 : value % 13;
                mAlphaPoints += perValue;
                PokerCard pokerCard = new PokerCard(value);
                pokerCard.setBounds(xOffset, baseLineA, mCardW, mCardH);
                xOffset += mCardGap;
                this.addActor(pokerCard);
            }
        }
        int [] bellePoints = mHelper.getBellePoints();

        if (bellePoints != null && bellePoints.length > 0 ) {
            int xOffset = KCardStartX;
            for (int value : bellePoints) {
            	if (value == 0) {
            		continue;
            	}
            	int perValue = (value % 13 == 0)? 13 : value % 13;
                mBellePoints += perValue;
                PokerCard pokerCard = new PokerCard(value);
                pokerCard.setBounds(xOffset, baseLineB, mCardW, mCardH);
                xOffset += mCardGap;
                this.addActor(pokerCard);
            }
        }

        int [] marcoPoints = mHelper.getMarcoPoints();
        if (marcoPoints != null && marcoPoints.length > 0 ) {
            int xOffset = KCardStartX;
            for (int value : marcoPoints) {
            	if (value == 0) {
            		continue;
            	}
            	int perValue = (value % 13 == 0)? 13 : value % 13;
                mMarcoPoints += perValue;
                PokerCard pokerCard = new PokerCard(value);
                pokerCard.setBounds(xOffset, baselineM, mCardW, mCardH);
                xOffset += mCardGap;
                this.addActor(pokerCard);
            }
        }
        
        String result;
        if (mMarcoPoints <= mBellePoints 
        		&& mMarcoPoints <= mAlphaPoints) {
        	result = SolitaireApplication.getContextObject()
        			.getResources()
        			.getString(R.string.poker_stage_game_win);

        } else {
        	result = SolitaireApplication.getContextObject()
        			.getResources()
        			.getString(R.string.poker_stage_game_lose);
        }
    	TextOutput txt = null;
    	txt = new TextOutput(result);
    	txt.setPosition((mScreenSize.x - txt.getWidth()) / 2, .0f);
		this.addActor(txt);
		DelayAction delay = Actions.delay(3);
		RotateByAction rot = Actions.rotateBy(360, 2);
		MoveToAction moveTo = Actions.moveTo( (mScreenSize.x - txt.getWidth()) / 2
				, mScreenSize.y / 2 - txt.getHeight()
				, 2 );
		ParallelAction actions1 = Actions.parallel( moveTo, rot );
		SequenceAction actions2 = Actions.sequence(actions1, delay);
		txt.addAction(actions2);
    }

    public void handleGameOver() {
        freshPassedPointCard(mABaseLine, mBBaseLine, mMBaseLine);
    }
}
