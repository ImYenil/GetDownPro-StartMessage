package net.choco.getdownpro;

import lombok.Getter;
import net.choco.getdownpro.listener.ArenaListener;
import net.choco.getdownpro.manager.FileManager;
import net.choco.getdownpro.manager.GDSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private FileManager fileManager;

    @Getter
    private GDSettings settings;

    @Override
    public void onEnable() {
        instance = this;

        if (!Bukkit.getPluginManager().isPluginEnabled("GetDownPro")) {
            info("GetDownPro was not found. Disabling...");
            this.setEnabled(false);
            return;
        }

        this.fileManager = new FileManager(this);
        this.loadAllConfigs();

        this.settings = new GDSettings(this);
        this.settings.loadSettings();

        Bukkit.getPluginManager().registerEvents(new ArenaListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadAllConfigs() {
        this.fileManager.getConfig("config.yml").copyDefaults(true).save();
        this.fileManager.getConfig("translates.yml").copyDefaults(true).save();
    }

    private void reloadAllConfigs() {
        for (FileManager.Config c : FileManager.configs.values()) {
            c.reload();
        }
    }

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(Main.getInstance().getSettings().getPrefix() + message);
    }
}
