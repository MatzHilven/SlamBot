package me.matzhilven.slambot.listeners;

import me.matzhilven.slambot.SlamBot;
import me.matzhilven.slambot.suggestion.Suggestion;
import me.matzhilven.slambot.suggestion.SuggestionManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class SuggestionListener extends ListenerAdapter {

    private final SlamBot main;
    private final String suggestionsChannel;
    private final SuggestionManager suggestionManager;

    public SuggestionListener(SlamBot main) {
        this.main = main;
        this.suggestionManager = main.getSuggestionManager();
        this.suggestionsChannel = main.getConfig().getString("suggestions-channel");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getChannel().getId().equals(suggestionsChannel)) return;
        if (event.getMember() == null) return;
        if (event.getMember().getUser().isBot()) return;

        Suggestion suggestion = new Suggestion(
                suggestionManager.getID(),
                event.getMember().getId(),
                LocalDateTime.now(),
                event.getMessage().getContentRaw()
        );

        suggestionManager.addSuggestion(suggestion, event.getMessage());
    }
}
