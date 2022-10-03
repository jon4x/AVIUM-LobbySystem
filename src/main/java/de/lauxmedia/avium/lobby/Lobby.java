package de.lauxmedia.avium.lobby;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.NPCPool;
import de.lauxmedia.avium.lobby.commands.*;
import de.lauxmedia.avium.lobby.listener.*;
import de.lauxmedia.avium.lobby.utils.NpcManager;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;

    private static NPCPool npcPool;
    private static FileConfiguration customNPCConfig;
    private final File customNPCYml = new File(getDataFolder()+"/npcs.yml");


    @Override
    public void onEnable() {
        // instance
        instance = this;
        // register Events
        registerEvents();
        // register Commands
        registerCommands();
        // load config
        loadConfigs();
        // create NPC Pool
        createNPCs();
    }

    // Plugin shutdown logic
    @Override
    public void onDisable() {
        // remove NPCs
        for (NPC npc : getNpcPool().getNPCs()) {
            getServer().getConsoleSender().sendMessage("Found NPC: " + npc.getProfile().getName());
            npc.visibility().queueDestroy().send();
        }
    }

    // Register Events
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new NavigatorListener(), this);
        pluginManager.registerEvents(new ProfileListener(), this);
        pluginManager.registerEvents(new VisibilityListener(), this);
        pluginManager.registerEvents(new ProtectListener(), this);
        pluginManager.registerEvents(new UtilListener(), this);
        pluginManager.registerEvents(new DoubleJumpListener(), this);
        pluginManager.registerEvents(new NpcListener(), this);
        pluginManager.registerEvents(new EnderPearlListener(), this);
        pluginManager.registerEvents(new LobbySwitcherListener(), this);
    }

    // Register Commands
    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("killall")).setExecutor(new KillAllCommand());
        Objects.requireNonNull(this.getCommand("setspawn")).setExecutor(new SetSpawnCommand());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(this.getCommand("build")).setExecutor(new BuildCommand());
        Objects.requireNonNull(this.getCommand("setwarp")).setExecutor(new SetWarpCommand());
        Objects.requireNonNull(this.getCommand("warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(this.getCommand("createnpc")).setExecutor(new CreateNpcCommand());
        Objects.requireNonNull(this.getCommand("editnpc")).setExecutor(new EditNpcCommand());
    }

    // load Config
    private void loadConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        // custom NPC config
        File customYml = new File(getDataFolder()+"/npcs.yml");
        customNPCConfig = YamlConfiguration.loadConfiguration(customYml);
        Utility.saveCustomYml(customNPCConfig, customYml);
    }

    private void createNPCs() {
        // create NPC Pool
        npcPool = NPCPool.builder(getInstance())
                .spawnDistance(60)
                .actionDistance(40)
                .tabListRemoveTicks(20)
                .build();
        // load NPCs
        Bukkit.getScheduler().scheduleAsyncDelayedTask(this, () -> NpcManager.getNpcsFromConfig(), 60L);
    }

    // Getter
    public static Lobby getInstance() {
        return instance;
    }

    public File getCustomNPCYml() {
        return customNPCYml;
    }

    public static NPCPool getNpcPool() {
        return npcPool;
    }

    public static FileConfiguration getCustomNpcConfig() {
        return customNPCConfig;
    }
}
