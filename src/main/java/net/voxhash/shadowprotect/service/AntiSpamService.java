package net.voxhash.shadowprotect.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.ShadowPlayer;
import net.voxhash.shadowprotect.util.StringUtil;
import net.voxhash.shadowprotect.util.WithPlugin;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Messages;
import net.voxhash.shadowprotect.util.Constants.Permissions;
import net.voxhash.shadowprotect.util.web.Prediction;
import net.voxhash.shadowprotect.util.web.Type;
import net.voxhash.shadowprotect.util.web.endpoint.PredictionEndpoint;

public class AntiSpamService implements Config, Messages, WithPlugin {
    
    private String result;
    private ShadowPlayer player;

    public AntiSpamService(String input, final ShadowPlayer player) {
        this.player = player;
        this.result = input;
    }

    public String getResult() {
        return this.result;
    }

    public Boolean check() {
        if (Config.Anti_Capitals.ENABLED) {
            String msg = this.result;
            if (Config.Anti_Capitals.IGNORED_WORDS.size() > 0) {
                for (String word: Config.Anti_Capitals.IGNORED_WORDS) {
                    msg = msg.replaceAll("(?i)" + word, "");
                }
            }
            if (msg.length() >= Config.Anti_Capitals.MIN_MESSAGE_LENGTH) {
                if (StringUtil.capitalFrequency(msg) >= Config.Anti_Capitals.THRESHOLD) {
                    player.sendMessage(Format.format(Messages.Warn.Anti_Capitals.TOO_MANY_CAPS, new HashMap<String, String>() {{
                        put("message", result);
                        put("player_name", player.getName());
                        put("player_uuid", player.getUniqueId().toString());
                    }}, true));

                    if (Config.Anti_Capitals.NOTIFY_STAFF)
                        notifyStaff(this.player, Messages.Notify.CAPITALS, msg, Permissions.NOTIFY_STAFF);

                    return false;
                }
            }   
        }

        if (Config.Anti_Swear.ENABLED) {
            String msg = this.result;
            boolean contains = false;
            if (Config.Anti_Swear.SWEAR_WORDS.size() > 0) {
                if (Config.Anti_Swear.AGGRESSIVE) {
                    List<Integer> indexes = new ArrayList<Integer>();
                    for (int i = 0; i < msg.length(); i++) {
                        if (msg.charAt(i) == ' ')
                            indexes.add(i);
                    }
                    
                    String noSpace = msg.replaceAll(" ", "");
                    for (String word: Config.Anti_Swear.SWEAR_WORDS) {
                        if (noSpace.contains(word)) {
                            noSpace = noSpace.replaceAll(word, StringUtil.repeat("*", word.length()));
                            contains = true;
                        }
                    }
                    for (int index: indexes) {
                        msg = msg.substring(0, index) + " " + noSpace.substring(0, noSpace.length()) + msg.substring(index, msg.length());
                    }
                    msg = noSpace;
                }
                for (String word: msg.split(" ")) {
                    String noSpecial = word.replaceAll("[^a-zA-Z0-9]", "");
                    for (String swear: Config.Anti_Swear.SWEAR_WORDS) {
                        if (allowedWords.contains(word.toLowerCase())) {
                            continue;
                        }
                        if (StringUtil.levenshteinDistance(noSpecial, swear) <= Config.Anti_Swear.MAX_LEVENSHTEIN_DISTANCE) {
                            contains = true;
                            msg = msg.replace(word, StringUtil.repeat("*", word.length()));
                            this.result = (msg.replace(word, StringUtil.repeat("*", word.length())));
                        }
                        
                    }
                }
            }

            if (Config.Anti_Swear.NOTIFY_STAFF && contains)
                            notifyStaff(this.player, Messages.Notify.SWEAR, msg, Permissions.NOTIFY_STAFF);
        }

        if (Config.Anti_Spam.ENABLED) {
            if (Config.Anti_Spam.Smart_Filter.ENABLED) {
                try {
                    PredictionEndpoint endpoint = new PredictionEndpoint(Config.Anti_Spam.Smart_Filter.API_TOKEN, this.result);
                    Prediction prediction = endpoint.getPrediction();
                    if (prediction.getProbability() >= Config.Anti_Spam.Smart_Filter.THRESHOLD && prediction.getType() != Type.HAM) {
                        player.sendMessage(Format.format(Messages.Warn.Anti_Spam.SPAM_PROBABILITY, new HashMap<String, String>() {{
                            put("message", result);
                            put("player_name", player.getName());
                            put("player_uuid", player.getUniqueId().toString());
                        }}, true));

                        if (Config.Anti_Spam.Smart_Filter.NOTIFY_STAFF)
                            notifyStaff(this.player, Messages.Notify.AI, this.result, Permissions.NOTIFY_STAFF, new String[] {"confidence", String.valueOf(Math.round((prediction.getProbability() * 100) * 100) / 100 )}, new String[] {"type", prediction.getType().toString()});
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    getPlugin().getLogger().severe("Failed to create endpoint for AI prediction. Please contact plugin authors.");
                }
            }

            if (Config.Anti_Spam.Entropy_Check.ENABLED) {
                if (StringUtil.entropy(this.result) / 100 >= Config.Anti_Spam.Entropy_Check.ENTROPY_THRESHOLD) {
                    player.sendMessage(Format.format(Messages.Warn.Anti_Spam.ENTROPY, new HashMap<String, String>() {{
                        put("message", result);
                        put("player_name", player.getName());
                        put("player_uuid", player.getUniqueId().toString());
                    }}, true));
                    if (Config.Anti_Spam.Entropy_Check.NOTIFY_STAFF) {
                        notifyStaff(this.player, Messages.Notify.ENTROPY, this.result, Permissions.NOTIFY_STAFF,  new String[] {"entropy", String.valueOf(Math.round(StringUtil.entropy(this.result) * 100) / 100)});
                    }
                }
            }

            if (Config.Anti_Spam.Similarity_Check.ENABLED) {
                if (player.getLastMessageEvent() != null) {
                    if (StringUtil.similarity(this.result, player.getLastMessageEvent().getMessage()) >= Config.Anti_Spam.Similarity_Check.SIMILARITY_THRESHOLD) {
                        player.sendMessage(Format.format(Messages.Warn.Anti_Spam.TOO_MANY_REPEATS, new HashMap<String, String>() {{
                            put("message", result);
                            put("player_name", player.getName());
                            put("player_uuid", player.getUniqueId().toString());
                        }}, true));

                        if (Config.Anti_Spam.Similarity_Check.NOTIFY_STAFF)
                            notifyStaff(this.player, Messages.Notify.SIMILAR, this.result, Permissions.NOTIFY_STAFF, new String[] {"similarity", String.valueOf(Math.round((StringUtil.similarity(this.result, player.getLastMessageEvent().getMessage()) * 100) * 100.0) / 100.0)});

                        return false;
                    }
                }
            }

            if (player.getLastMessageEvent() != null) {
                int delay = getPlugin().chatDelay;
                if ((System.currentTimeMillis() - player.getLastMessageEvent().getTimestamp())/1000L < delay) {
                    player.sendMessage(Format.format(Messages.Warn.Anti_Spam.CHAT_DELAY, new HashMap<String, String>() {{
                        put("delay", String.valueOf((delay - (System.currentTimeMillis() - player.getLastMessageEvent().getTimestamp())/1000L)));
                    }}, true));
                    return false;
                }
            }
        }
        return true;
    }

    private void notifyStaff(ShadowPlayer player, String message, String input, String permission, String[] ...args) {
        HashMap<String, String> placeholders = new HashMap<String, String>() {{
            put("message", input);
            for (int i = 0; i < args.length; i++) {
                put(args[i][0], args[i][1]);
            }
        }};

        Bukkit.getServer().getOnlinePlayers().forEach(staff -> {
            if (staff.hasPermission(permission)) {
                staff.sendMessage(Format.format(message, player.getPlayer(), placeholders));
            }
        });
    }

    private static ArrayList<String> allowedWords = new ArrayList<String>() {{
        add("are");
        add("im");
        add("you");
        add("hello");
        add("shell");
        add("shells");
        add("shot");
        add("shots");
        add("he'll");
        add("she'll");
        add("analyze");
        add("analyzed");
        add("analyse");
        add("analysed");
        add("suck");
        add("duck");
        add("ducks");
        add("sucks");
        add("yuck");
        add("yucks");
        add("so");
        add("its");
        add("it");
        add("jun");
        add("june");
        add("six");
        add("sixties");
        add("this");
        add("to");
        add("me");
        add("as");
        add("a");
        add("like");
        add("where");
        add("when");
        add("shutting");
        add("shut");
        add("pick");
        add("bob");
        add("pass");
    }};
}
