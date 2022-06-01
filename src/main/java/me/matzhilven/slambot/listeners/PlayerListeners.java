package me.matzhilven.slambot.listeners;

import me.matzhilven.slambot.SlamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerListeners implements Listener {

    private final SlamBot main;

    public PlayerListeners(SlamBot main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (!event.getPlayer().hasPlayedBefore()) {
            main.getTextChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(main.getConfig().getString("messages.first-join").replace("%player%", event.getPlayer().getName()))
                    .setColor(Color.GREEN)
                    .build()
            ).queue();
            return;
        }

        main.getTextChannel().sendMessageEmbeds(new EmbedBuilder()
                .setTitle(main.getConfig().getString("messages.join").replace("%player%", event.getPlayer().getName()))
                .setColor(Color.GREEN)
                .build()
        ).queue();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        main.getTextChannel().sendMessageEmbeds(new EmbedBuilder()
                .setTitle(main.getConfig().getString("messages.left").replace("%player%", event.getPlayer().getName()))
                .setColor(Color.RED)
                .build()
        ).queue();
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        main.getTextChannel().sendMessage(main.getConfig().getString("messages.message-format")
                .replace("%player%", event.getPlayer().getName())
                .replace("%message%", event.getMessage())
        ).queue();
    }
}
