import ddf.minim.*;
Minim minim;
AudioPlayer player;

Star[] stars = new Star[25];
Kerstboom kerstboom;

PFont fontA;
float x = width+200; 
float y = 150;

void setup() {
  size(400,400);  
  background(0);
  smooth();
  
  fontA = loadFont("Georgia-BoldItalic-20.vlw");
  textFont(fontA, 20);  
  
  kerstboom = new Kerstboom();
  
  for (int i = 0; i < stars.length; i ++ ) {    
    stars[i] = new Star();  
  }
   minim = new Minim(this);
   player = minim.loadFile("jinglebells.wav", 8192);
   player.play();
   player.loop();
}

void draw() {
   
  background(0);
  smooth();
  kerstboom.display();
  fill(255,0,0);
  float txtSpeed = 1.5;
  x -= txtSpeed;
  if (x < -200)
  {
    x = width;
    y = random(50,200);
  }
   text("Jingle Bells!",x,y);
 
  for (int i=0; i < stars.length; i++ ) {    
    stars[i].rollover(mouseX,mouseY); // Passing the mouse coordinates into an object.
    stars[i].display();    
  }  
}

void stop()
{
  player.close();  
  minim.stop();  
  super.stop();
}



