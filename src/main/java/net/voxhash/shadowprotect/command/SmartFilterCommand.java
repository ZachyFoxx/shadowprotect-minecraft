package net.voxhash.shadowprotect.command;

import java.io.UnsupportedEncodingException;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.arguments.TextArgument;
import net.voxhash.shadowprotect.util.Constants;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.web.endpoint.RegistrationEndpoint;
import net.voxhash.shadowprotect.util.web.endpoint.VerifyEndpoint;

public class SmartFilterCommand {

    public static CommandAPICommand command = new CommandAPICommand("smartfilter")
            .withPermission("shadowprotect.admin")
            .withAliases("smf")
            .withSubcommand(
                    new CommandAPICommand("register")
                            .withArguments(new StringArgument("username"))
                            .withArguments(new StringArgument("password"))
                            .withArguments(new TextArgument("email"))
                            .executesPlayer((sender, args) -> {
                                String username = (String) args[0];
                                String password = (String) args[1];
                                String email = (String) args[2];

                                try {
                                    RegistrationEndpoint endpoint = new RegistrationEndpoint(email, username, password);
                                    int status = endpoint.register();
                                    if (status == 200) {
                                        sender.sendMessage(Format.format(Constants.Config.General.PREFIX
                                                + " &aPlease check your email for your verification token!"
                                                + "\n    &7&oMake sure to check your spam folder", sender));
                                    } else {
                                        switch (status) {
                                            case 400:
                                                sender.sendMessage(Format.format(Constants.Config.General.PREFIX
                                                        + " &4Uh oh! Make sure everything is correct and try again",
                                                        sender));
                                                break;
                                            case 500:
                                                sender.sendMessage(Format.format(Constants.Config.General.PREFIX
                                                        + " &4Uh oh! Something went wrong. Contact the plugin authors!",
                                                        sender));
                                                break;
                                        }
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    sender.sendMessage(Format.format(Constants.Config.General.PREFIX
                                            + " &4An error occurred while registering your account. Contact the plugin authors!",
                                            sender));
                                    e.printStackTrace();
                                }
                            }))
            .withSubcommand(
                    new CommandAPICommand("verify")
                            .withArguments(new StringArgument("token"))
                            .executesPlayer((sender, args) -> {
                                try {
                                    VerifyEndpoint endpoint = new VerifyEndpoint((String) args[0]);
                                    int status = endpoint.verify();
                                    switch (status) {
                                        case 400:
                                            sender.sendMessage(Format.format(
                                                    Constants.Config.General.PREFIX
                                                            + " &4Uh oh! Make sure everything is correct and try again",
                                                    sender));
                                            break;
                                        case 500:
                                            sender.sendMessage(Format.format(Constants.Config.General.PREFIX
                                                    + " &4Uh oh! Something went wrong. Contact the plugin authors!",
                                                    sender));
                                            break;
                                        default:
                                            sender.sendMessage(Format.format(Constants.Config.General.PREFIX
                                                    + " &aSuccess! Your account has been verified!", sender));
                                            break;
                                    }

                                } catch (Exception e) {

                                }
                            }));
}
