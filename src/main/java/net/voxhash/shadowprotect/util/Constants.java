package net.voxhash.shadowprotect.util;

import java.util.List;

import net.voxhash.shadowprotect.ShadowProtect;

public interface Constants {
    static ShadowProtect plugin = ShadowProtect.getPlugin(ShadowProtect.class);
    public static final String PLUGIN_NAME = "ShadowProtect";

    public static interface Config {
        public static interface General {
            public static final String PREFIX = plugin.getConfig().getString("general.prefix", "&8[&c&lShadowProtect&8]&r");
            public static final String MESSAGE = plugin.getConfig().getString("general.message", "&7");
            public static final String PRIMARY_COLOR = plugin.getConfig().getString("general.primary_color", "&c&l");
            public static final String SECONDARY_COLOR = plugin.getConfig().getString("general.secondary_color", "&8");
            public static final String EXTRA_COLOR = plugin.getConfig().getString("general.extra_color", "&e&l");
            public static final String ERROR_COLOR = plugin.getConfig().getString("general.error_color", "&4");
            public static final String WARNING_COLOR = plugin.getConfig().getString("general.warning_color", "&e");
            public static final String SUCCESS_COLOR = plugin.getConfig().getString("general.success_color", "&a");
            public static final String INFO_COLOR = plugin.getConfig().getString("general.info_color", "&b");
            public static final Boolean CHECK_SIGN = plugin.getConfig().getBoolean("general.check_sign", true);
            public static final Boolean CHECK_DM = plugin.getConfig().getBoolean("general.check_dm", true);
            public static final List<String> COMMANDS_TO_DISABLE = plugin.getConfig().getStringList("general.commands_to_disable");
        }

        public static interface Anti_Capitals {
            public static final Boolean ENABLED = plugin.getConfig().getBoolean("anti_capitals.enabled", true);
            public static final Boolean NOTIFY_STAFF = plugin.getConfig().getBoolean("anti_capitals.notify_staff", true);
            public static final Double THRESHOLD = plugin.getConfig().getDouble("anti_capitals.threshold", 0.5);
            public static final Boolean IGNORE_USERNAMES = plugin.getConfig().getBoolean("anti_capitals.ignore_usernames", true);
            public static final Integer MIN_MESSAGE_LENGTH = plugin.getConfig().getInt("anti_capitals.min_message_length", 4);
            public static final List<String> IGNORED_WORDS = plugin.getConfig().getStringList("anti_capitals.ignored_words");
        }
        
        public static interface Anti_Spam {
            public static final Boolean ENABLED = plugin.getConfig().getBoolean("anti_spam.enabled", true);
            public static final Boolean NOTIFY_STAFF = plugin.getConfig().getBoolean("anti_spam.notify_staff", true);

            public static interface Smart_Filter {
                public static final Boolean ENABLED = plugin.getConfig().getBoolean("anti_spam.smart_filter.enabled", true);
                public static final Boolean NOTIFY_STAFF = plugin.getConfig().getBoolean("anti_spam.smart_filter.notify_staff", true);
                public static final String API_TOKEN = plugin.getConfig().getString("anti_spam.smart_filter.api_token");
                public static final Double THRESHOLD = plugin.getConfig().getDouble("anti_spam.smart_filter.threshold", 0.85);
                public static final List<String> IGNORED_PHRASES = plugin.getConfig().getStringList("anti_spam.smart_filter.ignored_phrases");
            }
            
            public static interface Chat {
                public static final Integer DELAY = plugin.getConfig().getInt("anti_spam.chat.delay", 1);
                public static final List<String> IGNORED_WORDS = plugin.getConfig().getStringList("anti_spam.chat.ignored_words");
            }
            
            public static interface Similarity_Check {
                public static final Boolean ENABLED = plugin.getConfig().getBoolean("anti_spam.similarity_check.enabled", true);
                public static final Boolean NOTIFY_STAFF = plugin.getConfig().getBoolean("anti_spam.similatiry_check.notify_staff", true);
                public static final Double SIMILARITY_THRESHOLD = plugin.getConfig().getDouble("anti_spam.similarity_check.similarity_threshold", 0.85);
                public static final Boolean IGNORE_SPECIAL = plugin.getConfig().getBoolean("anti_spam.similarity_check.ignore_special", true);
                public static final Boolean IGNORE_FIRST_ARG_COMMAND = plugin.getConfig().getBoolean("anti_spam.similarity_check.ignore_first_arg_command", true);
                public static final List<String> IGNORED_COMMANDS = plugin.getConfig().getStringList("anti_spam.similarity_check.ignored_commands");
            }
            
            public static interface Entropy_Check {
                public static final Boolean ENABLED = plugin.getConfig().getBoolean("anti_spam.entropy_check.enabled", false);
                public static final Boolean NOTIFY_STAFF = plugin.getConfig().getBoolean("anti_spam.entropy_check.notify_staff", true);
                public static final Double ENTROPY_THRESHOLD = plugin.getConfig().getDouble("anti_spam.entropy_check.entropy_threshold", 0.85);
                public static final Boolean IGNORE_USERNAMES = plugin.getConfig().getBoolean("anti_spam.entropy_check.ignore_usernames", true);
                public static final Boolean IGNORE_SPECIAL = plugin.getConfig().getBoolean("anti_spam.entropy_check.ignore_special", true);
            }
        }

        public static interface Anti_Swear {
            public static final Boolean ENABLED = plugin.getConfig().getBoolean("anti_swear.enabled", true);
            public static final Boolean NOTIFY_STAFF = plugin.getConfig().getBoolean("anti_swear.notify_staff", false);
            public static final List<String> SWEAR_WORDS = plugin.getConfig().getStringList("anti_swear.swear_words");
            public static final Integer MAX_LEVENSHTEIN_DISTANCE = plugin.getConfig().getInt("anti_swear.max_levenshtein_distance", 1);
        }

        public static interface Social_Spy {
                public static final Boolean IGNORE_BLANK = plugin.getConfig().getBoolean("social_spy.sign.ignore_blank", true);
                public static final List<String> ENABLED_COMMANDS = plugin.getConfig().getStringList("social_spy.enabled_commands");
        }
    }
    
    public static interface Messages {
        public static interface Join_Leave {
            public static final List<String> JOIN_MESSAGES = plugin.getConfig().getStringList("language.join_leave.join_messages");
            public static final List<String> LEAVE_MESSAGES = plugin.getConfig().getStringList("language.join_leave.leave_messages");
            public static final List<String> KICK_MESSAGES = plugin.getConfig().getStringList("language.join_leave.kick_messages");
        }

        public static interface Chat {
            public static final String FORMAT = plugin.getConfig().getString("language.chat.format", "%message_color%%player_name% %secondary_color%» %message_color%%message%");
            public static final String MUTED = plugin.getConfig().getString("language.chat.muted", "%error_color%You are muted and may not speak!");
            public static final String DELAY_SET = plugin.getConfig().getString("language.chat.delay_set", "%prefix% %error_color%A chat delay of %extra_color%&l%delay% %error_color%seconds has been set by %extra_color%&l%player_name%%error_color%!");
            public static final String CLEARED = plugin.getConfig().getString("language.chat.cleared", "%prefix% %error_color%Chat has been cleared by %extra_color%&l%player_name%%error_color%!");
            public static final String LEAVE_MESSAGE = plugin.getConfig().getString("language.chat.leave_message", "%secondary_color%[%error_color%-%secondary_color%] %message_color%%player_name%");
            public static final String JOIN_MESSAGE = plugin.getConfig().getString("language.chat.join_message", "%secondary_color%[%success_color%+%secondary_color%] %message_color%%player_name%");
        }
            
        public static interface Social_Spy {
            public static final String SIGN_SPY_TOGGLE = plugin.getConfig().getString("language.sign_spy_toggle", "%prefix% %message_color%Sign Spy » %status%");
            public static final String SOCIAL_SPY_TOGGLE = plugin.getConfig().getString("language.social_spy_toggle", "%prefix% %message_color%Social Spy » %status%");
            public static final String SOCIAL_SPY_MESSAGE = plugin.getConfig().getString("language.social_spy_message", "%prefix% %message_color%Social Spy » %player_name% » %message%");
            public static final String SIGN_SPY_MESSAGE = plugin.getConfig().getString("language.social_spy.sign_spy_message", "%prefix% %message_color%%player_name% %secondary_color%» \n %secondary_color%» %sign_line_1% \n %secondary_color%» %sign_line_2% \n %secondary_color%» %sign_line_3% \n %secondary_color%» %sign_line_4% \n%secondary_color%(%message_color%%sign_location%%secondary_color%)");
        }

        public static interface Staff {
            public static final String STAFF_CHAT_FORMAT = plugin.getConfig().getString("language.staff.staff_chat_format", "%prefix% %message_color%%player_name% %secondary_color%» %message_color%%message%");
            public static final String STAFF_CHAT_PREFIX = plugin.getConfig().getString("language.staff.staff_chat_prefix", "+");
            public static final String STAFF_CHAT_TOGGLE = plugin.getConfig().getString("language.staff.staff_chat_toggle", "%prefix% %message_color%Staff Chat » %status%");
            public static final String DELAY_SET = plugin.getConfig().getString("language.staff.delay_set", "%prefix% %message_color%Delay set to %extra_color%%delay%%message_color% seconds");
            public static final String DELAY_NEGATIVE = plugin.getConfig().getString("language.staff.delay_negative", "%prefix% %error_color%Delay cannot be negative!");
            public static final String DELAY = plugin.getConfig().getString("language.staff.delay", "%prefix% %message_color%Delay is currently set to %extra_color%%delay%%message_color% seconds");
        }
        public static interface DM {
            public static final String OUTGOING = plugin.getConfig().getString("language.dm.outgoing", "%extra_color%✉ %message_color%to: &a%to_name% %secondary_color%» %message_color%&o%message%");
            public static final String INCOMING = plugin.getConfig().getString("language.dm.incoming", "%extra_color%✉ %message_color%from: &a%from_name% %secondary_color%» %message_color%&o%message%");
            public static final String CANNOT_DM_SELF = plugin.getConfig().getString("language.dm.cannot_dm_self", "%error_color%You cannot send a DM to yourself.");
            public static final String BLOCKED = plugin.getConfig().getString("language.dm.blocked", "%error_color%You cannot send a DM to %player_name%.");
            public static final String CANNOT_BLOCK_SELF = plugin.getConfig().getString("language.dm.cannot_block_self", "%error_color%You cannot block yourself.");
            public static final String NO_LAST_DM = plugin.getConfig().getString("language.dm.no_last_dm", "%error_color%There is no message to reply to.");
        }

        public static interface Notify {
            public static final String CAPITALS = plugin.getConfig().getString("language.notify.capitals", "%prefix% %message_color%%player_name% %error_color%used capitals in their message!");
            public static final String NORMAL = plugin.getConfig().getString("language.notify.message", "%prefix% %message_color%%player_name% %error_color%spamming!");
            public static final String AI = plugin.getConfig().getString("langage.notify.message_ai", "%prefix% %message_color%%player_name% %error_color%spamming with &e(Confidence: %confidence%%) %message_color%(Message: %message%)!");
            public static final String SIMILAR = plugin.getConfig().getString("language.notify.message_similar", "%prefix% %message_color%%player_name% %error_color%spamming with &e(Similarity: %similarity%%)!");
            public static final String ENTROPY = plugin.getConfig().getString("language.notify.message_entropy", "%prefix% %message_color%%player_name% %error_color%spamming with &e(Entropy: %entropy%%)!");
            public static final String SWEAR = plugin.getConfig().getString("language.warn.anti_swear.message_swear", "%prefix% %message_color%%player_name%'s %error_color%message contains a swear word!");

        }

        public static interface Warn {
            public static interface Anti_Capitals {
                public static final String TOO_MANY_CAPS = plugin.getConfig().getString("language.warn.anti_capitals.too_many_caps", "%prefix% %error_color%Your message contains too many capital letters!");
            }
            public static interface Anti_Spam {
                public static final String TOO_MANY_REPEATS = plugin.getConfig().getString("language.warn.anti_spam.too_many_repeats", "%prefix% %error_color%Your message contains too many repeated characters!");
                public static final String SPAM_PROBABILITY = plugin.getConfig().getString("language.warn.anti_spam.spam_probability", "%prefix% %error_color%Your message may contain spam!");
                public static final String ENTROPY = plugin.getConfig().getString("language.warn.anti_spam.entropy", "%prefix% %error_color%Your message contains too much entropy!");
                public static final String CHAT_DELAY = plugin.getConfig().getString("language.warn.anti_spam.chat_delay", "%prefix% %error_color%You must wait %delay% seconds before sending another message!");
                
            }
        }
        
    }

    public static interface Permissions {
        public static final String ADMIN = "shadowprotect.admin";
        public static final String COMMAND_BYPASS = "shadowprotect.command.bypass";
        public static final String CHAT_BYPASS = "shadowprotect.chat.bypass";
        public static final String NOTIFY_STAFF = "shadowprotect.notify";
        public static final String MUTE_CHAT = "shadowprotect.chat.mute";
    }
}
