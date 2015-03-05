package com.sucy.skill.cmd;

import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerAccounts;
import com.sucy.skill.api.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A command that allows a player to reset all of their data
 */
public class CmdResetAll implements IFunction
{
    private static final String CANNOT_USE   = "cannot-use";
    private static final String RESET        = "reset";
    private static final String CONFIRM      = "confirm";
    private static final String INSTRUCTIONS = "instructions";
    private static final String DISABLED     = "world-disabled";

    /**
     * Runs the command
     *
     * @param cmd    command that was executed
     * @param plugin plugin reference
     * @param sender sender of the command
     * @param args   argument list
     */
    @Override
    public void execute(ConfigurableCommand cmd, Plugin plugin, CommandSender sender, String[] args)
    {
        // Disabled world
        if (sender instanceof Player && !SkillAPI.getSettings().isWorldEnabled(((Player) sender).getWorld()))
        {
            cmd.sendMessage(sender, DISABLED, "&4You cannot use this command in this world");
        }

        // Only players have profession options
        else if (sender instanceof Player)
        {
            if (args.length == 0 || !args[0].equalsIgnoreCase("confirm"))
            {
                cmd.sendMessage(sender, CONFIRM, ChatColor.DARK_RED + "This will delete all of your account data entirely");
                cmd.sendMessage(sender, INSTRUCTIONS, ChatColor.GRAY + "Type " + ChatColor.GOLD + "/class resetall confirm" + ChatColor.GRAY + " to continue");
            }
            else
            {
                PlayerAccounts data = SkillAPI.getPlayerAccountData((Player) sender);
                for (PlayerData playerData : data.getAllData().values())
                {
                    playerData.resetAll();
                }
                cmd.sendMessage(sender, RESET, ChatColor.DARK_GREEN + "You have reset all of your account data");
            }
        }

        // Console doesn't have profession options
        else
        {
            cmd.sendMessage(sender, CANNOT_USE, ChatColor.RED + "This cannot be used by the console");
        }
    }
}
