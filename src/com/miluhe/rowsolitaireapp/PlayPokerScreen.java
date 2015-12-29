package com.miluhe.rowsolitaireapp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miluhe.rowsolitaireapp.stages.PokerStage;

/**
 * Created by jakke on 15-12-29.
 */
public class PlayPokerScreen implements Screen {

    private int mHeight;
    private int mWidth;
    private PokerStage mPokerStage;
    private SpriteBatch batch;
    private BitmapFont font;

    public PlayPokerScreen() {
        //实例化
        mPokerStage = new PokerStage();

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    // --------------------------------------------------------------------------------
    //  Screen
    @Override
    public void dispose() {
    }

    @Override
    public void pause() {


    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT ); //清屏
        Gdx.input.setInputProcessor( mPokerStage );
        mPokerStage.act();
        mPokerStage.draw();
    }

    @Override
    public void resize( int width, int height ) {
        mWidth = width;
        mHeight = height;

        mPokerStage.setRegion( mWidth, mHeight );
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
}