package ingameObjects;

import java.util.ArrayList;

import gameLogic.CircleHitbox;
import gameLogic.Vektor;
import gameLogic.World;

public class ArakhMummy extends Character implements IngameObject {
	
	public ArakhMummy(Vektor position, World world){
		
		super(position.getX(), position.getY(), "Graphics/icon.png", "ArakhMummy" ,world.getInterface());
		
		this.position = position;
		this.world = world;
		healthPoints = 500;
		speed = 5;
		status = 1;
		
	}
	
	@Override
	public Vektor getPosition() {
		return position;
	}
	
	public char getTeam(){
		return 'e';
	}

	@Override
	public void tick() {
		System.out.println("arakhMummy - position: [" + position.getX() + ", " + position.getY() + direction +"] HP: " + healthPoints + " Status: " + status);
		//collision detection first, movement second, attacking last
		//sets position of graphic object
		updateGraphic();
		if(timer >= 5 && status == 0) world.deathNote(this);
		collision(world.detectCollissionType(position));
		alreadyMoved = false;
		timer++;
		
		//now actions ->
		if(world.getClosest('h', position) != null && status != 0) jumpAttack(world.getClosest('n', position));
		
		
	}

	public void collision(ArrayList<Integer> collisions) {
		for(int effect : collisions){
			switch (effect){
				case 0: position = previousPosition;
				break;
				
				default: healthPoints = healthPoints - effect; 
						if(healthPoints <= 0){
							status = 0;
							timer = 0;
						}
				break;
			}
		}
	}
	
	private Vektor jumpTo(Vektor target){
		Vektor way = position.connectingTo(target);
		return way.scale(3*speed/(way.length()));
	}
	
	private Vektor rollAway(Vektor target){
		Vektor way = target.connectingTo(position);
		return way.scale(2*speed/(way.length()));
	}
	
	private void jumpAttack(Vektor target){
		if(position.connectingTo(target).length() < 1.5*speed){
			world.addHitbox(new CircleHitbox(position.plus(position.connectingTo(target)), 2, 10000, 1));
			status = 4;
			System.out.println("Hitbox created at x: " + position.plus(position.connectingTo(target)).getX() + " y: " + position.plus(position.connectingTo(target)).getY() + " Radius: " + (speed-1));
		}
		else if(position.connectingTo(target).length() < 6*speed){
			System.out.println("jumping...");
			movement(jumpTo(target));
			status = 3;
		}
		else{
			movement(goTo(target));
			status = 2;
		}
	}

	@Override
	public void updateGraphic() {
		setPosition(position.getX(), position.getY());
	}
}
