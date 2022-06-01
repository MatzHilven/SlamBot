package me.matzhilven.slambot.listeners;

import me.matzhilven.slambot.SlamBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class TicketListener extends ListenerAdapter {

    private final SlamBot main;
    private final JDA api;
    private final MessageChannel ticketChannel;

    public TicketListener(SlamBot main) {
        this.main = main;
        this.api = main.getApi();
        this.ticketChannel = (MessageChannel) api.getGuildChannelById(main.getConfig().getString("ticket.channel"));
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getChannel().equals(ticketChannel)) {
            System.out.println("not in correct channel");
            return;
        }
    }
}
