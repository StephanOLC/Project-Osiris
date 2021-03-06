package ingameObjects;

import gameLogic.Vektor;

public interface IngameObject {
	public char getTeam();
	public Vektor getPosition();
	public void tick();
	public void updateGraphic();
}
