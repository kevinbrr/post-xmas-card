package kb.postxmascard;

import kb.utils.KeyDetector;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;


public class PostXmasCard extends PApplet
{
	KeyDetector keyDetector;
	
	Star[] stars = new Star[12];
	Kerstboom kerstboom;

	/*
	PFont fontA;
	float x = width+200; 
	float y = 150;
	*/
	
	public static void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "PostXmasCard" });
	}

	public void setup() 
	{
	  size(400,400);  
	  background(0);
	  smooth(); 
	  
	  // custom key detector to detect multiple key presses
	  keyDetector = new KeyDetector();
	  registerKeyEvent(keyDetector);
	  
	  // draw the christmas tree (this one is able to shoot!)
	  kerstboom = new Kerstboom(this);
	  
	  // get row and column dimension based on the total number of stars
	  // (note that any number larger than ten ending on 1, 3, 7 or 9 only
	  // produces a single line with stars, because these numbers can't be
	  // equally divided)
	  int[] starsRowAndColNum = getRowAndColNumByTotal(stars.length);
	  int starsRows = starsRowAndColNum[0];
	  int starsCols = starsRowAndColNum[1];
	  int curRow = -1;
	  double skyHeight = Math.ceil(height/2f);
	  float cellWidth = (width/starsCols);
	  float cellHeight = (float) (skyHeight/starsRows);
	  
	  // fill the sky with stars!
	  for (int i = 0; i < stars.length; i ++ ) 
	  {
		  // determine the current row
		  if ( i%starsCols == 0 ) curRow++;
		  
		  // put stars neatly into a grid so they don't overlap
		  float starX 		= cellWidth*(i%starsCols); 
		  float starY 		= cellHeight*curRow;
		  float starSize 	= random(20,35);
		  
		  // randomize star position within their cell
		  starX = random(starX+(starSize/2), starX+cellWidth-(starSize/2));
		  starY = random(starY+(starSize/2), starY+cellHeight-(starSize/2));
		  
		  // create star
		  Star star = new Star(this, starX, starY, starSize, starSize, random(0,360));
		  registerDraw(star);
		  stars[i] = star;
	  }
	}

	public void draw () 
	{   
		background(0);
		kerstboom.display();
		
		checkHits();
		
		/*
		fill(255,0,0);
		
		float txtSpeed = 1.5f;
		x -= txtSpeed;
		if (x < -200)
		{
			x = width;
			y = random(50,200);
		}
		
		text("Jingle Bells!",x,y);
		*/ 
	}
	
	protected void checkHits ()
	{
		for ( int i=0; i<stars.length; i++ )
		{
			Star star = stars[i];
			
			for ( int j=0; j<kerstboom.bullets.size(); j++ )
			{
				Bullet bullet = kerstboom.bullets.get(j);
			
				PVector intersect = segIntersection(
					// bottom line of star
					star.cx-(star.width/2), 
					star.cy+(star.height/2), 
					star.cx+(star.width/2), 
					star.cy+(star.height/2),
					// middle line of bullet
					bullet.x+(bullet.width/2),
					bullet.y,
					bullet.x+(bullet.width/2),
					bullet.y+bullet.height
				);
				
				if ( intersect != null && !star.isHit )
				{
					float hitAngle = (float) Math.toDegrees( 
										Math.atan2(
											intersect.x-star.cx, star.cy-intersect.y
										));
					
					if (hitAngle > 0) 	hitAngle = (180-hitAngle)+90;
					else 				hitAngle = Math.abs(hitAngle+90);
					
					star.fall(hitAngle);
					bullet.explode();
				}
			}
		}
	}
	
	/**
	 * Get row and column distribution based on total number
	 * 
	 * NOTE that any number larger than ten and ending on 1, 3, 7 or 9 always
	 * will return a single row with the total number as columns, because these 
	 * numbers can't be equally divided.
	 * 
	 * @param total
	 * @return
	 */
	protected int[] getRowAndColNumByTotal( int total )
	{
		int rows = (int) Math.ceil(total/10f)+2;
		int cols = 1;
		
		if ( total > 10 )
		{
			switch ( total%10 )
			{
				case 1 :
				case 3 :
				case 7 :
				case 9 :
					int[] resultArr = {1,total};
					return resultArr;
				default :
					// nothing to do here...
			}
		}
		
		while ( rows >= 1 )
		{
			float result = total/rows;
			if ( total%rows == 0 ) 
			{
				cols = (int) result;
				break;	
			}	
			rows--;
		}
		
		int[] resultArr = {rows,cols};
		return resultArr;
	}
	
	/**
	 * Two line intersection test
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	protected PVector segIntersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) 
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