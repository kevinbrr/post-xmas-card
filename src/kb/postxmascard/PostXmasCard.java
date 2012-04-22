package kb.postxmascard;

import processing.core.PApplet;
import processing.core.PFont;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import kb.postxmascard.*;


public class PostXmasCard extends PApplet
{
	Star[] stars = new Star[10];
	Kerstboom kerstboom;

	PFont fontA;
	float x = width+200; 
	float y = 150;
	
	public static void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "PostXmasCard" });
	}

	public void setup() 
	{
	  size(400,400);  
	  background(0);
	  smooth(); 
	  
	  kerstboom = new Kerstboom(this);
	  
	  for (int i = 0; i < stars.length; i ++ ) {    
	    stars[i] = new Star(this);  
	  }
	}

	public void draw() 
	{   
		background(0);
		smooth();
		kerstboom.display();
		fill(255,0,0);
		float txtSpeed = 1.5f;
		x -= txtSpeed;
		if (x < -200)
		{
			x = width;
			y = random(50,200);
		}
		text("Jingle Bells!",x,y);
	 
		for (int i=0; i < stars.length; i++ ) {    
			stars[i].rollover(mouseX,mouseY); // Passing the mouse coordinates into an object.
			stars[i].display();    
		}  
	}
}
