package kb.parabola;

import processing.core.PApplet;

public class Parabola extends PApplet 
{
	double gravity = 9.8;
	double time = 1;
	double velocity = 30; // rate of change of displacement (speed)
	float angle = 65;
	
	public static void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "Parabola" });
	}

	public void setup() 
	{
		size(400,400);  
		background(0);
		smooth(); 
	}
	
	public void draw ()
	{
		int startX = width/2;
		int startY = height/2;
	
		fill(0);
		rect(0,0,width,height);
		fill(255);
		
		int xpos = (int)(startX + horizontalDisplacement(time));
		int ypos = (int)(startY - verticalDisplacement(time));
		
		ellipse(xpos,ypos,10,10);
		time += 0.1;
		
		// reset time 
		if (xpos>width || ypos>height) time=0;
		
		fill(0);
	}
	
	double  horizontalDisplacement( double time ) 
	{ 
		return velocity*time*cos(radians(angle)); 
	}
	
	double  verticalDisplacement( double time ) 
	{ 
		return (velocity*time*sin(radians(angle)) - gravity/2*time*time); 
	}
}
