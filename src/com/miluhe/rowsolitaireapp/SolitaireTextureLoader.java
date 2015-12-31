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
	
	private HashMap<String, Texture> mTextures;
	private AssetManager mAssetManager;
	private static SolitaireTextureLoader mSelf;
	
	static {
		mSelf = null;
	}
	
	private SolitaireTextureLoader() {
		mTextures = new HashMap<String, Texture>();
//		mAssetManager = new AssetManager();
		
		String fileName = "poker/cards.png";
        Texture texture = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPokerTexture, texture );
        
        fileName = "textures/pokertable_bg.png";
        Texture textureTable = new Texture( Gdx.files.internal( fileName ) );
        mTextures.put( KPokerTable, textureTable );
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
	}

}
