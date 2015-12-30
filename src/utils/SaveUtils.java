package utils;

import com.miluhe.rowsolitaireapp.SolitaireApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

/**
 * Created by jakke on 15-12-30.
 */
public class SaveUtils {
	public static final String RS_SP_Text = "RowSolitaireApp";
	public static final String RS_SP_ScreenW = "ScreenWidth";
	public static final String RS_SP_ScreenH = "ScreenHeight";
	
	public static void setScreenSize( Point pt ) {
		Context cnx = SolitaireApplication.getContextObject(); 
		cnx.getSharedPreferences(RS_SP_Text, Context.MODE_PRIVATE)
        .edit()
        .putString( RS_SP_ScreenW, "" + pt.x )
        .putString( RS_SP_ScreenH, "" + pt.y )
        .commit();
	}
	
	public static void getScreenSize( Point pt )
	{
		Context cnx = SolitaireApplication.getContextObject(); 
		SharedPreferences sp = 
				cnx.getSharedPreferences(RS_SP_Text, Context.MODE_PRIVATE);
        
		String strX = sp.getString( RS_SP_ScreenW, "0" );
        String strY = sp.getString( RS_SP_ScreenH, "0" );
        
        pt.x = Integer.parseInt(strX);
        pt.y = Integer.parseInt(strY);
        
	}
}
