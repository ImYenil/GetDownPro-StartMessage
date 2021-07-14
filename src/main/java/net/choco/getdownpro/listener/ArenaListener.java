package net.choco.getdownpro.listener;

import net.choco.getdown.api.event.GDGameStateSwitchEvent;
import net.choco.getdown.objects.arena.GDArena;
import net.choco.getdown.objects.arena.GDArenaState;
import net.choco.getdownpro.Main;
import net.choco.getdownpro.message.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {

    @EventHandler
    public void onArenaStart(GDGameStateSwitchEvent e) {
        String arenaName = e.getArena().getName();
        World lobby = Bukkit.getWorld(Main.getInstance().getSettings().getMainWorld());
        GDArena gdArena = e.getArena();
        if (gdArena.getGDArenaState() == GDArenaState.LOBBY) {
            if (gdArena.getPlayers().size() >= gdArena.getMinPlayers()) {
                lobby.getPlayers().forEach(player -> player.sendMessage(""));
                TextComponent textComponent = new TextComponent(Message.START_TEXT.getMessageWithPrefix().replace("{arena}", arenaName));
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Message.START_TEXT_TIP.getMessageWithPrefix().replace("{arena}", arenaName)).create()));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gd join " + arenaName));
                lobby.getPlayers().forEach(player -> player.spigot().sendMessage(textComponent));
                lobby.getPlayers().forEach(player -> player.sendMessage(""));
            }
        }
    }
}
