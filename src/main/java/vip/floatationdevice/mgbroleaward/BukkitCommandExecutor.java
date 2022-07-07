package vip.floatationdevice.mgbroleaward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BukkitCommandExecutor implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length == 1)
        {
            switch(args[0])
            {
                case "reload": // reload the config
                    if(sender.hasPermission("mgbroleaward.reload"))
                        ConfigUtil.loadConfig();
                    else
                        sender.sendMessage("You don't have permission to reload the config");
                    break;
                case "check": // force a complete check of all Guilded users' roles
                    if(sender.hasPermission("mgbroleaward.check"))
                    {
                        sender.sendMessage("This function is not implemented yet");
                    }
                    else
                        sender.sendMessage("You don't have permission to do a complete role check");
                    break;
                case "help": // show help
                    sender.sendMessage("/mgbroleaward reload - reload the plugin\n/mgbroleaward check - force a complete check of all Guilded users' roles\n/mgbroleaward help - show this message");
                    break;
            }
        }
        return false;
    }
}
