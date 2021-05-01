package Pixelaze.PixelArtifacts;

import org.bukkit.inventory.ItemStack;

public class ArchaeologicalArtifact {
    private ItemStack item;
    private int min;
    private int max;
    private int rarity;

    public ArchaeologicalArtifact(ItemStack item, int min, int max, int rarity) {
        this.item = item;
        this.min = min;
        this.max = max;
        this.rarity = rarity;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "Artifact {" + item.toString() +
                ", min =" + min +
                ", max =" + max +
                ", rarity =" + rarity +
                '}';
    }
}
