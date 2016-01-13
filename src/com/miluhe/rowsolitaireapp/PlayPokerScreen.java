package com.miluhe.rowsolitaireapp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.stages.PokerStage;
import com.miluhe.rowsolitaireapp.stages.ResultStage;
import com.miluhe.rowsolitaireapp.stages.StartStage;
import com.miluhe.rowsolitaireapp.utils.IResultObserver;

/**
 * Created by jakke on 15-12-29.
 */
public class PlayPokerScreen implements Screen, IResultObserver {

    private int mHeight;
    private int mWidth;
    private PokerStage mPokerStage;
    private ResultStage mResultStage;
    private StartStage mStartStage;
    private SpriteBatch batch;
    private BitmapFont font;
    private LogicHelper mHelper;

    public PlayPokerScreen() {
    	// init logic helper
    	mHelper = new LogicHelper();
    	
        // init stages
    	mStartStage = new StartStage();
        mPokerStage = new PokerStage(mHelper);
        mResultStage = new ResultStage(mHelper);
        mStartStage.setmResultOberser(this);
        mPokerStage.setmResultOberser(this);
        mResultStage.setmResultOberser(this);

        batch = new SpriteBatch();
        font = new BitmapFont();
        
        SolitaireApplication.
          setStageMarker(SolitaireApplication.TStageMarker.EStageStart);
    }

    // --------------------------------------------------------------------------------
    //  Screen
    @Override
    public void dispose() {
    	mHelper.cleanTable();
        mHelper = null;
        mResultStage.dispose();
    	mPokerStage.dispose();
    	mStartStage.dispose();
    }

    @Override
    public void pause() {


    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT ); //清屏
        Stage currentStage;
        if (SolitaireApplication.getStageMarker() 
        		== SolitaireApplication.TStageMarker.EStagePokerTable) {
        	currentStage = mPokerStage;
        } else if (SolitaireApplication.getStageMarker() 
        		== SolitaireApplication.TStageMarker.EStageStart) {
        	currentStage = mStartStage;
        } else/* if (SolitaireApplication.getStageMarker() 
        		== SolitaireApplication.TStageMarker.EStageResult)*/ {
        	currentStage = mResultStage;
        }
        Gdx.input.setInputProcessor( currentStage );
        currentStage.act();
        currentStage.draw();
    }

    @Override
    public void resize( int width, int height ) {
        mWidth = width;
        mHeight = height;

        mStartStage.setRegion( mWidth, mHeight );
        mPokerStage.setRegion( mWidth, mHeight );
        mResultStage.setRegion( mWidth, mHeight );
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(mPokerStage);

    }

	@Override
	public void handleGameOver() {
        Timer timer = new Timer();

        Task timerTask = new Task() {
         @Override
            public void run() {
        	 SolitaireApplication
             .setStageMarker(SolitaireApplication.TStageMarker.EStageResult);
        	 mResultStage.handleGameOver();
            }
        };
        timer.scheduleTask(timerTask, 2, 1, 0);
		
	}

	@Override
	public void handlePlayAgain() {
        mPokerStage = new PokerStage(mHelper);
        mPokerStage.setRegion( mWidth, mHeight );
        mPokerStage.setmResultOberser(this);
        SolitaireApplication
        .setStageMarker(SolitaireApplication.TStageMarker.EStagePokerTable);
	}
}