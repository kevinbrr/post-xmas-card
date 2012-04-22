package kb.postxmascard;
import processing.core.PApplet;

public class Kerstboom 
{
	PApplet parent;
	
	int x;
	float c1;      
	float c2; 
	float c3;
	float c1dir;  
	float c2dir; 
	float c3dir;
	int r;
	
	public Kerstboom ( PApplet p ) 
	{
	    parent = p;
	
	    x = 150;
	    c1 = 0;      
		c2 = 255; 
		c3 = 123;
		c1dir = 1.5f;  
		c2dir = -1.5f; 
		c3dir = 0.5f;
		r = 3;
	}
	
	void display() 
	{
		//kerstboom
		parent.background(0);
		//stam of potje
		parent.strokeWeight(2);
		parent.stroke(128,64,0);
		parent.fill(200,128,0);
		parent.rect(x-10,parent.height-20,20,40);
    
		//onderkantboom
		parent.stroke(255);
		parent.fill(0, 128,0);
		parent.beginShape();
		parent.vertex(x,parent.height - 100);
		parent.vertex(x-40,parent.height-20);
		parent.vertex(x+40,parent.height-20);
		parent.endShape(parent.CLOSE);
      
		parent.stroke(255);
		parent.fill(0,128,0);
		parent.beginShape();
		parent.vertex(x,parent.height-100);
		parent.vertex(x-35,parent.height-40);
		parent.vertex(x+35,parent.height-40);
		parent.endShape(parent.CLOSE);
		//topje
		parent.beginShape();
		parent.vertex(x, parent.height-100);
		parent.vertex(x-30,parent.height-60);
		parent.vertex(x+30,parent.height-60);
		parent.endShape(parent.CLOSE);
    
		//lichtjes
		parent.noStroke();
		parent.fill(c1,c2,c3);
		parent.ellipse (x,parent.height - 85, 3,3);
		parent.fill(c3,c1,c2);
		parent.ellipse (x-13,parent.height - 72, r,r);
		parent.fill(c1,c1,c2);
		parent.ellipse (x+13,parent.height - 72, r,r);
		parent.fill(c2,c1,c2);
		parent.ellipse (x,parent.height - 70, r,r);
   
		parent.fill(c3,c2,c1);
		parent.ellipse (x+17,parent.height - 55, r,r);
		parent.fill(c2,c1,c3);
		parent.ellipse (x-17,parent.height - 55, r,r);
		parent.fill(c1,c1,c1);
		parent.ellipse (x-9,parent.height - 52, r,r);
		parent.fill(c2,c3,c1);
		parent.ellipse (x+4,parent.height - 50, r,r);
    
		parent.fill(c1,c3,c2);
		parent.ellipse (x-25,parent.height - 35, r,r);
		parent.fill(c2,c2,c1);
		parent.ellipse (x+25,parent.height - 35, r,r);
		parent.fill(c3,c1,c3);
		parent.ellipse (x-13,parent.height - 30, r,r);
		parent.fill(c2,c1,c1);
		parent.ellipse (x+12,parent.height - 30, r,r);
		parent.fill(c3,c3,c3);
		parent.ellipse (x-1,parent.height - 28, r,r);
    
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
    
		if (parent.keyPressed && (parent.key == parent.CODED)) { 
			if (parent.keyCode == parent.LEFT) { // 
				x--;
			} else if (parent.keyCode == parent.RIGHT) { // 
				x++;
			}
		}
		if (x <= 40){
			x = 40;
		}
		if ( x >= 360){
			x = 360;
		}     
	}
}
