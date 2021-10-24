package main;

import java.awt.Color;
import static main.Bot.PREFIX;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceiver extends ListenerAdapter{
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String command = e.getMessage().getContentRaw().split(" ")[0];
        String[] message = e.getMessage().getContentRaw().split(" ");
        
        if(command.startsWith(PREFIX)) command = command.substring(PREFIX.length()); else return;
        
        switch(command.toLowerCase()){
            case "ping":
                System.out.println("pong");
                break;
            case "zalando":
                e.getChannel()
                        .sendMessage(createEmbed(ZalandoScrapper.find(message[1]),e))
                        .queue();
                break;
            default:
        }
    }

    private MessageEmbed createEmbed(Shoe shoe, GuildMessageReceivedEvent e) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.ORANGE);
        builder.setTitle(shoe.name, shoe.shopLink);
        builder.setThumbnail(shoe.imageLink);
        
        builder.addField("GENERAL PID", shoe.name.split(" ")[0],false);
        
        StringBuilder sb = new StringBuilder();
        //int totalStock = 0;
        
        for(ShoeSize shoeSize:shoe.shoeSizes){
            /*
            builder.addField(((shoeSize.stock==0)? "❌ ":"✅ ") +"Size: " + shoeSize.size + " Price: (" +shoeSize.price+")" , 
                    "**Stock: ["+shoeSize.stock + "]** Id: "+shoeSize.id, 
                    false);
            */
            
            sb.append("``");
            sb.append(shoeSize.size);
            sb.append(shoeSize.align());
            sb.append("``  ");
            
            sb.append("``");
            sb.append(shoeSize.id);
            sb.append("  `` ");
            
            sb.append("");
            sb.append(shoeSize.stock);
            //sb.append((shoeSize.stock>=100)?"":(shoeSize.stock>=10?" ":"  "));
            sb.append("\n");
            
            //totalStock += shoeSize.stock;
        }
        builder.addField("🟢 In Stock\n" +
                            "🔴 Out of Stock","",false);
        builder.addField("SIZES\t SIZE PIDS\t\t\t\t\t\t\t STOCK ",sb.toString(),false);
        //builder.addField("TOTAL STOCK:",totalStock+"",false);
        String url = shoe.shopLink;
        /*
        builder.addField("",
                    toBoldWithUri("UK",url)+ //not working
                    toBoldWithUri("NO",url)+//not working
                    toBoldWithUri("AT",url)+
                    toBoldWithUri("BE",url)+
                    toBoldWithUri("CH",url)+
                    toBoldWithUri("ES",url)+
                    toBoldWithUri("IT",url)+
                    toBoldWithUri("NL",url)
                ,false);
         builder.addField("",
                    toBoldWithUri("DE",url)+
                    toBoldWithUri("SE",url)+
                    toBoldWithUri("FR",url)+
                    toBoldWithUri("DK",url)+
                    toBoldWithUri("PL",url)+
                    toBoldWithUri("CZ",url)
                ,false);
        */
        
        builder.setFooter("CookCandy4s Scraper","https://i.ibb.co/0KfhXP0/xd.jpg");
        
        return builder.build();
    }
    
    public static String toBoldWithUri(String title, String uri) {
        uri = uri.replaceAll("zalando.?.?.?", "zalando."+title);
        return " **[" + title + "]" + "(" + uri + ")** ";
    }
}