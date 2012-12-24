package kb.postxmascard;

import processing.core.PApplet;
import processing.core.*;
import ddf.minim.*;



public class SoundEngine {
	PostXmasCard app;
	
	Minim minim;
	AudioPlayer player;
	AudioMetaData meta;
	AudioInput input;	

	AudioPlayer christSound;
	AudioSample shootSound;
	AudioSample starSound;	
	AudioSample resetSound;
	
	AudioSample test[];
	String test_samples[]= {
		//"dump.wav",
		//"MBd6.wav",
		//"tingplop.wav", 
		// "glasting.wav",
		// "glasting_5ST.wav",
		"glasting_down.wav"
	};	
	
	public SoundEngine(PostXmasCard p) {
		app = p;
		minim = new Minim(app);				
		
		//load samples
		shootSound = app.minim.loadSample("bulletSound.wav", 2048);
		//shootSound = app.minim.loadSample("shot.wav", 2048);
		christSound = app.minim.loadFile("hallelujah.wav", 4096);		
		resetSound = app.minim.loadSample("jinglebells.wav", 2048);	
		
		starSound = app.minim.loadSample("glasting_down.wav", 2048);
		
		test = new AudioSample[test_samples.length];
		for (int i=0; i <test_samples.length; i++) {
			test[i] = app.minim.loadSample(test_samples[i], 2048);
		}		
	}		

	public void playChristSound() {
		
		if ( !christSound.isPlaying() ) {
			christSound.rewind();
			christSound.play();					
		}		
	}	
	
	public void starSound() {
		int r = (int) app.random(test.length);
		test[r].trigger();		
	}
	
	public void stop(){
		christSound.close();
		shootSound.close();
		starSound.close();
		resetSound.close();
		
		//player.close();
		//minim.stop();
		//super.stop();
	}
}