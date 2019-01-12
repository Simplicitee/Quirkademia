package me.simp.quirkademia.ability;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.simp.quirkademia.QuirkPlugin;
import me.simp.quirkademia.quirk.QuirkUser;
import me.simp.quirkademia.util.ActivationType;

public class AbilityBoard {
	
	private QuirkUser user;
	private Player player;
	private Scoreboard board;
	
	public AbilityBoard(QuirkUser user) {
		this.user = user;
		this.player = Bukkit.getPlayer(user.getUniqueId());
		this.board = QuirkPlugin.get().getServer().getScoreboardManager().getNewScoreboard();
		this.update();
	}
	
	public QuirkUser getUser() {
		return user;
	}
	
	public void update() {
		Objective o = board.registerNewObjective("abilities", "dummy", "Quirk Abilities");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		int i = 0;
		for (ActivationType type : user.getQuirk().getAbilities().keySet()) {
			QuirkAbilityInfo info = user.getQuirk().getAbilities().get(type);
			Score s = o.getScore(info.getName());
			s.setScore(i);
			i++;
		}
		
		player.setScoreboard(board);
	}
	
	public void destroy() {
		player.setScoreboard(QuirkPlugin.get().getServer().getScoreboardManager().getMainScoreboard());
	}
}
