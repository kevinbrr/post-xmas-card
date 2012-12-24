package kb.postxmascard;
import java.util.ArrayList;

import processing.core.PImage;

public class Kerstboom 
{
	SoundEngine soundEngine;
	PostXmasCard app;
	
	public int moveSpeed = 5;
	public ArrayList<Bullet> bullets;
	
	public int shotPause = 10;
	
	protected boolean isShooting = false;
	protected int prevShot = 0;
	protected PImage fire;
	protected int hitCount = 0;
	
	int x;
	float c1;      
	float c2; 
	float c3;
	float c1dir;  
	float c2dir; 
	float c3dir;
	int r;
	
	public Kerstboom ( PostXmasCard p ) 
	{
	    app = p;
	    bullets = new ArrayList<Bullet>();
	    soundEngine = new SoundEngine(app);
	    
	    //fire = new Gif(app, "fire_animation.gif");
		//fire.play();
	    fire = app.loadImage("fire.png");
	    
	    x = 150;
	    c1 = 0;      
		c2 = 255; 
		c3 = 123;
		c1dir = 1.5f;  
		c2dir = -1.5f; 
		c3dir = 0.5f;
		r = 3;
	}
	
	public void hit ()
	{
		hitCount++;
	}
	
	public void display() 
	{
		drawTree();
		
		// make sure the image will be full opaque
		//app.tint(255);
		
		// handle key presses
		if (app.keyDetector.keyPressed()) 
		{
			if ( !isShooting )
			{
				// shoot new bullet
				if (app.keyDetector.keyPressed(app.ENTER)
					|| app.keyDetector.keyPressed(32)) 
				{
					//app.tint(255,255);
					app.image(fire, x-17, app.height-123);
					soundEngine.shootSound.trigger();
					
					Bullet bullet = new Bullet(app, x, app.height-100, 9);
					bullet.x -= bullet.width/2;
					bullets.add(bullet);
					app.registerDraw(bullet);
					
					isShooting = true;
				}
			}
			else
			{
				prevShot++;
				if (prevShot < 3) {
					//app.tint(255,255);
					app.image(fire, x-17, app.height-123);
				}
				if (prevShot == shotPause)
				{
					isShooting = false;
					prevShot = 0;
				}
			}
			
			// move tree
			if (app.keyDetector.keyPressed(app.LEFT)) 		x -= moveSpeed;
			else if (app.keyDetector.keyPressed(app.RIGHT)) x += moveSpeed;
		}
		
		// don't move tree beyond the boundaries
		if (x <= 0) 			x = 0;
		if ( x >= app.width) 	x = app.width;
		
		// clean up unused elements
		cleanUp();
	}
	
	/**
	 * Delete unused objects from memory
	 */
	protected void cleanUp () 
	{
		// remove bullets outside the screen boundaries
		for ( int i=0; i<bullets.size(); i++ )
		{
			Bullet bullet = bullets.get(i);
			if (bullet.y+bullet.height < 0 || bullet.exploded) bullets.remove(i);
		}
	}
	
	/**
	 * Draw the pretty tree with blinking lights
	 */
	protected void drawTree () 
	{
		// kerstboom
		//app.background(0);
		
		// stam of potje
		app.strokeWeight(2);
		app.stroke(128,64,0);
		app.fill(200,128,0);
		app.rect(x-10,app.height-20,20,40);
    
		// onderkantboom
		app.stroke(255);
		app.fill(0, 128,0);
		app.beginShape();
		app.vertex(x,app.height - 100);
		app.vertex(x-40,app.height-20);
		app.vertex(x+40,app.height-20);
		app.endShape(app.CLOSE);
      
		app.stroke(255);
		app.fill(0,128,0);
		app.beginShape();
		app.vertex(x,app.height-100);
		app.vertex(x-35,app.height-40);
		app.vertex(x+35,app.height-40);
		app.endShape(app.CLOSE);
		
		// topje
		app.beginShape();
		app.vertex(x, app.height-100);
		app.vertex(x-30,app.height-60);
		app.vertex(x+30,app.height-60);
		app.endShape(app.CLOSE);
		
		// lichtjes
		app.noStroke();
		
		
		/* These checks remove a light if the tree is hit by a star. But I don't
		 * think that is really cool */
		hitCount = 0;
		
		if ( hitCount < 1 )
		{	
			app.fill(c1,c2,c3);
			app.ellipse (x,app.height - 85, 3,3);
		}
		if ( hitCount < 2 )
		{	
			app.fill(c3,c1,c2);
			app.ellipse (x-13,app.height - 72, r,r);
		}
		if ( hitCount < 3 )
		{
			app.fill(c1,c1,c2);
			app.ellipse (x+13,app.height - 72, r,r);
		}
		if ( hitCount < 4 )
		{
			app.fill(c2,c1,c2);
			app.ellipse (x,app.height - 70, r,r);
		}
		if ( hitCount < 5 )
		{
			app.fill(c3,c2,c1);
			app.ellipse (x+17,app.height - 55, r,r);
		}
		if ( hitCount < 6 )
		{
			app.fill(c2,c1,c3);
			app.ellipse (x-17,app.height - 55, r,r);
		}
		if ( hitCount < 7 )
		{
			app.fill(c1,c1,c1);
			app.ellipse (x-9,app.height - 52, r,r);
		}
		if ( hitCount < 8 )
		{
			app.fill(c2,c3,c1);
			app.ellipse (x+4,app.height - 50, r,r);
		}
		if ( hitCount < 9 )
		{
			app.fill(c1,c3,c2);
			app.ellipse (x-25,app.height - 35, r,r);
		}
		if ( hitCount < 10 )
		{
			app.fill(c2,c2,c1);
			app.ellipse (x+25,app.height - 35, r,r);
		}
		if ( hitCount < 11 )
		{
			app.fill(c3,c1,c3);
			app.ellipse (x-13,app.height - 30, r,r);
		}
		if ( hitCount < 12 )
		{
			app.fill(c2,c1,c1);
			app.ellipse (x+12,app.height - 30, r,r);
		}
		if ( hitCount < 13 )
		{
			app.fill(c3,c3,c3);
			app.ellipse (x-1,app.height - 28, r,r);
		}
		
		c1 = c1 + c1dir;
		c2 = c2 + c2dir;
		c3 = c3 + c3dir;
    
		// Reverse direction of color change 
		if (c1 < 0 || c1 > 255 || c3 < 0) {
			c1dir *= -1;
			c3dir *= -1;
		}
  
		if (c2 < 0 || c2 > 255 || c3 > 255 ) {
			c2dir *= -1;
			c3dir *= -1;
		}
	}
}
