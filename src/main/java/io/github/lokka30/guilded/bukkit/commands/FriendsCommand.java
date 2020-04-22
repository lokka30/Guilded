package io.github.lokka30.guilded.bukkit.commands;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class FriendsCommand implements CommandExecutor {

    /*
    /friends add <player>
        - Send a friend request
    /friends requests
        - Shows all of your friend requests
    /friends cancel <player>
        - Cancel a friend request
    /friends remove <player>
        - Remove a player from your friends list
    /friends accept <player>
        - Accept a friend request
    /friends deny <player>
        - Deny a friend request
    /friends ignore <player>
        - Ignore a player from their friend requests
    /friends ignored
        - Shows you a list of all the players you are ignoring
    /friends list
        - List all of your friends and what server/world they are in
    /friends about <player>
        - Tells you information about the friendship with that player.
        - 'You became friends with Notch on 22 April 2020'
        - 'Notch is on the server Survival'
    /friends options requests <on/off>
    /friends options hidden <on/off>
        - Doesn't tell your friend if you are online or offline
            - If you aren't hidden, such info is shown on join/quit and also /friends about
        - Doesn't show your current server in /friends about
     */
    private GuildedBukkit instance;

    public FriendsCommand(final GuildedBukkit instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (player.hasPermission("guilded.friends")) {
                if (args.length > 0) {
                    switch (args[0].toLowerCase()) {
                        case "add":
                            if (player.hasPermission("guilded.friends.add")) {
                                if (args.length == 2) {
                                    @SuppressWarnings("deprecation") final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                    if (!target.hasPlayedBefore() && !target.isOnline()) {
                                        player.sendMessage(instance.getUtils().prefixFromMessages("target-never-played", "%target% hasn't joined before.")
                                                .replaceAll("%target%", args[1]));
                                    } else {
                                        final String senderUUID = player.getUniqueId().toString();
                                        final String targetUUID = target.getUniqueId().toString();

                                        //are the players already friends?
                                        if (instance.getFriendsManager().getFriendsList(senderUUID).contains(targetUUID)) {
                                            player.sendMessage(instance.getUtils().prefixFromMessages("friends-add-already", "You are already friends with %target%")
                                                    .replaceAll("%target%", Objects.requireNonNull(target.getName())));
                                        } else {
                                            //has the target ignored the sender?
                                            if (instance.getFriendsManager().isIgnoringFriendRequests(targetUUID, senderUUID)) {
                                                player.sendMessage(instance.getUtils().prefixFromMessages("friends-add-ignored", "%target% is ignoring your requests")
                                                        .replaceAll("%target%", Objects.requireNonNull(target.getName())));
                                            } else {
                                                //has the target disabled requests?
                                                if (instance.getFriendsManager().canReceiveRequests(targetUUID)) {
                                                    instance.getFriendsManager().sendFriendRequest(targetUUID, senderUUID);
                                                    player.sendMessage(instance.getUtils().prefixFromMessages("friends-add-success", "Sent a friend request to %target%")
                                                            .replaceAll("%target%", Objects.requireNonNull(target.getName())));
                                                    if (target.isOnline()) {
                                                        Objects.requireNonNull(target.getPlayer()).sendMessage(instance.getUtils().prefixFromMessages("friends-add-requested", "%player% sent you a friend request")
                                                                .replaceAll("%player%", player.getName()));
                                                    }
                                                } else {
                                                    player.sendMessage(instance.getUtils().prefixFromMessages("friends-add-disabled-requests", "%target% does not want to receive friend requests.")
                                                            .replaceAll("%target%", Objects.requireNonNull(target.getName())));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(instance.getUtils().prefixFromMessages("friends-add-usage", "Usage: /friends add <target>"));
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "remove":
                            if (player.hasPermission("guilded.friends.remove")) {
                                if (args.length == 2) {
                                    @SuppressWarnings("deprecation") final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                    final String targetUUID = target.getUniqueId().toString();
                                    final String playerUUID = player.getUniqueId().toString();

                                    if (target.hasPlayedBefore() || target.isOnline()) { //Has target played before?
                                        if (instance.getFriendsManager().getFriendsList(playerUUID).contains(targetUUID)) {
                                            instance.getFriendsManager().removeFriend(playerUUID, targetUUID);
                                            player.sendMessage(instance.getUtils().prefixFromMessages("friends-remove-success", "Removed %target% from your friends list")
                                                    .replaceAll("%target%", Objects.requireNonNull(target.getName())));
                                        } else {
                                            player.sendMessage(instance.getUtils().prefixFromMessages("friends-remove-not-a-friend", "%target% isn't on your friends list")
                                                    .replaceAll("%target%", Objects.requireNonNull(target.getName())));
                                        }
                                    } else {
                                        player.sendMessage(instance.getUtils().prefixFromMessages("target-never-joined", "%target% hasn't joined before")
                                                .replaceAll("%target%", args[1]));
                                    }
                                } else {
                                    player.sendMessage(instance.getUtils().prefixFromMessages("friends-remove-usage", "Usage: /friends remove <target>"));
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "requests":
                            if (player.hasPermission("guilded.friends.requests")) {
                                if (args.length == 1) {
                                    final String playerUUID = player.getUniqueId().toString();
                                    List<String> requesters = instance.getFriendsManager().getRequesters(playerUUID);
                                    if (requesters.size() == 0) {
                                        player.sendMessage(instance.getUtils().prefixFromMessages("friends-requests-none", "No friend requests to display"));
                                    } else {
                                        player.sendMessage(instance.getUtils().prefixFromMessages("friends-requests-header", "You haven't responded to the following friend requests:"));
                                        for (String requester : requesters) {
                                            @SuppressWarnings("deprecation") final OfflinePlayer requesterPlayer = Bukkit.getOfflinePlayer(requester);
                                            if (requesterPlayer.hasPlayedBefore() || requesterPlayer.isOnline()) {
                                                //Make sure the requester is still a valid offline player
                                                player.sendMessage(instance.getUtils().prefixFromMessages("friends-requests-each", " - %requester%")
                                                        .replaceAll("%requester%", Objects.requireNonNull(requesterPlayer.getName())));
                                            } else {
                                                //This request is no longer valid - maybe the server reset but the owner didn't reset the friends data, so the request is no longer valid.
                                                instance.getFriendsManager().cancelFriendRequest(requester, playerUUID);
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(instance.getUtils().prefixFromMessages("friends-requests-usage", "Usage: /friends requests"));
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "cancel":
                            if (player.hasPermission("guilded.friends.cancel")) {

                                sender.sendMessage("cancel subcommand has not been implemented yet.");

                                if (args.length == 2) {
                                    //TODO
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "accept":
                            if (player.hasPermission("guilded.friends.accept")) {

                                sender.sendMessage("accept subcommand has not been implemented yet.");

                                if (args.length == 2) {
                                    //TODO
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "deny":
                            if (player.hasPermission("guilded.friends.deny")) {

                                sender.sendMessage("deny subcommand has not been implemented yet.");

                                if (args.length == 2) {
                                    //TODO
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "list":
                            if (player.hasPermission("guilded.friends.list")) {

                                sender.sendMessage("list subcommand has not been implemented yet.");

                                if (args.length == 1) {
                                    //TODO
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "about":
                            if (player.hasPermission("guilded.friends.about")) {

                                sender.sendMessage("about subcommand has not been implemented yet.");

                                if (args.length == 2) {
                                    //TODO
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "ignore":
                            if (player.hasPermission("guilded.friends.ignore")) {

                                sender.sendMessage("ignore subcommand has not been implemented yet.");

                                if (args.length == 2) {
                                    //TODO /friends ignore Notch
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        case "ignored":
                            if (player.hasPermission("guilded.friends.ignored")) {

                                sender.sendMessage("ignored subcommand has not been implemented yet.");

                                if (args.length == 1) {
                                    //TODO
                                } else {
                                    //Todo usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                        case "options":
                            if (player.hasPermission("guilded.friends.options")) {

                                sender.sendMessage("options subcommand has not been implemented yet.");

                                if (args.length == 3) {
                                    //TODO
                                } else {
                                    //TODO Usage
                                }
                            } else {
                                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
                            }
                            break;
                        default:
                            //TODO usage
                            break;
                    }
                } else {
                    //TODO Usage
                }
            } else {
                player.sendMessage(instance.getUtils().prefixFromMessages("no-permission", "No permission"));
            }
        } else {
            sender.sendMessage(instance.getUtils().prefixFromMessages("players-only", "Only players may use this command."));
        }
        return true;
    }
}
