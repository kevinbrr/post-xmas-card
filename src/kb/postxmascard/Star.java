package kb.postxmascard;
import processing.core.PApplet;

public class Star 
{
	PApplet parent; // The parent PApplet that we will render ourselves onto
	
	float x;     
	float y;
	float speed; 
	float angle;
	boolean mouse; 
	float base;
	float r;
	
	public Star ( PApplet p ) 
	{
	    parent = p;
	    
	    x = parent.random(0,parent.width); 
	    y = parent.random(0,parent.height/2);
	    mouse = false;
	    base = 25;//parent.random(0,20); //lengte driehoekje
	    angle = 0.0f;
	    r = (base*4)+4; //radius onzichtbare ellipse
	}
	
	void display() 
	{
		if (mouse) 
		{ 
		  parent.fill(255,255,0);
		  angle += 0.1;
		} else {
		  parent.fill(255,255,0);
		}
		parent.stroke(255,255,0);
		
		//star(x,y,base,1,5);
		 star(5, x, y, 10, 10, angle, 0.4f);
	} 
	
	void star(int n, float cx, float cy, float r, float proportion)
	{
		double size = 2.0 * r;
		float sizeF = (float) size;
		
		this.star(n, cx, cy, sizeF, sizeF, 0.0f, proportion);
	}

	void star(int n, float cx, float cy, float w, float h,
	  float startAngle, float proportion)
	{
	  if (n > 2)
	  {
	    float angle = parent.TWO_PI/ (2 *n);  // twice as many sides
	    float dw; // draw width
	    float dh; // draw height
	    
	    double dubW = w / 2.0;
	    double dubH = h / 2.0;
	    
	    w = (float) dubW;
	    h = (float) dubH;
	    
	    parent.beginShape();
	    for (int i = 0; i < 2 * n; i++)
	    {
	      dw = w;
	      dh = h;
	      if (i % 2 == 1) // for odd vertices, use short radius
	      {
	        dw = w * proportion;
	        dh = h * proportion;
	      }
	      parent.vertex(cx + dw * parent.cos(startAngle + angle * i),
	        cy + dh * parent.sin(startAngle + angle * i));
	    }
	    parent.endShape(parent.CLOSE);
	  }
	}
	
	void star(float x, float y, float base, float lengthStar, int numPoints) 
	{
		parent.pushMatrix();
		parent.translate(x,y);
		parent.rotate(angle);
		//angle -= 0.01;
		float halfBase = base / 2;
		float theta = parent.TWO_PI / numPoints;
		for (int i = 0; i < numPoints; i++) {
		  parent.rotate(theta);
		  parent.triangle(0, -halfBase,
		           0, halfBase,
		           base * (1 + lengthStar), 0);
		}
		parent.popMatrix();
	}
	
	void rollover(int mx, int my) 
	{ 
		if (mx < x+r && mx > x-r && my < y+r  && my > y-r) {
			mouse = true;
		} else {
			mouse = false;
		}
	}
}
