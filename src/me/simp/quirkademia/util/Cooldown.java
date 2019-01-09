package me.simp.quirkademia.util;

public class Cooldown {

	private long start, duration;
	
	public Cooldown(long start, long duration) {
		this.start = start;
		this.duration = duration;
	}
	
	public long getStartTime() {
		return start;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public long getEndTime() {
		return start + duration;
	}
	
	public long getRemaining() {
		return getEndTime() - System.currentTimeMillis();
	}
}
