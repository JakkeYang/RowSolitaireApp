package com.miluhe.rowsolitaireapp.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.miluhe.rowsolitaireapp.SolitaireFontLoader;

/**
 * Created by jakke on 16-1-5.
 */
public class TextOutput extends Actor {

    private String mText;

    public TextOutput(String txt) {
        mText = txt;
    }

    public void setText(String txt) {
        mText = txt;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        SolitaireFontLoader.instance().getFont()
                .drawMultiLine(batch, mText, getX(), getY());

    }


}
