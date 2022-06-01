package me.matzhilven.slambot.suggestion;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;

public class SuggestionManager {

    private final JDA api;
    private final HashMap<Integer, Suggestion> suggestions;

    public SuggestionManager(JDA api) {
        this.api = api;
        this.suggestions = new HashMap<>();
    }

    public void addSuggestion(Suggestion suggestion, Message message) {
        suggestions.put(suggestion.id(), suggestion);

        message.getChannel().sendMessageEmbeds(new EmbedBuilder()
                .setTitle("Suggestion #" + suggestion.id() + " | By " + message.getGuild().getMemberById(suggestion.member()).getEffectiveName())
                .setDescription(suggestion.message())
                .build()
        ).queue(message1 -> {
            message1.addReaction("minn:973663135573618689").queue();
            message1.addReaction("minn:973663135959482448").queue();
        });

        message.delete().queue();
    }

    public int getID() {
        return suggestions.size() + 1;
    }
}
