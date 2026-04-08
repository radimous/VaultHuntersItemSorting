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
@Mixin(value = StoredItemStack.ComparatorAmount.class, remap = false)
public class MixinStoredItemStackComparatorAmount
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

        int registryOrder = SortingHelper.compareRegistryNames(
            leftStack.getItem().getRegistryName(),
            rightStack.getItem().getRegistryName(),
            !this.reversed);

        if (registryOrder != 0 || !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
        {
            // If registry order is not 0 or item is not sortable, then return it.
            cir.setReturnValue(registryOrder);
        }
        else if (leftStack.getItem() == ModItems.JEWEL)
        {
            if (!VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                GearDataCache leftData = GearDataCache.of(leftStack);
                GearDataCache rightData = GearDataCache.of(rightStack);

                // Update item cache if vault versions mismatch.
                if (((IExtraGearDataCache) leftData).isInvalidCache())
                {
                    GearDataCache.removeCache(leftStack);
                    GearDataCache.createCache(leftStack);
                }

                // Update item cache if vault versions mismatch.
                if (((IExtraGearDataCache) rightData).isInvalidCache())
                {
                    GearDataCache.removeCache(rightStack);
                    GearDataCache.createCache(rightStack);
                }

                cir.setReturnValue(SortingHelper.compareJewels(left.getDisplayName(),
                    GearDataCache.of(leftStack),
                    leftStack.getOrCreateTag().getInt("freeCuts"),
                    right.getDisplayName(),
                    GearDataCache.of(rightStack),
                    rightStack.getOrCreateTag().getInt("freeCuts"),
                    VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(Configuration.SortBy.AMOUNT),
                    !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.TOOL)
        {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                cir.setReturnValue(SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    sortingDirection == SortingDirection.ASCENDING));
        }
        else if (SortingHelper.VAULT_GEAR_SET.contains(leftStack.getItem().getRegistryName()))
        {
            if (!VaultJewelSorting.CONFIGURATION.getGearSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(SortingHelper.compareVaultGear(left.getDisplayName(),
                    VaultGearData.read(leftStack),
                    right.getDisplayName(),
                    VaultGearData.read(rightStack),
                    VaultJewelSorting.CONFIGURATION.getGearSortingOptions(Configuration.SortBy.AMOUNT),
                    !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.INSCRIPTION)
        {
            if (!VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(SortingHelper.compareInscriptions(left.getDisplayName(),
                    InscriptionData.from(leftStack),
                    right.getDisplayName(),
                    InscriptionData.from(rightStack),
                    VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(Configuration.SortBy.AMOUNT),
                    !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.VAULT_CRYSTAL)
        {
            if (!VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareVaultCrystals(leftStack.getDisplayName().getString(),
                        CrystalData.read(leftStack),
                        rightStack.getDisplayName().getString(),
                        CrystalData.read(rightStack),
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(Configuration.SortBy.AMOUNT),
                        !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.TRINKET)
        {
            if (!VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareTrinkets(leftStack.getDisplayName().getString(),
                        AttributeGearData.read(leftStack),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        AttributeGearData.read(rightStack),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(Configuration.SortBy.AMOUNT),
                        !this.reversed));
            }
        }
        else if (SortingHelper.VAULT_CHARMS.contains(leftStack.getItem().getRegistryName()))
        {
            if (!VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareCharms(leftStack.getDisplayName().getString(),
                        AttributeGearData.read(leftStack),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        AttributeGearData.read(rightStack),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(Configuration.SortBy.AMOUNT),
                        !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.VAULT_CATALYST_INFUSED)
        {
            if (!VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareCatalysts(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(Configuration.SortBy.AMOUNT),
                        !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.VAULT_DOLL)
        {
            if (!VaultJewelSorting.CONFIGURATION.getDollSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareVaultDolls(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(Configuration.SortBy.AMOUNT),
                        !this.reversed));
            }
        }
        else if (leftStack.getItem() == ModItems.CARD)
        {
            if (!VaultJewelSorting.CONFIGURATION.getCardSortingOptions(Configuration.SortBy.AMOUNT).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareCards(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCardSortingOptions(Configuration.SortBy.AMOUNT),
                        !this.reversed));
            }
        }
            else {
            Integer simpleCmpRv = SortingHelper.simpleStackCompare(leftStack, rightStack, !this.reversed);
            if (simpleCmpRv != null) {
                cir.setReturnValue(simpleCmpRv);
            }
        }
    }
}

