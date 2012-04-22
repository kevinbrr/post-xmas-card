package kb.parabola;

import processing.core.PApplet;

public class Target 
{
	PApplet pa;

	public int fillColor=255;
	public int x;
	public int y;
	public int size;
	
	public Target ( PApplet p, double size, int x, int y ) 
	{
	    pa = p;
	}
	
	public void display ()
	{
		// draw target
		pa.fill(fillColor);
		pa.ellipse(x,y,size,size);
	}
}
