package me.simp.quirkademia.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.simp.quirkademia.ability.QuirkAbilityInfo;
import me.simp.quirkademia.quirk.FusedQuirk;
import me.simp.quirkademia.quirk.Quirk;

public class FuseCommand extends QuirkCommand {

	public FuseCommand() {
		super("fusion", "Fuse quirks together! Cannot fuse a quirk with itself! Cannot fuse with another fusion!", "/quirk fusion <fusion name> [create | add <quirk> | remove <quirk> | delete]", new String[] {"fusion", "fuse", "f", "combine", "merge"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender, false)) {
			return;
		} else if (!acceptableLength(sender, args.size(), 1, 3)) {
			return;
		}
		
		String name = args.get(0).replace("-", " ").replace("_", " ");
		Quirk quirk = plugin.getQuirkManager().getQuirk(name);
		
		if (args.size() == 1) {
			if (quirk == null) {
				sender.sendMessage(ChatColor.RED + "Unknown quirk!");
				return;
			} else if (!(quirk instanceof FusedQuirk)) {
				sender.sendMessage(ChatColor.RED + "That quirk isn't a fusion!");
				return;
			}
			
			FusedQuirk fusion = (FusedQuirk) quirk;
			
			sender.sendMessage(ChatColor.YELLOW + "Fusion: " + fusion.getChatColor() + fusion.getName());
			
			sender.sendMessage(ChatColor.YELLOW + "Fused Quirks:");
			for (Quirk q : fusion.getQuirks()) {
				sender.sendMessage(ChatColor.YELLOW + "- " + q.getChatColor() + q.getName());
			}
			
			sender.sendMessage(ChatColor.YELLOW + "Fusion Abilities:");
			for (QuirkAbilityInfo info : fusion.getAbilities().values()) {
				sender.sendMessage(ChatColor.YELLOW + "- " + fusion.getChatColor() + info.getName());
			}
		} else {
			if (args.get(1).equalsIgnoreCase("create")) {
				if (args.size() > 2) {
					sender.sendMessage(ChatColor.RED + "Incorrect Usage!");
					return;
				} else if (quirk != null) {
					sender.sendMessage("Quirk already exists!");
					return;
				}
				
				String[] split = name.split(" ");
				String newName = "";
				for (int i = 0; i < split.length; i++) {
					newName = (newName.equals("") ? "" : newName + " ") + split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
				}
				
				FusedQuirk fusion = new FusedQuirk(newName);
				plugin.getQuirkManager().register(plugin, fusion);
				sender.sendMessage(ChatColor.YELLOW + "Created new fusion: " + ChatColor.WHITE + fusion.getName());
			} else if (args.get(1).equalsIgnoreCase("delete")) {
				if (args.size() > 2) {
					sender.sendMessage(ChatColor.RED + "Incorrect Usage!");
					return;
				} else if (quirk == null) {
					sender.sendMessage(ChatColor.RED + "Cannot delete non-existent quirk!");
					return;
				} else if (!(quirk instanceof FusedQuirk)) {
					sender.sendMessage(ChatColor.RED + "Cannot delete non-fusion quirk!");
					return;
				}
				
				FusedQuirk fusion = (FusedQuirk) quirk;
				if (plugin.getQuirkManager().deleteFusion(fusion)) {
					sender.sendMessage(ChatColor.YELLOW + "Deleted fusion: " + ChatColor.WHITE + fusion.getName());
				} else {
					sender.sendMessage(ChatColor.RED + "Failed to delete fusion!");
				}
			} else if (args.get(1).equalsIgnoreCase("add")) {
				if (args.size() < 3) {
					sender.sendMessage(ChatColor.RED + "Incorrect Usage!");
					return;
				} else if (quirk == null) {
					sender.sendMessage(ChatColor.RED + "Unknown fusion!");
					return;
				} else if (!(quirk instanceof FusedQuirk)) {
					sender.sendMessage(ChatColor.RED + "That quirk isn't a fusion!");
					return;
				}
				
				FusedQuirk fusion = (FusedQuirk) quirk;
				Quirk selected = plugin.getQuirkManager().getQuirk(args.get(2).replace("-", " ").replace("_", " "));
				
				if (selected instanceof FusedQuirk) {
					sender.sendMessage(ChatColor.RED + "The selected quirk cannot be a fusion!");
					return;
				}
				
				if (!fusion.getQuirks().contains(selected)) {
					fusion.addQuirk(selected);
					sender.sendMessage(selected.getChatColor() + selected.getName() + ChatColor.YELLOW + " was added to " + fusion.getChatColor() + fusion.getName());
				} else {
					sender.sendMessage(ChatColor.RED + "Fusion already contains that quirk!");
				}
			} else if (args.get(1).equalsIgnoreCase("remove")) {
				if (args.size() < 3) {
					sender.sendMessage(ChatColor.RED + "Incorrect Usage!");
					return;
				} else if (quirk == null) {
					sender.sendMessage(ChatColor.RED + "Unknown fusion!");
					return;
				} else if (!(quirk instanceof FusedQuirk)) {
					sender.sendMessage(ChatColor.RED + "That quirk isn't a fusion!");
					return;
				}
				
				FusedQuirk fusion = (FusedQuirk) quirk;
				Quirk selected = plugin.getQuirkManager().getQuirk(args.get(2).replace("-", " ").replace("_", " "));
				
				if (selected instanceof FusedQuirk) {
					sender.sendMessage(ChatColor.RED + "The selected quirk cannot be a fusion!");
					return;
				}
				
				if (fusion.getQuirks().contains(selected)) {
					fusion.removeQuirk(selected);
					sender.sendMessage(selected.getChatColor() + selected.getName() + ChatColor.YELLOW + " was removed from " + fusion.getChatColor() + fusion.getName());
				} else {
					sender.sendMessage(ChatColor.RED + "Fusion does not contain that quirk!");
				}
			}
		}
	}

	@Override
	public List<String> completer(CommandSender sender, List<String> args) {
		List<String> completions = new ArrayList<>();
		
		if (args.size() == 3) {
			for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
				if (!(quirk instanceof FusedQuirk)) {
					completions.add(quirk.getName().replace(" ", "-"));
				}
			}
		} else if (args.size() == 2) {
			completions.addAll(Arrays.asList("create", "add", "remove", "delete"));
		} else if (args.size() == 1) {
			for (Quirk quirk : plugin.getQuirkManager().getQuirks()) {
				if (quirk instanceof FusedQuirk) {
					completions.add(quirk.getName().replace(" ", "-"));
				}
			}
		}
		
		return completions;
	}

}
