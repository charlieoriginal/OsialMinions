package org.osial.osialminions;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.osial.osialminions.minions.MinionHandler;

public final class OsialMinions extends JavaPlugin implements Listener {

    private MinionHandler minionHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        minionHandler = new MinionHandler(this);
        getServer().getPluginManager().registerEvents(minionHandler, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
}
