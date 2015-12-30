package com.miluhe.rowsolitaireapp.stages;

import java.util.ArrayList;
import java.util.List;

import utils.SaveUtils;
import android.graphics.Point;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.actors.PokerCard;

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
    
    // FIXME
    //  get from values folder
    private static final float KMarcoCardBeginX = 100.0f;
    private static final float KMarcoCardBeginY = 100.0f;
    private static final float KMarcoCardOffsetX = 30.0f;
    // end
    List<PokerCard> mListActors = new ArrayList<PokerCard>();

    public PokerStage() {
        Texture bgTexture = new Texture( Gdx.files.internal( "textures/pokertable_bg.jpg" ) );
        mBackgroundImage = new Image( new TextureRegion( bgTexture, 0, 384, 1024, 640 ) );

        this.addActor( mBackgroundImage );
        helper = new LogicHelper();

        helper.startTable();
        mCardsA = helper.showAlphaCards();
        mCardsB = helper.showBelleCards();
        mCardsM = helper.showMarcoCards();

        Point screenSize = new Point();
        int offset = SolitaireApplication.getContextObject()
        				.getResources().getInteger(R.integer.cardoffset);
        SaveUtils.getScreenSize(screenSize);
        float x = (screenSize.x - 16*offset - PokerCard.KWidth) / 2;
        float y = KMarcoCardBeginY;

//        for ( int i : mCardsA ) {
//            PokerCard poker = new PokerCard( i );
//            poker.setPosition( x, y );
//
//            x += 30.0f;
//            this.addActor( poker );
//        }
//        x = 100.0f;
//        y += 50.0f;
//
//        for ( int i : mCardsB ) {
//            PokerCard poker = new PokerCard( i );
//            poker.setPosition( x, y );
//
//            x += 30.0f;
//            this.addActor( poker );
//        }
//        x = 100.0f;
//        y += 50.0f;
//

        for ( int i : mCardsM ) {
            PokerCard poker = new PokerCard( i );
            poker.setPosition( x, y );

            x += KMarcoCardOffsetX;
            this.addActor( poker );
            mListActors.add(poker);
        }

    }

    public void setRegion( int w, int h ) {
        mWidth = w;
        mHeight = h;

        mBackgroundImage.setSize( mWidth, mHeight );
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
    public boolean mouseMoved(int arg0, int arg1) {
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
        // TODO Auto-generated method stub

        int relativeX = (int)(screenX - KMarcoCardBeginX);
        int relativeY = (int)(screenY - KMarcoCardBeginY - 160);

        int index = -1;
        if ( relativeY >=0 && relativeY <= 160
                &&  relativeX >=0 && relativeX <= 16*KMarcoCardOffsetX+120 ) {
            if (relativeX > 16*KMarcoCardOffsetX ) {
                index = 16;
            } else {
                index = (int)(relativeX / KMarcoCardOffsetX);
            }

//			if ( index != -1 ) {
//				int[] newSequence = new int[3];
//				helper.getPlayersCard(newSequence);
//				PokerCard actor = mListActors.get(index);
//		        MoveToAction moveTo = Actions.moveTo(Gdx.graphics.getWidth() / 2
//		        		, Gdx.graphics.getHeight()/2);
//				actor.addAction(moveTo);
//				helper.setPlayerCard(actor.getmValue());
//				helper.getPlayersCard(newSequence);
//				Log.e("*******", "Alpha: "+newSequence[0]);
//				Log.e("*******", "Belle: "+newSequence[1]);
//				Log.e("*******", "Marco: "+newSequence[2]);
//
//			}
        }
        return false;
    }

}