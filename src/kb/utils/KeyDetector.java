package kb.utils;

import java.awt.event.KeyEvent;

import processing.core.PApplet;

/**
 * Modified version of Option 1 multiplekeys (should provide improved performance and accuracy)
 * @author Yonas Sandb¾k http://seltar.wliia.org (modified by jeffg)
*/
public class KeyDetector 
{
	boolean[] keys = new boolean[526];
	
	public boolean keyPressed ()
	{
		return keys.length > 0;
	}
	
	public boolean keyPressed( int k )
	{
		if (keys.length >= k) return keys[k];  
		return false;
	}
	
	public void keyEvent( KeyEvent e ) 
	{
		if (e.getID() == e.KEY_PRESSED) 	keys[e.getKeyCode()] = true;
		if (e.getID() == e.KEY_RELEASED) 	keys[e.getKeyCode()] = false; 
	}
}
