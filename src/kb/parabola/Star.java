package kb.parabola;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PVector;

public class Star 
{
	PApplet pa;
	
	public float cx;
	public float cy;
	public float width;
	public float height;
	public float angle=0;
	public PVector[] points;
	
	public Star ( PApplet p, float cx, float cy, float width, float height )
	{
		this.pa = p;
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
	}
	
	public void draw ()
	{
		pa.fill(255,255,0);
		drawStar( 5, cx, cy, width, height, angle, 0.4f );
	}
	
	protected void drawStar(
		int n, 
		float cx, 
		float cy, 
		float w, 
		float h,
		float startAngle, 
		float proportion
	) {
	  if (n > 2)
	  {
	    float angle = pa.TWO_PI/ (2 *n);  // twice as many sides
	    float dw; // draw width
	    float dh; // draw height
	    
	    double dubW = w / 2.0;
	    double dubH = h / 2.0;
	    
	    w = (float) dubW;
	    h = (float) dubH;
	    
	    points = new PVector[2*n];
	    
	    pa.beginShape();
	    for (int i = 0; i < 2 * n; i++)
	    {
	      dw = w;
	      dh = h;
	      if (i % 2 == 1) // for odd vertices, use short radius
	      {
	        dw = w * proportion;
	        dh = h * proportion;
	      }
	      float x = cx + dw * pa.cos(startAngle + angle * i);
	      float y = cy + dh * pa.sin(startAngle + angle * i);
	      
	      pa.vertex(x,y);
	      points[i] = new PVector(x,y);
	    }
	    pa.endShape(pa.CLOSE);
	  }
	}
}
