package kb.parabola;

import processing.core.PApplet;

public class HitAngle extends PApplet 
{
	int targetSize=100;
	int targetX;
	int targetY;
	int bulletX;
	int bulletY;
	int bulletSpeed = 5;
	boolean bulletShot = false;
	
	Target target;
	
	public static void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "PostXmasCard" });
	}

	public void setup() 
	{
		size(400,400);  
		background(0);
		smooth(); 
	
		targetX = width/2;
		targetY = height/2;
		
		bulletX = (width/2)-5; 
		bulletY = height-25;
	
		target = new Target(this,50.0,targetX,targetY);
	}
	
	public void draw ()
	{
		// draw new background
		fill(0);
		rect(0,0,width,height);
		
		// draw target
		fill(255);
		ellipse(targetX,targetY,targetSize,targetSize);
		//target.display();
		
		float radius = targetSize/2;
		int numPoints = 5;
		int numParts = ceil(numPoints/2);
		float angle = PI/(float)(numPoints-1);
		int hitAngle = -1;
		
		float prevX = 0;
		float prevY =0;
		
		for(int i=1;i<=numPoints;i++)
		{
			int point = i-(numPoints+1)/2;
			
			float pointX = (radius*sin(angle*point))+width/2;
			float pointY = (radius*cos(angle*point))+height/2;			
			
			boolean rightSide = (i >= ceil(numPoints/2)+2);
			
			ellipse(pointX,pointY,5,5);
			
			// hit test
			if ( bulletShot && bulletY <= targetY+(targetSize/2) 
				 && (bulletX <= targetX+(targetSize/2) 
					&& bulletX >= targetX-(targetSize/2)-10)
				)
			{
				if ( !rightSide && bulletY <= prevY 
					 && (bulletX+10 <= pointX && bulletX+10 >= prevX) )
				{
					hitAngle = 90-(90/numParts)*(numParts-i-2);
					bulletShot = false;
					bulletY = height-25;
				}
				
				if ( rightSide && bulletY <= pointY 
					 && (bulletX <= pointX && bulletX >= prevX) )
				{
					hitAngle = 90+(90/numParts)*(i-1-numParts);
					bulletShot = false;
					bulletY = height-25;
				}
				
				if ( i == ceil(numPoints/2)+1 && bulletY <= pointY 
					 && bulletX+10 >= pointX && bulletX <= pointX)
				{
					hitAngle = 90;
					bulletShot = false;
					bulletY = height-25;
				}
			}
			
			prevX = pointX;
			prevY = pointY;
		} 
		
		// draw bullet
		if (keyPressed) 
		{ 
			if (keyCode == LEFT) 		bulletX--;
			else if (keyCode == RIGHT) 	bulletX++;
			else if (key == ENTER)		bulletShot = true;
		}
		// move bullet
		if (bulletShot && bulletY > 0)
		{
			bulletY -= bulletSpeed;
		}
		else if (bulletShot && bulletY <= 0)
		{
			bulletShot = false;
			bulletY = height-25;
		}
		
		rect(bulletX,bulletY,10,25);
	}
}