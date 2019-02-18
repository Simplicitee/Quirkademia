package me.simp.quirkademia.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;

public abstract class DisplayBoard {
	
	protected QuirkUser user;
	protected Player player;
	protected Scoreboard board;

	public DisplayBoard(QuirkUser user) {
		this.user = user;
		this.player = Bukkit.getPlayer(user.getUniqueId());
		this.board = QuirkPlugin.get().getServer().getScoreboardManager().getNewScoreboard();
	}
	
	public QuirkUser getUser() {
		return user;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Scoreboard getBoard() {
		return board;
	}
	
	public abstract void update();
	
	public void destroy() {
		player.setScoreboard(QuirkPlugin.get().getServer().getScoreboardManager().getMainScoreboard());
	}
}
