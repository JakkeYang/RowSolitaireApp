package com.miluhe.rowsolitaireapp.stages;

import utils.SaveUtils;
import android.graphics.Point;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.miluhe.rowsolitaire.LogicHelper;
import com.miluhe.rowsolitaireapp.SolitaireTextureLoader;
import com.miluhe.rowsolitaireapp.actors.PlayerAvatar;

/**
 * Created by jakke on 16-1-8.
 */
public class ResultStage extends Stage {
    private Point mScreenSize;
	private PlayerAvatar mActorAlpha;
    private PlayerAvatar mActorBelle;
    private PlayerAvatar mActorMarco;
    private LogicHelper mHelper;
    
    public ResultStage(LogicHelper helper) {
        mScreenSize = new Point();
        mHelper = helper;
        SaveUtils.getScreenSize(mScreenSize);
        init();
    }

	private void init() {
		mActorAlpha = new PlayerAvatar(SolitaireTextureLoader.KPlayerAlpha);
        mActorBelle = new PlayerAvatar(SolitaireTextureLoader.KPlayerBelle);
        mActorMarco = new PlayerAvatar(SolitaireTextureLoader.KPlayerMarco);
        
        int baseLineY = mScreenSize.y / 2;
        mActorAlpha.setBounds( 100, baseLineY - PlayerAvatar.KPlayerTextureY/2
        		, PlayerAvatar.KPlayerTextureX, PlayerAvatar.KPlayerTextureY);
        mActorBelle.setBounds( 100, baseLineY - PlayerAvatar.KPlayerTextureY/2 - 187
        		, PlayerAvatar.KPlayerTextureX, PlayerAvatar.KPlayerTextureY);
        mActorMarco.setBounds(100, baseLineY + PlayerAvatar.KPlayerTextureY/2 + 187
        		, PlayerAvatar.KPlayerTextureX, PlayerAvatar.KPlayerTextureY);
        
        this.addActor(mActorAlpha);
        this.addActor(mActorBelle);
        this.addActor(mActorMarco);
        
//        int [] alphaPoints = mHelper.getAlphaPoints();
//        int [] bellePoints = mHelper.getBellePoints();
//        int [] marcoPoints = mHelper.getMarcoPoints();
	}
    
}
