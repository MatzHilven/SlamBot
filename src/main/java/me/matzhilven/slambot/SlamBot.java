package me.matzhilven.slambot;

import me.matzhilven.slambot.commands.StatsCommand;
import me.matzhilven.slambot.listeners.PlayerListeners;
import me.matzhilven.slambot.listeners.ServerListeners;
import me.matzhilven.slambot.listeners.SuggestionListener;
import me.matzhilven.slambot.listeners.TicketListener;
import me.matzhilven.slambot.suggestion.SuggestionManager;
import me.matzhilven.slambot.ticket.TicketManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.awt.*;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public final class SlamBot extends JavaPlugin implements EventListener {


    private JDA api;

    private String server;
    private Guild guild;
    private TextChannel textChannel;

    private SuggestionManager suggestionManager;
    private TicketManager ticketManager;


    @Override
    public void onEnable() {
        saveResource("config.yml", true);

        server = getConfig().getString("server");

        try {
            api = JDABuilder.createDefault(getConfig().getString("bot.token"))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
            api.addEventListener(this);
            api.getPresence().setPresence(Activity.playing(getConfig().getString("bot.status")), false);

            CommandListUpdateAction commands = api.updateCommands();

            commands.addCommands(
                    Commands.slash("stats", "See the in-game stats of a player.")
                            .addOptions(new OptionData(STRING, "username", "The username of the player you want to check.").setRequired(true))
            );

            commands.queue();

        } catch (LoginException e) {
            e.printStackTrace();
        }

        new PlayerListeners(this);


    }

    @Override
    public void onDisable() {
        textChannel.sendMessageEmbeds(new EmbedBuilder()
                .setTitle(getConfig().getString("messages.stop"))
                .setColor(Color.RED)
                .build()
        ).queue();
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            suggestionManager = new SuggestionManager(api);
            ticketManager = new TicketManager(api, getConfig().getString("ticket.category"));

            guild = api.getGuildById(getConfig().getString("guild-id"));

            api.addEventListener(
                    new TicketListener(this),
                    new SuggestionListener(this),
                    new ServerListeners(this),
                    new StatsCommand(this)
            );

            textChannel = guild.getTextChannelById(getConfig().getString("channel." + server));


            textChannel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(getConfig().getString("messages.start"))
                    .setColor(Color.GREEN)
                    .build()
            ).queue();
        }

    }

    public JDA getApi() {
        return api;
    }

    public Guild getGuild() {
        return guild;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public SuggestionManager getSuggestionManager() {
        return suggestionManager;
    }

    public TicketManager getTicketManager() {
        return ticketManager;
    }
}
