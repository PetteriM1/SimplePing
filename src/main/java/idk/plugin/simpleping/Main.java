package idk.plugin.simpleping;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class Main extends PluginBase {

    private final Set<String> players = new HashSet<>();

    public void onEnable() {
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, new NukkitRunnable() {
            @Override
            public void run() {
                for (Player p : getServer().getOnlinePlayers().values()) {
                    if (players.contains(p.getName())) {
                        String ping;

                        if (p.getPing() < 100) {
                            ping = "\u00A7a" + p.getPing() + " ms";
                        } else if (p.getPing() < 200) {
                            ping = "\u00A7e" + p.getPing() + " ms";
                        } else if (p.getPing() < 300) {
                            ping = "\u00A76" + p.getPing() + " ms";
                        } else {
                            ping = "\u00A7c" + p.getPing() + " ms";
                        }

                        p.sendPopup("Ping: " + ping);
                    }
                }
            }
        }, 20, 20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ping")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                String ping;

                if (p.getPing() < 100) {
                    ping = "\u00A7a" + p.getPing() + " ms";
                } else if (p.getPing() < 200) {
                    ping = "\u00A7e" + p.getPing() + " ms";
                } else if (p.getPing() < 300) {
                    ping = "\u00A76" + p.getPing() + " ms";
                } else {
                    ping = "\u00A7c" + p.getPing() + " ms";
                }

                p.sendMessage("Ping: " + ping);
            } else {
                sender.sendMessage("\u00A7cThis command only works in game");
            }

            return true;
        }

        if (command.getName().equalsIgnoreCase("toggleping")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (players.contains(p.getName())) {
                    players.remove(p.getName());
                    p.sendMessage("\u00A7aShowing ping in hud disabled");
                } else {
                    players.add(p.getName());
                    p.sendMessage("\u00A7aShowing ping in hud enabled");
                }
            } else {
                sender.sendMessage("\u00A7cThis command only works in game");
            }

            return true;
        }

        if (command.getName().equalsIgnoreCase("getping")) {
            if (args.length != 1 || !sender.hasPermission("ping.see.other")) {
                return false;
            }

            Player p = getServer().getPlayer(args[0]);

            if (p == null) {
                sender.sendMessage("\u00A7cUnknown player");
                return true;
            }

            String ping;

            if (p.getPing() < 100) {
                ping = "\u00A7a" + p.getPing() + " ms";
            } else if (p.getPing() < 200) {
                ping = "\u00A7e" + p.getPing() + " ms";
            } else if (p.getPing() < 300) {
                ping = "\u00A76" + p.getPing() + " ms";
            } else {
                ping = "\u00A7c" + p.getPing() + " ms";
            }

            sender.sendMessage(p.getName() + "'s ping: " + ping);
            return true;
        }

        return true;
    }
}
