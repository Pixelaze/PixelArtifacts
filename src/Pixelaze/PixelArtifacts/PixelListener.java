package Pixelaze.PixelArtifacts;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static Pixelaze.PixelArtifacts.PixelArtifactsMain.*;
import static org.bukkit.Bukkit.getLogger;

public class PixelListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(DEBUG_MODE) getLogger().info("DEBUG MODE: REGISTERED BLOCK BREAK EVENT");
        if(valueInList(event.getBlock().getType(), MaterialsList)) {
            if(DEBUG_MODE) getLogger().info("DEBUG MODE: BLOCK REGISTERED IN MATERIALS LIST");
            Player player = event.getPlayer();
            Material tool = player.getInventory().getItemInMainHand().getType();
            if(valueInList(tool, ToolsList)) {
                if(DEBUG_MODE) getLogger().info("DEBUG MODE: TOOL REGISTEREN IN TOOLS LIST");
                if (player.hasPermission("pixelartifacts.artifacts.mineartifact") || player.isOp()) {
                    //Getting chance
                    if (DEBUG_MODE) getLogger().info("DEBUG MODE: PLAYER " + player.getDisplayName() + " HAS PERMISSION TO MINE ARTIFACT.");
                    double dropChance = chance;
                    if(valueInList(tool, BadToolsList)) {
                        dropChance += badItemsBonus;
                        if(DEBUG_MODE) getLogger().info("DEBUG MODE: BAD TOOL REGISTERED");
                    }
                    if(player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                        dropChance *= silkTouchModifier;
                        if(DEBUG_MODE) getLogger().info("DEBUG MODE: SILK TOUCH DETECTED");
                        //TODO: ДОБАВИТЬ ПОДДЕРЖКУ ФОРТУНЫ
                        //TODO: ИЗМЕНИТЬ if(DEBUG_MODE) getLogger().info() НА НОРМАЛЬНЫЙ МЕТОД
                    }

                    //Calculating randomly
                    int rolled = random.nextInt(100);
                    if(DEBUG_MODE) getLogger().info("DEBUG MODE: ROLLED " + rolled + " VS DROPCHANCE " + dropChance + "%");
                    if(rolled <= dropChance) {
                        player.sendMessage(artifactFoundedMessage);
                        ArchaeologicalArtifact item = ArtifactsList.get(random.nextInt(ArtifactsList.size()));
                        ItemStack itemStack = item.getItem();
                        itemStack.setAmount(item.getMin() + random.nextInt(item.getMax() - item.getMin() + 1)); //ЯШЫЗ
                        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
                    }
                }
            }
        }
    }
}
