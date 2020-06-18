package com.sameer.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.security.Key;
import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture birds[];
	Texture topTube,BottomTube;
	Texture gameOver;
	Circle birdCircle;
    Rectangle topTubeRectangle[];
    Rectangle bottomTubeRectangle[];
	BitmapFont font;

    //ShapeRenderer shapeRenderer;
	int flag=0;
	int birdY=0;
	int gameState=0;
	int velocity=0;
	int gravity=2;
	float gap=600;
	int score=0;
	int currentTube=0;
    int tubeVelocity=4;
    int noOfTubes=4;

    int tubeX[]=new int[noOfTubes];
    float offSet[]=new float[noOfTubes];
    int distanceBetweenTubes;
    Random randomGenrator;
    int reStart=0;
	@Override
	public void create ()
    {
		batch = new SpriteBatch();
	    birdCircle=new Circle();
        topTubeRectangle=new Rectangle[noOfTubes];
        bottomTubeRectangle=new Rectangle[noOfTubes];
   font=new BitmapFont();
   font.setColor(Color.WHITE);
   font.getData().setScale(10f);


	 //   shapeRenderer=new ShapeRenderer();
	    background=new Texture("bg.png");
		gameOver=new Texture("gameover.png");

		birds=new Texture [2];
	    birds[0]= new Texture("bird.png");
		birds[1]= new Texture("bird2.png");
	    topTube=new Texture("toptube.png");
		BottomTube=new Texture("bottomtube.png");
        randomGenrator=new Random();
        distanceBetweenTubes=Gdx.graphics.getWidth()/2;
        gameStart();

    }
public void gameStart()
{
	birdY=Gdx.graphics.getHeight()/2-birds[flag].getHeight();

	for(int i=0;i<noOfTubes;i++)
	{
		offSet[i]=(randomGenrator.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-300);
		tubeX[i]=Gdx.graphics.getWidth()/2-topTube.getWidth()/2 +Gdx.graphics.getWidth()+i*distanceBetweenTubes;

		topTubeRectangle[i]=new Rectangle();
		bottomTubeRectangle[i]=new Rectangle();

	}



}

	@Override
	public void render()
	{

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

	  if(gameState==1)
     	  {

     	  	if(tubeX[currentTube]<Gdx.graphics.getWidth()/2-birds[flag].getWidth())
			{
				score++;

				if(currentTube<noOfTubes-1)
				{
					currentTube++;

				}
				else {
					currentTube=0;
				}


			}

              if(Gdx.input.justTouched())
              {
                   velocity=-30;

              }
              for(int i=0;i<noOfTubes;i++)
              {
                 if(tubeX[i]<-topTube.getWidth())
                 {
                     tubeX[i]=noOfTubes*distanceBetweenTubes;
                 }
                   else {
                     tubeX[i] = tubeX[i] - tubeVelocity;
                 }

                   batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + offSet[i]);
                   batch.draw(BottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap - BottomTube.getHeight() / 2 - gap / 2 + offSet[i]);

				  topTubeRectangle[i]=new Rectangle( tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + offSet[i],topTube.getWidth(),topTube.getHeight());
				  bottomTubeRectangle[i]=new Rectangle( tubeX[i], Gdx.graphics.getHeight() / 2 - gap - BottomTube.getHeight() / 2 - gap / 2 + offSet[i],BottomTube.getWidth(),BottomTube.getHeight());



			  }
			  if(birdY>0||velocity<0)
			  {
				  velocity = velocity+gravity;
				  birdY = birdY - velocity;
			  }
          }
	     else if (gameState==0)
	     {
          if(Gdx.input.justTouched())
              {
              gameState=1;
              }
	     }
	     else if (gameState==2)
	         {

 	  	batch.draw(gameOver,Gdx.graphics.getWidth()/2-gameOver.getWidth()/2,Gdx.graphics.getHeight()/2-gameOver.getHeight()/2);
				birdY-=10;
	          if(birdY<0) {
				  if (Gdx.input.justTouched()) {

					  gameState = 1;
					  gameStart();
					  velocity = 0;
					  currentTube = 0;
					  score = 0;

				  }
			  }
	         }

		if(flag==0)
		{
			flag=1;
		}else {
			flag=0;
		}
	    batch.draw(birds[flag],Gdx.graphics.getWidth()/2-birds[flag].getWidth(),birdY);
         font.draw(batch,String.valueOf(score),100,200);

		batch.end();

	    birdCircle.set(Gdx.graphics.getWidth()/2-birds[flag].getWidth()/2,birdY+birds[flag].getHeight()/2,birds[flag].getWidth()/2);

	     //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	     //shapeRenderer.setColor(Color.RED);
	     //shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
           for(int i=0;i<noOfTubes;i++)
		   {
		//   	shapeRenderer.rect( tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + offSet[i],topTube.getWidth(),topTube.getHeight());
		 //  	shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap - BottomTube.getHeight() / 2 - gap / 2 + offSet[i],BottomTube.getWidth(),BottomTube.getHeight());


		   	if((Intersector.overlaps(birdCircle,topTubeRectangle[i]))||(Intersector.overlaps(birdCircle,bottomTubeRectangle[i])))
			   {
			   	gameState=2;
        	   }



		   }


         // shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		batch.dispose();
	}
}
