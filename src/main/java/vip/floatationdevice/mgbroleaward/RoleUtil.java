package vip.floatationdevice.mgbroleaward;

import org.bukkit.Bukkit;
import vip.floatationdevice.mgbridge.MGBridge;

import java.util.UUID;

import static vip.floatationdevice.mgbroleaward.ConfigUtil.maxRetries;
import static vip.floatationdevice.mgbroleaward.ConfigUtil.retryDelaySec;
import static vip.floatationdevice.mgbroleaward.MGBRoleAward.instance;

public class RoleUtil
{
    public static void giveRole(String userId, int roleId, UUID uuid)
    {
        Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable()
        {
            @Override
            public void run()
            {
                instance.getLogger().info("Guilded user bound: ID " + userId + " to player " + MGBridge.getPlayerName(uuid));
                // give the role to the user
                for(int i = 0; i != maxRetries; i++)
                {
                    try
                    {
                        MGBridge.instance.getG4JClient().getRoleManager().addRoleMember(MGBridge.getServerId(), roleId, userId);
                        instance.getLogger().info("Role " + roleId + " added to user " + userId);
                        return;
                    }
                    catch(Exception e)
                    {
                        instance.getLogger().warning("Failed to give role to user with ID " + userId + ": " + e.getMessage() + " (try " + (i + 1) + "/" + maxRetries + ")");
                        Bukkit.getScheduler().runTaskLater(instance, this, retryDelaySec * 20);
                    }
                }
                // max retries reached, give up
                instance.getLogger().severe("Failed to give role to user ID " + userId + " (bound to player " + MGBridge.getPlayerName(uuid) + ") after " + maxRetries + " retries");
            }
        });
    }

    public static void removeRole(String userId, int roleId, UUID uuid)
    {
        Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable()
        {
            @Override
            public void run()
            {
                instance.getLogger().info("Guilded user unbound: ID " + userId + " from player " + MGBridge.getPlayerName(uuid));
                // remove the role from the user
                for(int i = 0; i != maxRetries; i++)
                {
                    try
                    {
                        MGBridge.instance.getG4JClient().getRoleManager().removeRoleMember(MGBridge.getServerId(), roleId, userId);
                        instance.getLogger().info("Role " + roleId + " removed from user " + userId);
                        return;
                    }
                    catch(Exception e)
                    {
                        instance.getLogger().warning("Failed to remove role from user with ID " + userId + ": " + e.getMessage() + " (try " + (i + 1) + "/" + maxRetries + ")");
                        Bukkit.getScheduler().runTaskLater(instance, this, retryDelaySec * 20);
                    }
                }
                // max retries reached, give up
                instance.getLogger().severe("Failed to remove role from user ID " + userId + " (bound to player " + MGBridge.getPlayerName(uuid) + ") after " + maxRetries + " retries");
            }
        });
    }
}
