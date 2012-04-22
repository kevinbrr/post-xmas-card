class Kerstboom
{
  int x;
  float c1;      
  float c2; 
  float c3;
  float c1dir;  
  float c2dir; 
  float c3dir;
  int r;
  
  Kerstboom ()
  {
  x = 150;
  c1 = 0;      
  c2 = 255; 
  c3 = 123;
  c1dir = 1.5;  
  c2dir = -1.5; 
  c3dir = 0.5;
  r = 3;
  }
    
  void display() {
    //kerstboom
    background(0);
    //stam of potje
    strokeWeight(2);
    stroke(128,64,0);
    fill(200,128,0);
    rect(x-10,height-20,20,40);
    
    //onderkantboom
    stroke(255);
    fill(0, 128,0);
    beginShape();
      vertex(x,height - 100);
      vertex(x-40,height-20);
      vertex(x+40,height-20);
    endShape(CLOSE);
      
    stroke(255);
    fill(0,128,0);
    beginShape();
      vertex(x,height-100);
      vertex(x-35,height-40);
      vertex(x+35,height-40);
    endShape(CLOSE);
    //topje
    beginShape();
      vertex(x, height-100);
      vertex(x-30,height-60);
      vertex(x+30,height-60);
    endShape(CLOSE);
    
    //lichtjes
    noStroke();
    fill(c1,c2,c3);
    ellipse (x,height - 85, 3,3);
    fill(c3,c1,c2);
    ellipse (x-13,height - 72, r,r);
    fill(c1,c1,c2);
    ellipse (x+13,height - 72, r,r);
    fill(c2,c1,c2);
    ellipse (x,height - 70, r,r);
   
    fill(c3,c2,c1);
    ellipse (x+17,height - 55, r,r);
    fill(c2,c1,c3);
    ellipse (x-17,height - 55, r,r);
    fill(c1,c1,c1);
    ellipse (x-9,height - 52, r,r);
    fill(c2,c3,c1);
    ellipse (x+4,height - 50, r,r);
    
    fill(c1,c3,c2);
    ellipse (x-25,height - 35, r,r);
    fill(c2,c2,c1);
    ellipse (x+25,height - 35, r,r);
    fill(c3,c1,c3);
    ellipse (x-13,height - 30, r,r);
    fill(c2,c1,c1);
    ellipse (x+12,height - 30, r,r);
    fill(c3,c3,c3);
    ellipse (x-1,height - 28, r,r);
    
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
    
     if (keyPressed && (key == CODED)) { 
       if (keyCode == LEFT) { // 
         x--;
       } else if (keyCode == RIGHT) { // 
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


