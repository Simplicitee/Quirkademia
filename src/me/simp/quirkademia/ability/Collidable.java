package me.simp.quirkademia.ability;

public interface Collidable {

	public double getRadius();
	public boolean onCollision(QuirkAbility other);
	
}
