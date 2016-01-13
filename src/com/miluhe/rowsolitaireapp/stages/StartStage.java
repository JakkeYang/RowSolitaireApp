package com.miluhe.rowsolitaireapp.stages;

import android.graphics.Point;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.miluhe.rowsolitaireapp.R;
import com.miluhe.rowsolitaireapp.SolitaireApplication;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;
import com.miluhe.rowsolitaireapp.actors.TemporaryActor;
import com.miluhe.rowsolitaireapp.actors.TextOutput;
import com.miluhe.rowsolitaireapp.utils.IResultObserver;
import com.miluhe.rowsolitaireapp.utils.SaveUtils;

/**
 * Created by jakke on 16-1-12.
 */
public class StartStage extends Stage {
	private Image mBackgroundImage;
	private TemporaryActor mStart;
	private TemporaryActor mHelp;
	private TemporaryActor mAbout;
	private TextOutput mHelpText;
	private TextOutput mAboutText;
	private String mStrHelpText;
	private String mStrAboutText;
	private IResultObserver mResultOberser;
	private Point mScreenSize;
	
	public StartStage() {
		
		mScreenSize = new Point();

        SaveUtils.getScreenSize(mScreenSize);
        
		// init button and background
		init();
		
		// init text
		initText();
	}
	
	private void init() {
        Texture bgTexture =
                SolitaireTextureLoader.instance().load(SolitaireTextureLoader.KPokerTable);
        mBackgroundImage = new Image( bgTexture );
        this.addActor( mBackgroundImage );
        mBackgroundImage.setZIndex(0);
        
        mStart = new TemporaryActor(SolitaireTextureLoader.KButtonStart);
        mStart.setPosition((mScreenSize.x - mStart.getWidth())/2
        		, mScreenSize.y - mStart.getHeight() - 20);
        mHelp = new TemporaryActor(SolitaireTextureLoader.KButtonHelp);
        
        mHelp.setPosition((mScreenSize.x - mHelp.getWidth())/2
        		, mScreenSize.y - mStart.getHeight() - mHelp.getHeight() - 40);
        
        mAbout = new TemporaryActor(SolitaireTextureLoader.KButtonAbout);
        mAbout.setPosition((mScreenSize.x - mAbout.getWidth())/2
        		, mScreenSize.y - mStart.getHeight() - mHelp.getHeight() -mAbout.getHeight() - 60);
        
        this.addActor(mStart);
        this.addActor(mHelp);
        this.addActor(mAbout);
        
        mStart.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				mResultOberser.handlePlayAgain();
			}
        });
        
        mHelp.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				mHelpText = null;
				mHelpText = new TextOutput(mStrHelpText);
				StartStage.this.addActor(mHelpText);
				VisibleAction fadeOut = Actions.visible(false);
				DelayAction delay = Actions.delay(6);
				MoveToAction moveTo = Actions.moveTo( 0.0f
						, mScreenSize.y - mStart.getHeight() - mHelp.getHeight() -mAbout.getHeight() - 60
						, 2 );
				SequenceAction actions = Actions.sequence( moveTo, delay, fadeOut );
				mHelpText.addAction(actions);
			}
        });
        
        mAbout.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				mAboutText = null;
				mAboutText = new TextOutput(mStrAboutText);
				StartStage.this.addActor(mAboutText);
				VisibleAction fadeOut = Actions.visible(false);
				DelayAction delay = Actions.delay(3);
				MoveToAction moveTo = Actions.moveTo( 0.0f
						, mScreenSize.y - mStart.getHeight() - mHelp.getHeight() -mAbout.getHeight() - 60
						, 2 );
				SequenceAction actions = Actions.sequence(moveTo, delay, fadeOut);
				mAboutText.addAction(actions);
			}
        });
	}
	
	private void initText() {
        mStrHelpText = SolitaireApplication.getContextObject()
    			.getResources()
    			.getString(R.string.start_stage_help);
        
        mStrAboutText = SolitaireApplication.getContextObject()
    			.getResources()
    			.getString(R.string.start_stage_about);

	}
	
    public void setRegion( int w, int h ) {
        mBackgroundImage.setSize( w, h );
    }
    
    public void setmResultOberser(IResultObserver mResultOberser) {
        this.mResultOberser = mResultOberser;
    }
}
