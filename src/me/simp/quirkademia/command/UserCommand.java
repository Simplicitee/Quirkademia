package me.simp.quirkademia.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.FusedQuirk;
import me.simp.quirkademia.quirk.Quirk;
import me.simp.quirkademia.quirk.QuirkUser;

public class UserCommand extends QuirkCommand {

	public UserCommand() {
		super("user", "Command for anything dealing with quirkusers", "/quirk user [player [toggle | set <quirk> | bind <quirk> [slot]]]", new String[] {"user", "u", "player"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender, false)) {
			return;
		} else if (!acceptableLength(sender, args.size(), 0, 4)) {
			return;
		}
		
		if (args.size() == 0) {
			sender.sendMessage(ChatColor.YELLOW + "Online Quirk Users:");
			for (QuirkUser user : plugin.getUserManager().getOnlineUsers()) {
				ChatColor color = ChatColor.WHITE;
				
				if (user.getQuirk() != null) {
					color = user.getQuirk().getChatColor();
				}
				
				sender.sendMessage(color + Bukkit.getPlayer(user.getUniqueId()).getName());
			}
			return;
		}
		
		Player target = Bukkit.getPlayer(args.get(0));
		
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "Player not online!");
		} else {
			QuirkUser user = plugin.getUserManager().getUser(target.getUniqueId());
			
			if (user == null) {
				user = plugin.getUserManager().login(target.getUniqueId());
			}
			
			if (args.size() == 1) {
				sendUserInfo(sender, user);
			} else if (args.size() == 2) {
				if (args.get(1).equalsIgnoreCase("toggle")) {
					String from = "";
					
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (!player.getUniqueId().equals(target.getUniqueId())) {
							if (!sender.hasPermission("quirk.command.user.toggle.others")) {
								sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
								return;
							}
							
							from = " by " + player.getName() + " ";
						}
					} else {
						from = " by CONSOLE ";
					}
					
					String state = "";
					if (user.isQuirkDisabled()) {
						user.enableQuirk();
						state = ChatColor.GREEN + "ON";
					} else {
						user.disableQuirk();
						state = ChatColor.RED + "OFF";
					}
					
					target.sendMessage(ChatColor.YELLOW + "Your quirk has been toggled " + state + ChatColor.YELLOW + from + "!");
				}
			} else if (args.size() == 3) {
				if (args.get(1).equalsIgnoreCase("set")) {
					if (!sender.hasPermission("quirk.command.user.set")) {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
						return;
					}
					
					Quirk quirk = plugin.getQuirkManager().getQuirk(args.get(2).replace("-", " ").replace("_", " "));
					
					if (quirk == null) {
						sender.sendMessage(ChatColor.RED + "Unknown quirk!");
						return;
					}
					
					user.setQuirk(quirk);
					sender.sendMessage(ChatColor.YELLOW + "Set " + target.getName() + "'s quirk to " + quirk.getChatColor() + quirk.getName());
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (!player.getUniqueId().equals(target.getUniqueId())) {
							target.sendMessage(ChatColor.YELLOW + player.getName() + " has set your quirk to " + quirk.getChatColor() + quirk.getName());
						}
					} else {
						target.sendMessage(ChatColor.YELLOW + "CONSOLE has set your quirk to " + quirk.getChatColor() + quirk.getName());
					}
				} else if (args.get(1).equalsIgnoreCase("bind")) {
					Quirk quirk = plugin.getQuirkManager().getQuirk(args.get(2).replace('-', ' ').replace('_', ' '));
					int slot = target.getInventory().getHeldItemSlot();
					
					if (!(user.getQuirk() instanceof FusedQuirk)) {
						sender.sendMessage(ChatColor.RED + "The user's quirk must be a fusion of other quirks to use binds!");
						return;
					} else if (quirk == null) {
						sender.sendMessage(ChatColor.RED + "Unknown quirk!");
						return;
					} else if (!((FusedQuirk) user.getQuirk()).getQuirks().contains(quirk)) {
						sender.sendMessage(ChatColor.RED + "Quirk isn't part of the user's fusion!");
						return;
					} else if (slot < 0 || slot > 9) {
						sender.sendMessage(ChatColor.RED + "Slot must be between 0 and 9 (inclusive)");
						return;
					}
					
					user.setBind(slot, quirk);
					target.sendMessage(sender.getName() + ChatColor.YELLOW + " has set your slot " + (slot + 1) + " to use " + quirk.getName());
					if (sender instanceof Player) {
						if (!target.equals((Player) sender)) {
							sender.sendMessage(ChatColor.YELLOW + "You have set " + target.getName() + "'s slot " + (slot + 1) + " bind to " + quirk.getName());
						}
					}
				}
			} else if (args.size() == 4) {
				if (args.get(1).equalsIgnoreCase("bind")) {
					Quirk quirk = plugin.getQuirkManager().getQuirk(args.get(2).replace('-', ' ').replace('_', ' '));
					int slot = Integer.parseInt(args.get(3));
					
					if (!(user.getQuirk() instanceof FusedQuirk)) {
						sender.sendMessage(ChatColor.RED + "The user's quirk must be a fusion of other quirks to use binds!");
						return;
					} else if (quirk == null) {
						sender.sendMessage(ChatColor.RED + "Unknown quirk!");
						return;
					} else if (!((FusedQuirk) user.getQuirk()).getQuirks().contains(quirk)) {
						sender.sendMessage(ChatColor.RED + "Quirk isn't part of the user's fusion!");
						return;
					} else if (slot < 0 || slot > 9) {
						sender.sendMessage(ChatColor.RED + "Slot must be between 0 and 9 (inclusive)");
						return;
					}
					
					user.setBind(slot - 1, quirk);
					target.sendMessage(sender.getName() + ChatColor.YELLOW + " has set your slot " + slot + " to use " + quirk.getName());
					if (sender instanceof Player) {
						if (!target.equals((Player) sender)) {
							sender.sendMessage(ChatColor.YELLOW + "You have set " + target.getName() + "'s slot " + slot + " bind to " + quirk.getName());
						}
					}
				}
			}
		}
	}
	

	public void sendUserInfo(CommandSender sender, QuirkUser user) {
		Player target = Bukkit.getPlayer(user.getUniqueId());
		Quirk quirk = user.getQuirk();
		String name;
		
		if (quirk == null) {
			name = ChatColor.WHITE + "Quirkless";
		} else {
			name = user.getQuirk().getChatColor() + user.getQuirk().getName();
		}
		
		sender.sendMessage(ChatColor.YELLOW + target.getName());
		sender.sendMessage(ChatColor.YELLOW + "- Quirk: " + name);
		sender.sendMessage(ChatColor.YELLOW + "- Abilities: ");
		
		if (quirk != null) {
			for (QuirkAbilityInfo info : user.getQuirk().getAbilities().values()) {
				sender.sendMessage(ChatColor.YELLOW + "-- " + user.getQuirk().getChatColor() + info.getName());
			}
		}
		
		if (quirk instanceof FusedQuirk) {
			FusedQuirk fq = (FusedQuirk) quirk;
			sender.sendMessage(ChatColor.YELLOW + "Fused Quirks: " + ChatColor.WHITE + fq.getFusionList());
		}
	}

	@Override
	public List<String> completer(CommandSender sender, List<String> args) {
		List<String> completions = new ArrayList<>();
		
		if (args.size() == 1) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				completions.add(player.getName());
			}
		} else if (args.size() == 2) {
			completions.addAll(Arrays.asList("set", "toggle", "bind"));
		} else if (args.size() == 3 && (args.get(1).equalsIgnoreCase("set") || args.get(1).equalsIgnoreCase("bind"))) {
			for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
				completions.add(quirk.getName().replace(" ", "-").toLowerCase());
			}
		} else if (args.size() == 4) {
			completions.addAll(Arrays.asList("123456789".split("")));
		}
		
		return completions;
	}
}
