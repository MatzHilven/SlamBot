package me.matzhilven.slambot.ticket;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Optional;

public class TicketManager {

    private final JDA api;
    private final Category ticketCategory;
    private final HashMap<Integer, Ticket> tickets;

    public TicketManager(JDA api, String ticketCategory) {
        this.api = api;
        this.ticketCategory = api.getCategoryById(ticketCategory);
        this.tickets = new HashMap<>();
    }

    public Optional<Ticket> getMemberTicket(String memberId) {
        return tickets.values().stream().filter(ticket -> ticket.member().equals(memberId)).findFirst();
    }

    public void addTicket(Ticket ticket, Guild guild) {
        tickets.put(ticket.id(), ticket);

        ticketCategory.createTextChannel("ticket-" + ticket.id())
                .addPermissionOverride(guild.getMemberById(ticket.member()), EnumSet.of(Permission.VIEW_CHANNEL), null)
                .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                .setParent(ticketCategory)
                .queue();

    }

    public void removeTicket(int ticketID, Guild guild) {
        tickets.remove(ticketID);

        guild.getGuildChannelById("ticket-" + ticketID).delete().queue();
    }

}
