//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.refinedstorage.mixin;


import com.refinedmods.refinedstorage.screen.grid.sorting.QuantityGridSorter;
import com.refinedmods.refinedstorage.screen.grid.sorting.SortingDirection;
import com.refinedmods.refinedstorage.screen.grid.stack.IGridStack;
import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
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
 * This mixin handles custom jewel sorting order for rs.
 */
@Mixin(QuantityGridSorter.class)
public class MixinQuantityGridSorter
{
    /**
     * This method injects code at the start of compare to sort jewel items.
     * @param left The first stack item.
     * @param right The second stack item.
     * @param sortingDirection The sorting direction.
     * @param cir The callback info returnable.
     */
    @Inject(method = "compare(Lcom/refinedmods/refinedstorage/screen/grid/stack/IGridStack;Lcom/refinedmods/refinedstorage/screen/grid/stack/IGridStack;Lcom/refinedmods/refinedstorage/screen/grid/sorting/SortingDirection;)I",
        at = @At("RETURN"),
        cancellable = true,
        remap = false)
    public void compare(IGridStack left,
        IGridStack right,
        SortingDirection sortingDirection,
        CallbackInfoReturnable<Integer> cir)
    {
        if (Screen.hasShiftDown() || cir.getReturnValue() != 0)
        {
            // If shift is pressed or both names are not equal, then ignore.
            return;
        }

        if (!left.getModId().equals(right.getModId()))
        {
            // some small cleanup. We want to sort only vault items.
            cir.setReturnValue(
                String.CASE_INSENSITIVE_ORDER.compare(left.getModId(), right.getModId()));
            return;
        }

        if (left.getIngredient() instanceof ItemStack leftStack &&
            right.getIngredient() instanceof ItemStack rightStack) {
            // Get item registry names.

            int registryOrder = SortingHelper.compareRegistryNames(
                leftStack.getItem().getRegistryName(),
                rightStack.getItem().getRegistryName(),
                sortingDirection == SortingDirection.ASCENDING);

            if (registryOrder != 0 || !SortingHelper.isSortable(leftStack.getItem().getRegistryName())) {
                // If registry order is not 0, then return it.
                cir.setReturnValue(registryOrder);
                return;
            }
            Integer cmpRv = SortingHelper.compareItems(
                leftStack,
                rightStack,
                Configuration.SortBy.AMOUNT,
                sortingDirection == SortingDirection.ASCENDING);
            if (cmpRv != null) {
                cir.setReturnValue(cmpRv);
            }
        }
    }
}
