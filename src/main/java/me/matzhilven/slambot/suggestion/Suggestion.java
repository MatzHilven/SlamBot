package me.matzhilven.slambot.suggestion;

import java.time.LocalDateTime;

public record Suggestion(int id, String member, LocalDateTime created, String message) { }
