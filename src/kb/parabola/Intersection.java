package kb.parabola;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PVector;

public class Intersection extends PApplet 
{
	public Star star;
	
	public static void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "Intersection" });
	}

	public void setup() 
	{
		size(400,400);  
		background(0);
		smooth();
		
		star = new Star(this, 100, 100, 100, 100);
		
		//println( intersection(50,50,200,200,200,50,50,200) );
		
		
	}
	
	public void draw ()
	{
		// draw new background
		fill(255);
		rect(0,0,width,height);
		
		stroke(0);
		rect(100,130,10,25);
		
		PVector bulletPoints[] = new PVector[4];
		bulletPoints[0] = new PVector(100,130);
		bulletPoints[1] = new PVector(110,130);
		bulletPoints[2] = new PVector(110,155);
		bulletPoints[3] = new PVector(100,155);
		
		//line(50, 50, 200, 200);
		//line(200, 50, 50, 200);
		
		fill(255,255,0);
		star.angle += 0.01f;
		star.draw();
		
		PVector prevPoint = star.points[star.points.length-1];
		PVector thisPoint;
		
		for ( int i=0; i<star.points.length; i++ )
		{
			thisPoint = star.points[i];
			
			//line(prevPoint.x,prevPoint.y,thisPoint.x,thisPoint.y);
			//Point intersect = intersection( prevPoint.x,prevPoint.y,thisPoint.x,thisPoint.y,50,50,200,200 );
			
			PVector prevBulletPoint = bulletPoints[bulletPoints.length-1];
			
			for ( int j=0; j<bulletPoints.length; j++)
			{
				PVector bulletPoint = bulletPoints[j];
				
				PVector intersect = segIntersection( 
						prevPoint.x,prevPoint.y,
						thisPoint.x,thisPoint.y,
						prevBulletPoint.x,prevBulletPoint.y,
						bulletPoint.x,bulletPoint.y );
				
				if ( intersect != null )
				{
					println(intersect);
					ellipse(intersect.x,intersect.y,5,5);
				}
				
				prevBulletPoint = bulletPoint;
			}
			prevPoint = thisPoint;
		}
	}
	
	/**
	* Computes the intersection between two lines. The calculated point is approximate, 
	* since integers are used. If you need a more precise result, use doubles
	* everywhere. 
	* (c) 2007 Alexander Hristov. Use Freely (LGPL license). http://www.ahristov.com
	*
	* @param x1 Point 1 of Line 1
	* @param y1 Point 1 of Line 1
	* @param x2 Point 2 of Line 1
	* @param y2 Point 2 of Line 1
	* @param x3 Point 1 of Line 2
	* @param y3 Point 1 of Line 2
	* @param x4 Point 2 of Line 2
	* @param y4 Point 2 of Line 2
	* @return Point where the segments intersect, or null if they don't
	*/
	public Point intersection(
			int x1,int y1,int x2,int y2, 
			int x3, int y3, int x4,int y4
	) {
		int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if (d == 0) return null;

		int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
		int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

		return new Point(xi,yi);
	}

	PVector segIntersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) 
	{ 
	  float bx = x2 - x1; 
	  float by = y2 - y1; 
	  float dx = x4 - x3; 
	  float dy = y4 - y3;
	  float b_dot_d_perp = bx * dy - by * dx;
	  if(b_dot_d_perp == 0) {
	    return null;
	  }
	  float cx = x3 - x1;
	  float cy = y3 - y1;
	  float t = (cx * dy - cy * dx) / b_dot_d_perp;
	  if(t < 0 || t > 1) {
	    return null;
	  }
	  float u = (cx * by - cy * bx) / b_dot_d_perp;
	  if(u < 0 || u > 1) { 
	    return null;
	  }
	  return new PVector(x1+t*bx, y1+t*by);
	}
}
