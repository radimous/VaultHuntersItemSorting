//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.qio.mixin;


import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Comparator;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.IExtraGearDataCache;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import mekanism.common.inventory.ISlotClickHandler;
import mekanism.common.inventory.container.QIOItemViewerContainer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;


/**
 * This mixin handles custom jewel sorting order for Mekanism QIO.
 */
@Mixin(value = QIOItemViewerContainer.ListSortType.class, remap = false)
public class MixinQIOItemViewerContainerListSortType
{
    /**
     * This shadow field stores ascendingComparator.
     */
    @Shadow
    @Final
    private Comparator<ISlotClickHandler.IScrollableSlot> ascendingComparator;

    /**
     * This shadow field stores descendingComparator.
     */
    @Shadow
    @Final
    private Comparator<ISlotClickHandler.IScrollableSlot> descendingComparator;


    /**
     * This method redirects ascendingComparator to a comparator that sorts jewels.
     * @param instance Instance of ListSortType
     * @return new comparator that also handles jewels in ascending order
     */
    @Redirect(method = "sort", at = @At(value = "FIELD", target = "Lmekanism/common/inventory/container/QIOItemViewerContainer$ListSortType;ascendingComparator:Ljava/util/Comparator;", opcode = Opcodes.GETFIELD))
    private Comparator<ISlotClickHandler.IScrollableSlot> redirectAscending(QIOItemViewerContainer.ListSortType instance)
    {
        return this.ascendingComparator.thenComparing((stack1, stack2) ->
        {
            if (Screen.hasShiftDown())
            {
                return 0;
            }

            ItemStack leftStack = stack1.getItem().getStack();
            ItemStack rightStack = stack2.getItem().getStack();

            if (!stack1.getModID().equals(stack2.getModID()))
            {
                // some small cleanup. We want to sort only vault items.
                return String.CASE_INSENSITIVE_ORDER.compare(
                    stack1.getModID(),
                    stack2.getModID());
            }

            int registryOrder = SortingHelper.compareRegistryNames(
                leftStack.getItem().getRegistryName(),
                rightStack.getItem().getRegistryName(),
                true);

            if (registryOrder != 0 || !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
            {
                // Use default string comparing
                return registryOrder;
            }
            else
            {
                Configuration.SortBy sortBy = switch (instance) {
                    case NAME -> Configuration.SortBy.NAME;
                    case SIZE -> Configuration.SortBy.AMOUNT;
                    case MOD -> Configuration.SortBy.MOD;
                };
                return compareItems(leftStack, rightStack, sortBy, true);
            }
        });
    }


    /**
     * This method redirects descendingComparator to a comparator that sorts jewels.
     * @param instance Instance of ListSortType
     * @return new comparator that also handles jewels in descending order
     */
    @Redirect(method = "sort", at = @At(value = "FIELD", target = "Lmekanism/common/inventory/container/QIOItemViewerContainer$ListSortType;descendingComparator:Ljava/util/Comparator;", opcode = Opcodes.GETFIELD))
    private Comparator<ISlotClickHandler.IScrollableSlot> redirectDescending(QIOItemViewerContainer.ListSortType instance)
    {
        return this.descendingComparator.thenComparing((stack1, stack2) ->
        {
            if (Screen.hasShiftDown())
            {
                return 0;
            }

            ItemStack leftStack = stack1.getItem().getStack();
            ItemStack rightStack = stack2.getItem().getStack();

            if (!stack1.getModID().equals(stack2.getModID()))
            {
                // some small cleanup. We want to sort only vault items.
                return String.CASE_INSENSITIVE_ORDER.compare(
                    stack2.getModID(),
                    stack1.getModID());
            }

            int registryOrder = SortingHelper.compareRegistryNames(
                leftStack.getItem().getRegistryName(),
                rightStack.getItem().getRegistryName(),
                false);

            if (registryOrder != 0 || !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
            {
                // Use default string comparing
                return registryOrder;
            }
            else
            {
                Configuration.SortBy sortBy = switch (instance) {
                    case NAME -> Configuration.SortBy.NAME;
                    case SIZE -> Configuration.SortBy.AMOUNT;
                    case MOD -> Configuration.SortBy.MOD;
                };
                return compareItems(leftStack, rightStack, sortBy, false);
            }
        });
    }


    /**
     * This method compares two given items that have custom ordering.
     * @param leftStack First item to be compared
     * @param rightStack Second item to be compared
     * @param sortBy sorting type (name, amount, mod)
     * @param ascending order of items
     * @return order of items.
     */
    @Unique
    private static int compareItems(ItemStack leftStack,
        ItemStack rightStack,
        Configuration.SortBy sortBy,
        boolean ascending)
    {
        
        if (leftStack.getItem() == ModItems.JEWEL)
        {
            GearDataCache leftData = GearDataCache.of(leftStack);
            GearDataCache rightData = GearDataCache.of(rightStack);

            // Update item cache if vault versions mismatch.
            if (((IExtraGearDataCache) leftData).isInvalidCache())
            {
                GearDataCache.removeCache(leftStack);
                GearDataCache.createCache(leftStack);
                leftData = GearDataCache.of(leftStack);
            }

            // Update item cache if vault versions mismatch.
            if (((IExtraGearDataCache) rightData).isInvalidCache())
            {
                GearDataCache.removeCache(rightStack);
                GearDataCache.createCache(rightStack);
                rightData = GearDataCache.of(rightStack);
            }

            return SortingHelper.compareJewels(leftStack.getDisplayName().getString(),
                    leftData,
                    leftStack.getOrCreateTag().getInt("freeCuts"),
                    rightStack.getDisplayName().getString(),
                    rightData,
                    rightStack.getOrCreateTag().getInt("freeCuts"),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.TOOL)
        {
            // TODO: compare tools. Currently no.
            return 0;
        }
        else if (SortingHelper.VAULT_GEAR_SET.contains(leftStack.getItem().getRegistryName()))
        {
            return SortingHelper.compareVaultGear(leftStack.getDisplayName().getString(),
                    VaultGearData.read(leftStack),
                    rightStack.getDisplayName().getString(),
                    VaultGearData.read(rightStack),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.INSCRIPTION)
        {
            return SortingHelper.compareInscriptions(leftStack.getDisplayName().getString(),
                    InscriptionData.from(leftStack),
                    rightStack.getDisplayName().getString(),
                    InscriptionData.from(rightStack),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.VAULT_CRYSTAL)
        {
            return SortingHelper.compareVaultCrystals(leftStack.getDisplayName().getString(),
                    CrystalData.read(leftStack),
                    rightStack.getDisplayName().getString(),
                    CrystalData.read(rightStack),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.TRINKET)
        {
            return SortingHelper.compareTrinkets(leftStack.getDisplayName().getString(),
                    AttributeGearData.read(leftStack),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    AttributeGearData.read(rightStack),
                    rightStack.getTag(),
                    sortBy,
                    ascending);
        }
        else if (SortingHelper.VAULT_CHARMS.contains(leftStack.getItem().getRegistryName()))
        {
            return SortingHelper.compareCharms(leftStack.getDisplayName().getString(),
                    AttributeGearData.read(leftStack),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    AttributeGearData.read(rightStack),
                    rightStack.getTag(),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.VAULT_CATALYST_INFUSED)
        {
            return SortingHelper.compareCatalysts(leftStack.getDisplayName().getString(),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    rightStack.getTag(),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.VAULT_DOLL)
        {
            return SortingHelper.compareVaultDolls(leftStack.getDisplayName().getString(),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    rightStack.getTag(),
                    sortBy,
                    ascending);
        }
        else if (leftStack.getItem() == ModItems.CARD)
        {

            return SortingHelper.compareCards(leftStack.getDisplayName().getString(),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    rightStack.getTag(),
                    sortBy,
                    ascending);
        }
        //TODO:REFACTOR2[TAG]
        else
        {
            Integer simpleCmpRv = SortingHelper.simpleStackCompare(leftStack, rightStack, ascending);
            if (simpleCmpRv != null)
            {
                return simpleCmpRv;
            }
            return 0;
        }
    }
}
