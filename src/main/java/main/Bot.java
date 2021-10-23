package main;

import javax.security.auth.login.LoginException;
import static main.Settings.readSettings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;


public class Bot {
    public static JDA jda;
    public static String BOT_TOKEN, PREFIX;
    public static int party_count=0;
    public static void main(String[] args) {
        try{
            readSettings();
            System.out.println(BOT_TOKEN);
            jda = JDABuilder.createDefault(BOT_TOKEN)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
            jda.addEventListener(new MessageReceiver());
        } catch(LoginException e) {
            e.printStackTrace();
        }
    }
}