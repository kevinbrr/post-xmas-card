class Star {
  
  float x;     
  float y;
  float speed; 
  float angle;
  boolean mouse; 
  float base;
  float r;
   
  Star() {
    x = random(0,width); 
    y = random(0,height/2);
    mouse = false;
    base = random(0,5); //lengte driehoekje
    angle = 0.0;
    r = (base*4)+4; //radius onzichtbare ellipse
  }
  
  void display() {
    if (mouse) 
    { 
      fill(255,255,0);
      angle += 0.1;
    } else {
      fill(255,255,0);
    }
    stroke(255,255,0);
    star(x,y,base,1,5);
         
    stroke(0,0,0,0);
    fill(0,0,0,0);
    ellipse(x,y,r,r);
  } 
  
  void star(float x, float y, float base, float lengthStar, int numPoints) {
    pushMatrix();
    translate(x,y);
    rotate(angle);
    //angle -= 0.01;
    float halfBase = base / 2;
    float theta = TWO_PI / numPoints;
    for (int i = 0; i < numPoints; i++) {
      rotate(theta);
      triangle(0, -halfBase,
               0, halfBase,
               base * (1 + lengthStar), 0);
    }
     
    popMatrix();
  }

  void rollover(int mx, int my) { 
      if (mx < x+r && mx > x-r && my < y+r  && my > y-r) {
        mouse = true;
      } else {
        mouse = false;
      }
  }
}
