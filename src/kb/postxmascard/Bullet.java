package kb.postxmascard;

import processing.core.PApplet;


public class Bullet 
{
	PApplet app;
	
	public int speed;
	public int x;
	public int y;
	public int width = 5;
	public int height = 10;
	public boolean exploded = false;
	
	
	/**
	 * Constructor
	 * 
	 * @param app parent applet 
	 * @param x x position of bullet
	 * @param y y position of bullet
	 * @param speed speed of bullet
	 */
	public Bullet ( PApplet app, int x, int y, int speed ) 
	{
		this.app = app;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void draw ()
	{
		y -= speed;
	
		if ( !exploded )
		{
			// draw new bullet
			app.fill(255);
			app.noStroke();
			app.rect( x, y, width, height );
		}
	}
	
	public void explode ()
	{
		exploded = true;
	}
}
