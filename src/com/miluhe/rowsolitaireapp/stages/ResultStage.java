package com.miluhe.rowsolitaireapp.stages;

import com.miluhe.rowsolitaireapp.utils.IResultObserver;
import com.miluhe.rowsolitaireapp.utils.SaveUtils;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;
import com.miluhe.rowsolitaireapp.actors.PlayerAvatar;
import com.miluhe.rowsolitaireapp.actors.PokerCard;

/**
 * Created by jakke on 16-1-8.
 */
public class ResultStage extends Stage implements IResultObserver {
    private Point mScreenSize;
    private Image mBackgroundImage;
	private PlayerAvatar mActorAlpha;
    private PlayerAvatar mActorBelle;
    private PlayerAvatar mActorMarco;
    private LogicHelper mHelper;
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

	}

    private void freshPassedPointCard(float baseLineA, float baseLineB, float baselineM ) {
        int [] alphaPoints = mHelper.getAlphaPoints();

        if (alphaPoints != null && alphaPoints.length > 0 ) {
            int xOffset = KCardStartX;
            for (int value : alphaPoints) {
                mAlphaPoints += value;
                PokerCard pokerCard = new PokerCard(value);
                pokerCard.setPosition(xOffset, baseLineA);
                xOffset += mCardGap;
                this.addActor(pokerCard);
            }
        }
        int [] bellePoints = mHelper.getBellePoints();

        if (bellePoints != null && bellePoints.length > 0 ) {
            int xOffset = KCardStartX;
            for (int value : bellePoints) {
                mBellePoints += value;
                PokerCard pokerCard = new PokerCard(value);
                pokerCard.setPosition(xOffset, baseLineB);
                xOffset += mCardGap;
                this.addActor(pokerCard);
            }
        }

        int [] marcoPoints = mHelper.getMarcoPoints();
        if (marcoPoints != null && marcoPoints.length > 0 ) {
            int xOffset = KCardStartX;
            for (int value : marcoPoints) {
                mMarcoPoints += value;
                PokerCard pokerCard = new PokerCard(value);
                pokerCard.setPosition(xOffset, baselineM);
                xOffset += mCardGap;
                this.addActor(pokerCard);
            }
        }
    }

    @Override
    public void handleGameOver() {
        freshPassedPointCard(mABaseLine, mBBaseLine, mMBaseLine);
    }
}
