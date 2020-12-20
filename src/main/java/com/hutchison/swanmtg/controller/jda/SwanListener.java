package com.hutchison.swanmtg.controller.jda;

import com.hutchison.swanmtg.controller.route.RouteMappings;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class SwanListener extends ListenerAdapter implements EventListener {

    private static final int eventCount = 0;
    private static final long guildId = 790029256046280754L;
    private static Guild guild;
    private static final RouteMappings routeMappings = new RouteMappings("com.hutchison.swanmtg.controller");

    public void onReady(@Nonnull ReadyEvent event) {
        if (guild == null) {
            guild = event.getJDA().getGuildById(guildId);
            if (guild == null) throw new RuntimeException("Error finding primary guild.");
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();
        System.out.println("MESSAGE RECEIVED: \n" + contentRaw);
        if (event.getAuthor().isBot()) return;
        if (event.isFromType(ChannelType.PRIVATE)) return;
        event.getChannel().sendMessage("Pong!").queue();
        //        log.debug("Received " + contentRaw);
        routeMappings.stream()
                .filter(mapping -> mapping.check(contentRaw))
                .findFirst()
                .ifPresent(value -> send(event, contentRaw, value));
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        String contentRaw = e.getMessage().getContentRaw();
        System.out.println("PRIVATE MESSAGE RECEIVED: \n" + contentRaw);
        e.getAuthor().openPrivateChannel()
                .flatMap(c -> c.sendMessage("(It's free real estate)"))
                .queue();
    }

    @Override
    public void onException(@Nonnull ExceptionEvent e) {
        System.out.println("ExceptionEvent: " + e.getCause().getMessage());
    }

    // TODO: Log this
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        User user = event.getMember().getUser();
        String message = user.getId() + " - " + user.getName() + " joined " + event.getGuild().getName();
        System.out.println(message);
    }

    // TODO: Log this
    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        User user = event.getMember().getUser();
        String message = user.getId() + " - " + user.getName() + " gained role(s): " + event.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
        System.out.println(message);
    }

    // TODO: Log this
    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        User user = event.getMember().getUser();
        String message = user.getId() + " - " + user.getName() + " lost role(s): " + event.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
        System.out.println(message);
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        if (getGuild().getVoiceChannels().contains(event.getChannelJoined()) &&
                getGuild().getVoiceChannels().contains(event.getChannelLeft())) {
            User user = event.getMember().getUser();
            System.out.println(user.getId() + " - " + user.getName() + " joined voice channel: " + event.getChannelJoined().getName());
        }
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        if (getGuild().getVoiceChannels().contains(event.getChannelJoined()) &&
                getGuild().getVoiceChannels().contains(event.getChannelLeft())) {
            User user = event.getMember().getUser();
            System.out.println(user.getId() + " - " + user.getName() + " was moved to voice channel: " + event.getChannelJoined().getName());
        }
    }

    private List<VoiceChannel> getVoiceChannels(Event e) {
        return e.getJDA().getGuildById(guildId).getVoiceChannels();
    }

    public Guild getGuild() {
        if (guild == null) throw new RuntimeException("Guild is not initialized.");
        return guild;
    }

}
