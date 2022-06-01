package me.matzhilven.slambot.commands;

import me.matzhilven.slambot.SlamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class StatsCommand extends ListenerAdapter {

    private final SlamBot main;

    public StatsCommand(SlamBot main) {
        this.main = main;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if ("stats".equals(event.getName())) {
            Member member = event.getMember();
            if (member == null) return;

            event.deferReply().queue();

            String userName = event.getOption("username").getAsString();

            event.getHook().sendMessageEmbeds(new EmbedBuilder()
                    .setTitle("Welcome to SlamPvP")
                    .setThumbnail(member.getUser().getAvatarUrl())
                    .setColor(Color.CYAN)
                    .setDescription("")
                    .setFooter("Requested by " + member.getEffectiveName() + " | Command /stats <player>")
                    .build()
            ).queue();
        }
    }
}
