package minecade.dungeonrealms.GuildMechanics.commands;

import minecade.dungeonrealms.GuildMechanics.GuildMechanics;
import minecade.dungeonrealms.PermissionMechanics.PermissionMechanics;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGSetRank implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player pl = (Player)sender;
		if (pl == null) return true;

		if (!pl.isOp() || !PermissionMechanics.isGM(pl.getName())) return true;

		if (args.length == 0 || args.length > 2) {
			pl.sendMessage(ChatColor.RED + "Invalid syntax. Player also must be in a guild. \u00A7n/gsetrank <PLAYER> <1-4> \u00A7cor \u00A7n/gsetrank <1-4> \u00A7cif you're setting yourself.");
			return true;
		}

		if ((args.length == 2 && !isInt(args[1])) || (args.length == 1 && !isInt(args[0]))) {
			pl.sendMessage("Your rank parameter must be an integer!");
			return true;
		}

		if (args.length == 1) {
			if (Integer.parseInt(args[0]) > 4 || Integer.parseInt(args[0]) < 1) {
				pl.sendMessage(ChatColor.RED + "Please choose a rank between \u00A7n1 \u00A7cand \u00A7n4."); // \u00A7 = § - too lazy to use ChatColor, takes up less space as well.
				return true;
			}
			if (GuildMechanics.inGuild(pl.getName())) {
				GuildMechanics.setGuildRank(pl.getName(), Integer.parseInt(args[0]));
				String rank = "";
				switch (Integer.parseInt(args[0])) {
				case 1:
					rank = "Member";
					break;
				case 2:
					rank = "Officer";
					break;
				case 3:
					rank = "Co-Owner";
					break;
				case 4:
					rank = "Owner";
					break;
				default: 
					break;
				}
				pl.sendMessage(ChatColor.RED + "You set " + ChatColor.UNDERLINE + args[0] + ChatColor.RED + " to the rank " + ChatColor.UNDERLINE + rank);
				return true;
			}
			pl.sendMessage(ChatColor.RED + "You aren't in a guild.");
		}

		if (args.length == 2) {
			if (Integer.parseInt(args[1]) > 4 || Integer.parseInt(args[1]) < 1) {
				pl.sendMessage(ChatColor.RED + "Please choose a rank between \u00A7n1 \u00A7cand \u00A7n4."); // \u00A7 = § - too lazy to use ChatColor, takes up less space as well.
				return true;
			}
			if (GuildMechanics.inGuild(args[0])) {
				GuildMechanics.setGuildRank(args[0], Integer.parseInt(args[1]));
				String rank = "";
				switch (Integer.parseInt(args[1])) {
				case 1:
					rank = "Member";
					break;
				case 2:
					rank = "Officer";
					break;
				case 3:
					rank = "Co-Owner";
					break;
				case 4:
					rank = "Owner";
					break;
				default: 
					break;
				}
				pl.sendMessage(ChatColor.RED + "You set " + ChatColor.UNDERLINE + args[0] + ChatColor.RED + " to the rank " + ChatColor.UNDERLINE + rank);
				return true;
			}
			pl.sendMessage(ChatColor.RED + "That user isn't in a guild.");
		}
		return true;
	}

	private boolean isInt(String integer) {
		try {
			Integer.parseInt(integer);
		} catch (NumberFormatException ex) { 
			return false;
		}
		return true;
	}
}