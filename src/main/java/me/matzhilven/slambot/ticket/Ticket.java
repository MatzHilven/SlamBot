package me.matzhilven.slambot.ticket;

import java.time.LocalDateTime;

public record Ticket(int id, String member, LocalDateTime created, String message) { }