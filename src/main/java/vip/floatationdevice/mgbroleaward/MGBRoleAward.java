package vip.floatationdevice.mgbroleaward;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import vip.floatationdevice.mgbridge.MGBridge;
import vip.floatationdevice.mgbridge.event.UserBoundEvent;
import vip.floatationdevice.mgbridge.event.UserUnboundEvent;

import static vip.floatationdevice.mgbroleaward.ConfigUtil.loadConfig;
import static vip.floatationdevice.mgbroleaward.ConfigUtil.roleId;
import static vip.floatationdevice.mgbroleaward.RoleUtil.giveRole;
import static vip.floatationdevice.mgbroleaward.RoleUtil.removeRole;

public final class MGBRoleAward extends JavaPlugin implements Listener
{
    static MGBRoleAward instance = null;

    @Override
    public void onEnable()
    {
        instance = this;
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("mgbroleaward").setExecutor(new BukkitCommandExecutor());
    }

    @Override
    public void onDisable()
    {
        // nothing to do
    }

    @EventHandler
    public void onUserBound(UserBoundEvent event)
    {
        getLogger().info("Guilded user bound: ID " + event.getUserId() + " to player " + MGBridge.getPlayerName(event.getPlayerUUID()));
        if(roleId != 0) giveRole(event.getUserId(), roleId, event.getPlayerUUID());
        else getLogger().severe("Role ID is not set in 'config.yml', please set it and reload the plugin");
    }

    @EventHandler
    public void onUserUnbound(UserUnboundEvent event)
    {
        getLogger().info("Guilded user unbound: ID " + event.getUserId() + " from player " + MGBridge.getPlayerName(event.getPlayerUUID()));
        if(roleId != 0) removeRole(event.getUserId(), roleId, event.getPlayerUUID());
        else getLogger().severe("Role ID is not set in 'config.yml', please set it and reload the plugin");
    }
}
