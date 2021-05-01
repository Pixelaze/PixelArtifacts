package Pixelaze.PixelArtifacts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PixelArtifactsMain extends JavaPlugin implements Listener {

    public static Random random;
    public static double chance = 10; //10% of all block breacks
    public static double badItemsBonus = 20; //20% bonus to stone pickaxe and shovels, to 0.5% in total
    public static double silkTouchModifier = 2; //x2 on Silk Touch usage

    public static boolean DEBUG_MODE = false;

    public static List<Material> ToolsList, BadToolsList, MaterialsList;
    public static List<ArchaeologicalArtifact> ArtifactsList;

    public static String artifactFoundedMessage, pixArtifactsGetUsageWrong, pixArtifactsGetTooBig;
    public static List<String> pixelArtifactsInfoMessageList, pixelArtifactsHelpMessageList;

    @Override
    public void onEnable() {
        getLogger().info("PixelArtifacts has been started");
        random = new Random();

        readConfig();

        getServer().getPluginManager().registerEvents(new PixelListener(), this);

        getLogger().info("s1mple information:");
        getLogger().info("Chance: " + chance);
        getLogger().info("Bad Items Bonus: " + badItemsBonus);
        getLogger().info("Silk Touch Modifier: " + silkTouchModifier);
        getLogger().info("DEBUG MODE: " + DEBUG_MODE);
        getLogger().info("~ yeah, im just need to paste s1mple here. ~");
    }

    public void readConfig() {
        getLogger().info("Reading config...");
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        chance = getConfig().getInt("DropChance");
        badItemsBonus = getConfig().getInt("BadItemsBonus");
        silkTouchModifier = getConfig().getDouble("SilkTouchModifier");
        DEBUG_MODE = getConfig().getBoolean("DebugMode");
        if(DEBUG_MODE) getLogger().info("DEBUG MODE: CHANCES LOADED.");

        List<String> tempToolsList = getConfig().getStringList("ToolsList");
        ToolsList = stringListToMaterial(tempToolsList);
        List<String> tempBadToolsList = getConfig().getStringList("BadToolsList");
        BadToolsList = stringListToMaterial(tempBadToolsList);
        List<String> tempMaterialList = getConfig().getStringList("MaterialsList");
        //if(DEBUG_MODE) getLogger().info(tempMaterialList.get(0)); //Нахуй я оставлю ненужный код, сделанный лишь из-за того, что я еблан? Хватит задавать лишних вопросов. Бля хочу второй монитор, чтобы полностью читать такие комментарии и не переносить их на следующую строку...
        MaterialsList = stringListToMaterial(tempMaterialList);
        if(DEBUG_MODE) getLogger().info("DEBUG MODE: TOOLSLIST, BADTOOLSLIST AND MATERIALSLIST LOADED.");

        getLogger().info("Reading artifacts list...");
        List<String> tempArtifactsList = getConfig().getStringList("ArtifactsList");

        ArtifactsList = artifactsListRead(tempArtifactsList);
        getLogger().info("Artifacts list loaded!");
        pixArtifactsGetTooBig = ChatColor.translateAlternateColorCodes('&', getConfig().getString("PixelArtifactsGetTooBig"));
        pixArtifactsGetUsageWrong = ChatColor.translateAlternateColorCodes('&', getConfig().getString("PixelArtifactsGetUsage"));
        artifactFoundedMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("FoundedArtifactMessage"));
        if(DEBUG_MODE) getLogger().info("DEBUG MODE: ONE LINE MESSAGES LOADED.");

        pixelArtifactsHelpMessageList = translatingCodesOnMessagesList(getConfig().getStringList("PixelArtifactsHelpMessageList"));
        pixelArtifactsInfoMessageList = translatingCodesOnMessagesList(getConfig().getStringList("PixelArtifactsInfoMessageList"));
        getLogger().info("Config loaded!");
    }

    private List<Material> stringListToMaterial(List<String> list) {
        //try {
        List<Material> materials = new ArrayList<Material>(list.size());
        for (int i = 0; i < list.size(); i++) {
            materials.add(Material.valueOf(list.get(i)));
        }
        return materials;
        /*} catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Material>(1);
        }*/
    }

    private List<ArchaeologicalArtifact> artifactsListRead(List<String> list) {
        List<ArchaeologicalArtifact> returnList = new ArrayList<ArchaeologicalArtifact>(list.size());
        for (int i = 0; i < list.size(); i++) {
            String str[] = list.get(i).split(" ");

            //Getting values
            ItemStack itemStack = new ItemStack(Material.valueOf(str[0]));
            int min = Integer.parseInt(str[1]);
            int max = Integer.parseInt(str[2]);
            String name = str[3].replace('_', ' ');
            int rarity = Integer.parseInt(str[4]);

            //Load meta data
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            itemStack.setItemMeta(meta);

            ArchaeologicalArtifact artifact = new ArchaeologicalArtifact(itemStack, min, max, rarity);
            returnList.add(artifact);

            if(DEBUG_MODE) getLogger().info("DEBUG MODE: LOADED NEW ARTIFACT. \n" + artifact.toString());
        }
        return returnList;
    }

    private List<String> translatingCodesOnMessagesList(List<String> messageList) {
        for (int i = 0; i < messageList.size(); i++) {
            messageList.set(i, ChatColor.translateAlternateColorCodes('&', messageList.get(i)));
        }
        return messageList;
    }

    @Override
    public void onDisable() {
        getLogger().info("PixelArtifacts has been disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("pixelartifacts")) {
            if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
                if(DEBUG_MODE) getLogger().info("DEBUG MODE: SOMEONE IS REQUESTED A HELP");
                String message = ChatColor.YELLOW + "Pixel Artifacts Plugin";
                for (int i = 0; i < pixelArtifactsHelpMessageList.size(); i++) {
                    message += "\n" + pixelArtifactsHelpMessageList.get(i);
                }
                sender.sendMessage(message);
                return true;
            } else {
                //switch() case? Не, не слышали
                if(args[0].equalsIgnoreCase("reload")) {
                    if(sender.isOp() || sender.hasPermission("pixelartifacts.command.reload")) {
                        sender.sendMessage(ChatColor.RED + "Reloading config...");
                        getLogger().info("Reloading config by command...");
                        reloadConfig();
                        readConfig();
                        getLogger().info("Config reloaded!");
                        sender.sendMessage(ChatColor.YELLOW + "Config was been reloaded.");
                        return true;
                    }
                    return false;
                } else if(args[0].equalsIgnoreCase("credits")) {
                    String message = ChatColor.GOLD + "" + ChatColor.BOLD + "PixelArtifacts Plugin";
                    message += "\n" + ChatColor.GOLD + "Made by " + ChatColor.BOLD + "Pixelaze";
                    message += "\n" + ChatColor.GOLD + "GitHub: github.com/Pixelaze";
                    message += "\n" + ChatColor.GOLD + "Thanks for using it!";
                    message += "\n" + ChatColor.WHITE + "Hello " + ChatColor.BLUE + "from " + ChatColor.RED + "Russia!";
                    sender.sendMessage(message);

                    return true;
                } else if(args[0].equalsIgnoreCase("list")) {
                    if(sender.isOp() || sender.hasPermission("pixelartifacts.command.list")) {
                        String message = ChatColor.GOLD + "Artifacts list";
                        for (int i = 0; i < ArtifactsList.size(); i++) {
                            message += "\n" + ChatColor.WHITE + i + ". " + ArtifactsList.get(i).getItem().getItemMeta().getDisplayName();
                        }
                        sender.sendMessage(message);
                        return true;
                    }
                    return false;
                } else if(args[0].equalsIgnoreCase("get")) {
                    if(sender.isOp() || sender.hasPermission("pixelartifacts.command.get")) {
                        if(!(sender instanceof Player)) {
                            sender.sendMessage("Console has been dropped an artifact to trash bin. Are you happy?!");
                            return true; //блять пидоры охуевшие в консоли предметы просят
                        }
                        if(args.length != 2) {
                            sender.sendMessage(pixArtifactsGetUsageWrong);
                            return false;
                        }
                        try {
                            int index = Integer.parseInt(args[1]);
                            ItemStack itemStack = ArtifactsList.get(index).getItem();
                            Player p = (Player) sender;
                            p.getInventory().addItem(itemStack);
                        } catch (Exception e) {
                            sender.sendMessage(pixArtifactsGetTooBig);
                            return false;
                        }
                        return true;
                    }
                    return false;
                } else if(args[0].equalsIgnoreCase("info")) {
                    if(sender.isOp() || sender.hasPermission("pixelartifacts.command.info")) {
                        //Поздравляю! Вы нашли пасхалку в коде!
                        //sender.sendMessage("нет блять мне лень это делать"); //TODO: убрать это TODO
                        String message = "";
                        for (int i = 0; i < pixelArtifactsInfoMessageList.size(); i++) {
                            //Это можно было бы сделать гораздо эффектинее, но и так сойдет
                            message += pixelArtifactsInfoMessageList.get(i)
                                    .replaceAll("%CHANCE%", chance + "%")
                                    .replaceAll("%BADTOOLSBONUS%", badItemsBonus + "%")
                                    .replaceAll("%SILKTOUCHMODIFIER%", silkTouchModifier + "")
                                    .replaceAll("%MATERIALSCOUNT%", String.valueOf(MaterialsList.size()))
                                    .replaceAll("%ARTIFACTSCOUNT%", String.valueOf(ArtifactsList.size()))
                                    .replaceAll("%BADTOOLSCOUNT%", String.valueOf(BadToolsList.size()))
                                    .replaceAll("%TOOLSCOUNT%", String.valueOf(ToolsList.size()))
                                    .replaceAll("%DEBUGMODE%", DEBUG_MODE ? ChatColor.GREEN + "TRUE" : ChatColor.RED + "FALSE");
                        }
                        sender.sendMessage(message);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public static <T> boolean valueInList(T value, List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(value)) {
                return true;
            }
        }
        return false;
    }
}

