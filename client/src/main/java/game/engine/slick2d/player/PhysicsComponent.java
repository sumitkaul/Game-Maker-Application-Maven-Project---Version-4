package game.engine.slick2d.player;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
//import org.jbox2d.testbed.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.util.Log;

public class PhysicsComponent {

	
	public static World world;
	public static Set<Body> bodies;
	private float colorRed;
	private float colorBlue;
	private float colorGreen;
   public PhysicsComponent() throws IOException
   {
	   this.world=new World(new Vec2(0,9.8f),false);
		this.bodies=new HashSet<Body>();
		randomColors();
		setUpBox();
		setUpGround();
   }
   
   public void setUpGround()
	{
		BodyDef bodyDef =new BodyDef();
		bodyDef.position.set(0,0);
		bodyDef.type=BodyType.STATIC;
		PolygonShape boxShape=new PolygonShape();
		boxShape.setAsBox(1000,0);
		Body box=world.createBody(bodyDef);
		FixtureDef boxFixture=new FixtureDef();
		boxFixture.density=0.5f;
	    boxFixture.shape=boxShape;
		box.createFixture(boxFixture);
		bodies.add(box); 
	}
	
	public void setUpBox() throws IOException
	{
		
		BodyDef box1Def =new BodyDef();
		box1Def.position.set(500,500);
		
		box1Def.type=BodyType.DYNAMIC;
	    PolygonShape groundShape=new PolygonShape();
		groundShape.setAsBox(0.75f,0.75f);
		
		Body ground=world.createBody(box1Def);
		FixtureDef groundFixture=new FixtureDef();
		groundFixture.restitution=0.3f;
		groundFixture.friction=0.3f;
		groundFixture.shape=groundShape;
		ground.createFixture(groundFixture);
		bodies.add(ground);
		
	}
	
	
	public void moveLogic()
	{
		world.step(1/60f,8,3);
	}
	
	public void inputLogic()
	{
		for(Body body:bodies)
		{
			if(body.getType()==BodyType.DYNAMIC)
			{
				mouseInputLogic(body);
				keyboardInputLogic(body);
			}
		}
		
	}
	
	public void keyboardInputLogic(Body body)
	{
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A) &&  !Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			body.applyAngularImpulse(+0.1f);
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_A) &&  Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			body.applyAngularImpulse(-0.1f);
		}
		
	}
	
	public void randomColors() {
		Random randomGenerator = new Random();
		colorRed = randomGenerator.nextFloat();
		colorBlue = randomGenerator.nextFloat();
		colorGreen = randomGenerator.nextFloat();

	}

	
	public void mouseInputLogic(Body body)
	{
		if(Mouse.isButtonDown(0))
		{
			Vec2 mousePosition=new Vec2(Mouse.getX(),Mouse.getY()).mul(0.5f).mul(1/10f);
			Vec2 bodyPosition=body.getPosition();
			Vec2 force=mousePosition.sub(bodyPosition);
			body.applyForce(force,body.getPosition());
		}
		
		
	}
	
	public void drawBoxes()
	{
		
	    inputLogic();
        moveLogic();
        
		for(Body body: bodies)
		{
			if(body.getType()==BodyType.DYNAMIC)
			{
				glPushMatrix();
				
				Vec2 bodyPosition=body.getPosition();
				Log.debug("Position X:"+String.valueOf(bodyPosition.x)+"Position Y:"+String.valueOf(bodyPosition.y));
				glTranslatef(bodyPosition.x,bodyPosition.y,0);
				glRotated(Math.toDegrees(body.getAngle()),0,0,1);
				glRectf(-0.2f * 30,-0.2f * 30,0.2f * 30,0.2f * 30);
			    glPopMatrix();
			}
		}
	}
	
}
