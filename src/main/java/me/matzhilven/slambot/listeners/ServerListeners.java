package me.matzhilven.slambot.listeners;

import me.matzhilven.slambot.SlamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ServerListeners extends ListenerAdapter {

    private final SlamBot main;
    private final TextChannel welcomeChannel;
    private final TextChannel linkChannel;

    public ServerListeners(SlamBot main) {
        this.main = main;
        welcomeChannel = main.getGuild().getTextChannelById(main.getConfig().getString("channel.welcome"));
        linkChannel = main.getTextChannel();
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        welcomeChannel.sendMessageEmbeds(new EmbedBuilder()
                .setTitle("Welcome to SlamPvP")
                .setThumbnail(member.getUser().getAvatarUrl())
                .setColor(Color.YELLOW)
                .setDescription(String.format("""
                                                
                        <@%s>
                        > :paperclip: play.slampvp.com\s
                        > :paperclip: https://slampvp.com/\s
                        > :shopping_cart: https://slampvp.buycraft.net/\s
                                                
                        """, member.getId()))
                .setFooter("Total Members - " + (guild.getMembers().size() + 1))
                .build()
        ).queue();

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMember().getUser().isBot()) return;
        Bukkit.broadcastMessage(main.getConfig().getString("messages.message-game-format")
            .replace("%player%", event.getMember().getEffectiveName())
            .replace("%message%", event.getMessage().getContentRaw())
        );
    }
}
