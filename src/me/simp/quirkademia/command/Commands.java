package me.simp.quirkademia.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import me.simp.quirkademia.QuirkPlugin;

public class Commands {
	
	private Map<String, QuirkCommand> commandMap;
	private QuirkPlugin plugin;
	
	public Commands(QuirkPlugin plugin) {
		this.plugin = plugin;
		this.commandMap = new HashMap<>();
		this.load();
	}

	private void load() {
		PluginCommand main = plugin.getCommand("quirk");
		
		//load commands
		register(new UserCommand());
		register(new HelpCommand());
		register(new ListCommand());
		
		CommandExecutor exe = new CommandExecutor() {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "For names which have spaces, use '-' or '_' to indicate a space!");
					for (QuirkCommand cmd : commandMap.values()) {
						sender.sendMessage(ChatColor.YELLOW + cmd.getProperUse());
					}
					return true;
				} else {
					for (String sub : commandMap.keySet()) {
						QuirkCommand cmd = commandMap.get(sub);
						
						if (args[0].equalsIgnoreCase(sub) || Arrays.asList(cmd.getAliases()).contains(args[0].toLowerCase())) {
							List<String> sendingArgs = new ArrayList<>();
							
							for (int i = 1; i < args.length; i++) {
								sendingArgs.add(args[i]);
							}
							
							cmd.execute(sender, sendingArgs);
							return true;
						}
					}
				}
				return false;
			}
			
		};
		
		TabCompleter tab = new TabCompleter() {

			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
				List<String> possible = new ArrayList<>();
				
				if (args.length == 1) {
					for (String name : commandMap.keySet()) {
						possible.add(name);
					}
				} else if (args.length > 1) {
					for (String name : commandMap.keySet()) {
						QuirkCommand cmd = commandMap.get(name);
						
						if (args[0].equalsIgnoreCase(name) || Arrays.asList(cmd.getAliases()).contains(args[0].toLowerCase())) {
							List<String> sendingArgs = new ArrayList<>();
							
							for (int i = 1; i < args.length; i++) {
								sendingArgs.add(args[i]);
							}
							
							possible.addAll(cmd.completer(sender, sendingArgs));
						}
					}
				}
				
				String completing = args[args.length - 1];
				Iterator<String> checks = possible.iterator();
				while (checks.hasNext()) {
					String possibility = checks.next();
					
					if (!possibility.regionMatches(true, 0, completing, 0, completing.length())) {
						checks.remove();
					}
				}
				
				return possible;
			}
			
		};
		
		main.setExecutor(exe);
		main.setTabCompleter(tab);
	}
	
	public void register(QuirkCommand command) {
		if (!commandMap.containsKey(command.getName())) {
			commandMap.put(command.getName(), command);
		}
	}
	
	public QuirkCommand getCommand(String name) {
		if (commandMap.containsKey(name)) {
			return commandMap.get(name);
		}
		
		return null;
	}
	
	public Set<QuirkCommand> list() {
		return new HashSet<>(commandMap.values());
	}
}
