package lv.id.bonne.vaulthunters.jewelsorting.tomsstorage.mixin;


import com.tom.storagemod.StoredItemStack;
import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.IExtraGearDataCache;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;


/**
 * This mixin is used to sort jewels in Simple Storage Network mod.
 */
@Mixin(value = StoredItemStack.ComparatorName.class, remap = false)
public class MixinStoredItemStackComparatorName
{
    @Shadow
    public boolean reversed;

    /**
     * This method modifies the sortStackWrappers method to sort jewels. It is done by modifying second parameter in
     * Collections#sort call inside sortStackWrappers method.
     */
    @Inject(method = "compare(Lcom/tom/storagemod/StoredItemStack;Lcom/tom/storagemod/StoredItemStack;)I",
        at = @At(value = "RETURN"),
        cancellable = true)
    private void sortStackWrappersJewelCompare(StoredItemStack left,
        StoredItemStack right,
        CallbackInfoReturnable<Integer> cir)
    {
        if (Screen.hasShiftDown() || cir.getReturnValue() != 0)
        {
            // If shift is pressed or both names are not equal, then ignore.
            return;
        }

        ItemStack leftStack = left.getStack();
        ItemStack rightStack = right.getStack();

        // Get item registry names.

        int registryOrder = SortingHelper.compareRegistryNames(
            leftStack.getItem().getRegistryName(),
            rightStack.getItem().getRegistryName(),
            !this.reversed);

        if (registryOrder != 0 || !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
        {
            // If registry order is not 0, then return it.
            cir.setReturnValue(registryOrder);
            return;
        }

        Integer cmpRv = SortingHelper.compareItems(
            leftStack,
            rightStack,
            left.getDisplayName(),
            right.getDisplayName(),
            Configuration.SortBy.NAME,
            !this.reversed);
        if (cmpRv != null) {
            cir.setReturnValue(cmpRv);
        }
    }
}

