package com.miluhe.rowsolitaireapp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by jakke on 16-1-5.
 */
public class SolitaireFontLoader implements Disposable{
    private static SolitaireFontLoader mSelf = null;
    private BitmapFont mFont;

    private SolitaireFontLoader() {
        mFont = new BitmapFont(Gdx.files.internal("font/rs.fnt")
                , Gdx.files.internal("font/rs.png")
                , false);
    }

    public static SolitaireFontLoader instance() {
        if (mSelf == null) {
            mSelf = new SolitaireFontLoader();
        }

        return mSelf;
    }

    public BitmapFont getFont() {
        return mFont;
    }
    @Override
    public void dispose() {
        mFont.dispose();
        mFont = null;
        mSelf = null; // wild reference, must be gc
    }
}
