package com.miluhe.rowsolitaireapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class SolitaireTextureLoader implements Disposable {
	public static final String KPokerTexture = "cards";
	public static final String KPokerTable = "table";
    public static final String KPlayerAlpha = "Alpha";
	public static final String KPlayerBelle = "Belle";
	public static final String KPlayerMarco = "Marco";
    public static final String KButtonStart = "Start";
    public static final String KButtonRetry = "Retry";
    public static final String KButtonHelp = "Help";
    public static final String KButtonAbout = "About";

	private HashMap<String, Texture> mTextures;
	private AssetManager mAssetManager;
	private static SolitaireTextureLoader mSelf = null;
	
	private SolitaireTextureLoader() {
		mTextures = new HashMap<String, Texture>();
//		mAssetManager = new AssetManager();
		
		String fileName = "texture/cards.png";
        Texture texture = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPokerTexture, texture );
        
        fileName = "texture/pokertable_bg.png";
        Texture textureTable = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPokerTable, textureTable );

        fileName = "texture/alpha.png";
        Texture textureAlpha = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPlayerAlpha, textureAlpha );

        fileName = "texture/belle.png";
        Texture textureBelle = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPlayerBelle, textureBelle );

        fileName = "texture/marco.png";
        Texture textureMarco = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPlayerMarco, textureMarco );

        fileName = "texture/begin.png";
        Texture textureStart = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KButtonStart, textureStart );

        fileName = "texture/retry.png";
        Texture textureRetry = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KButtonRetry, textureRetry );
        
        fileName = "texture/help.png";
        Texture textureHelp = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KButtonHelp, textureHelp );
        
        fileName = "texture/about.png";
        Texture textureAbout = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KButtonAbout, textureAbout );

	}
	
	public static SolitaireTextureLoader instance() {
		if (mSelf == null) {
			mSelf = new SolitaireTextureLoader();
		}
		return mSelf;
	}
	
	public Texture load( String t ) {
		return mTextures.get( t );
	}

	@Override
	public void dispose() {
		Iterator<?> iter = mTextures.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			Texture val = (Texture) entry.getValue();
			val.dispose();
		}
        mSelf = null;
	}

}
