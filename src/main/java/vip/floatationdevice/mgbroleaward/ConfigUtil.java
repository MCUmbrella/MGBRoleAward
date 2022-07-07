package vip.floatationdevice.mgbroleaward;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static vip.floatationdevice.mgbroleaward.MGBRoleAward.instance;

public class ConfigUtil
{
    static int roleId = 0;
    static int maxRetries = 10;
    static long retryDelaySec = 5;
    static YamlConfiguration cfg = null;

    static void loadConfig()
    {
        File cfgFile = new File(instance.getDataFolder(), "config.yml");
        if(!cfgFile.exists())
        {
            instance.saveDefaultConfig();
        }
        cfg = YamlConfiguration.loadConfiguration(cfgFile);
        roleId = cfg.getInt("roleId", 0);
        if(roleId == 0)
        {
            instance.getLogger().severe("Please set the correct role ID in 'config.yml', then reload the plugin by typing '/mgbroleaward reload'");
            return;
        }
        maxRetries = cfg.getInt("maxRetries", 10);
        retryDelaySec = cfg.getLong("retryDelaySec", 5);
        instance.getLogger().info("Config is valid");
    }
}
