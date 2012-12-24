package kb.postxmascard;

import ddf.minim.Minim;
import kb.utils.KeyDetector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Post Xmas Card
 * --------------
 * Mod of Annemieke's 2011 christmas card that makes the tree shoot the stars
 * out of heaven. Check http://www.kleinegeluiden.nl/kerstkaart/ for the 
 * original version.
 * 
 * @author Annemieke van den Heuvel
 * @author Kevin Breed
 */
public class PostXmasCard extends PApplet
{
	KeyDetector keyDetector;
	Star[] stars = new Star[18];
	Kerstboom kerstboom;
	
	SoundEngine soundEngine;
	Minim minim;
	double fadeValue = 0.0;
	boolean fading = false;
	
	PImage imgOfChrist;
	float[] positionOfChrist = new float[2];
	double opacityOfChrist = 1.0;
	int pauseChrist = 75;
	int christPauseTime = 0;
	float[] christRiseSpeed = {0,0};
	
	public static void main(String args[]) 
	{
		PApplet.main(new String[] { "--present", "PostXmasCard" });
	}

	public void setup() 
	{
	  size(288,432);  
	  background(0);
	  smooth(); 
	  
	  // sound FX with minim and SoundEngine
	  minim = new Minim(this);	 
	  soundEngine = new SoundEngine(this);
	  
	  // custom key detector to detect multiple key presses
	  keyDetector = new KeyDetector();
	  registerKeyEvent(keyDetector);
	  
	  // draw the christmas tree (this one is able to shoot!)
	  kerstboom = new Kerstboom(this);
	  
	  drawStars();
	  
	  // load the pantocrator
	  imgOfChrist = loadImage("christ-pantocrator.png");
	  positionOfChrist[0] = 0;
	  positionOfChrist[1] = this.height;
	}

	public void draw () 
	{   
		background(0);
		
		checkHits();
		
		// draw horizon
		this.fill(0, 128,0);
		this.stroke(255,255,255);
		this.rect(-2, this.height-10,this.width+3,11);
		
		kerstboom.display();
	}
	
	protected void drawStars ()
	{
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
			float starX 	= cellWidth*(i%starsCols); 
			float starY 	= cellHeight*curRow;
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
	
	protected void checkHits ()
	{
		int invisibleStars = 0;
		
		for ( int i=0; i<stars.length; i++ )
		{
			Star star = stars[i];
			
			// skip check if star is not visible
			if ( !star.isVisible ) {
				invisibleStars++;
				continue;
			}
			
			// check if bullet hits star	
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
					soundEngine.starSound.trigger();
				}
			}
			
			// check if falling star hits tree
			if ( star.isHit && !star.hasHitTree )
			{
				PVector starPrevPoint = star.points[star.points.length-1];
				
				for ( int j=0; j<star.points.length; j++ )
				{
					PVector starPoint = star.points[j];
					PVector treeIntersectLeft = segIntersection(
							starPrevPoint.x,
							starPrevPoint.y,
							starPoint.x, 
							starPoint.y,
							kerstboom.x-43, 
							height,
							kerstboom.x,
							height-100
					);
					PVector treeIntersectRight = segIntersection(
							starPrevPoint.x,
							starPrevPoint.y,
							starPoint.x, 
							starPoint.y,
							kerstboom.x+43, 
							height,
							kerstboom.x,
							height-100
					);
					
					if ( !star.hasHitTree 
						 && (treeIntersectLeft != null || treeIntersectRight != null) )
					{
						star.hasHitTree = true;
						kerstboom.hit();
					}
					
					starPrevPoint = star.points[j];
				}
			}
		}
		
		if (invisibleStars >= stars.length) {
			theRiseOfChrist();
			soundEngine.playChristSound();
			soundEngine.christSound.unmute();
		}
	}
	
	protected void theRiseOfChrist ()
	{
		float currentVolume = soundEngine.christSound.getGain();
		
		float targetX = 0;
		float targetY = -10;
		
		float dx = targetX-positionOfChrist[0];
		float dy = targetY-positionOfChrist[1];
		
		int steps = 1000;
		
		if ( christRiseSpeed[0] == 0 ) 
			christRiseSpeed[0] = dx/steps;
		
		if ( christRiseSpeed[1] == 0 ) 
			christRiseSpeed[1] = dy/steps;
		
		// update position
		if (abs(dx) > 1) positionOfChrist[0] += christRiseSpeed[0];
		if (abs(dy) > 1) positionOfChrist[1] += christRiseSpeed[1];
		
		// rise christ
		if (abs(dx) > 1 || abs(dy) > 1)
		{
			tint(255, (float)opacityOfChrist*255f);
			image(imgOfChrist, positionOfChrist[0], positionOfChrist[1]);
			soundEngine.christSound.setGain(0);
		}
		
		// fade christ
		else
		{
			if ( christPauseTime >= pauseChrist )
			{
				tint(255, (float)opacityOfChrist*255f);
				image(imgOfChrist, positionOfChrist[0], positionOfChrist[1]);
				noTint();
				opacityOfChrist -= 0.01;
				
				if ( opacityOfChrist < 0.94 && !fading ) 
				{						
					fading = true;
					soundEngine.christSound.shiftGain(currentVolume, -80, 2000);						
				} else {
					fading = false;					
				}
				
				if (opacityOfChrist < 0.01 )	
				{					
					soundEngine.resetSound.trigger();
					reset();
				}
			}
			else {
				image(imgOfChrist, positionOfChrist[0], positionOfChrist[1]);
				christPauseTime++;
			}
		}
	}
	
	protected void reset() 
	{
		soundEngine.christSound.mute();
		
		christPauseTime = 0;
		opacityOfChrist = 1;
		positionOfChrist = new float[2];
		positionOfChrist[0] = 0;
		positionOfChrist[1] = this.height;
		
		stars = new Star[stars.length];
		drawStars();
	}
	
	// stop and close minim
	public void stop() {
		minim.stop();
		soundEngine.stop();
		super.stop();
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