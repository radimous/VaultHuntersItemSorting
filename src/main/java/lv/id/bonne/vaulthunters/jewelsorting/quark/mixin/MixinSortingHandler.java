//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.quark.mixin;


import iskallia.vault.item.SigilItem;
import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.world.item.ItemStack;
import vazkii.quark.base.handler.SortingHandler;


/**
 * This mixin handles custom jewel sorting order for quark.
 */
@Mixin(SortingHandler.class)
public class MixinSortingHandler
{
    /**
     * This method injects code at the start of stackCompare to sort jewel items.
     * @param stack1 The first stack item.
     * @param stack2 The second stack item.
     * @param cir The callback info returnable.
     */
    @Inject(method = "stackCompare(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)I",
        at = @At("RETURN"),
        cancellable = true,
        remap = false)
    private static void stackCompare(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Integer> cir)
    {
        if (stack1 == stack2 ||
            !stack1.getItem().equals(stack2.getItem()) ||
            !SortingHelper.isSortable(stack1.getItem().getRegistryName()))
        {
            // Leave to original code.
            return;
        }

        // Deal with Jewels
        if (stack1.getItem() == ModItems.JEWEL)
        {
            if (!VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareJewels(stack1.getDisplayName().getString(),
                        VaultGearData.read(stack1),
                        stack1.getOrCreateTag().getInt("freeCuts"),
                        stack2.getDisplayName().getString(),
                        VaultGearData.read(stack2),
                        stack2.getOrCreateTag().getInt("freeCuts"),
                        VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.TOOL)
        {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                cir.setReturnValue(SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    sortingDirection == SortingDirection.ASCENDING));
        }
        else if (SortingHelper.VAULT_GEAR_SET.contains(stack1.getItem().getRegistryName()))
        {
            if (!VaultJewelSorting.CONFIGURATION.getGearSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareVaultGear(stack1.getDisplayName().getString(),
                        VaultGearData.read(stack1),
                        stack2.getDisplayName().getString(),
                        VaultGearData.read(stack2),
                        VaultJewelSorting.CONFIGURATION.getGearSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.INSCRIPTION)
        {
            if (!VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareInscriptions(stack1.getDisplayName().getString(),
                        InscriptionData.from(stack1),
                        stack2.getDisplayName().getString(),
                        InscriptionData.from(stack2),
                        VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.VAULT_CATALYST_INFUSED)
        {
            if (!VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareCatalysts(stack1.getDisplayName().getString(),
                        stack1.getTag(),
                        stack2.getDisplayName().getString(),
                        stack2.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.VAULT_CRYSTAL)
        {
            if (!VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareVaultCrystals(stack1.getDisplayName().getString(),
                        CrystalData.read(stack1),
                        stack2.getDisplayName().getString(),
                        CrystalData.read(stack2),
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.TRINKET)
        {
            if (!VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareTrinkets(stack1.getDisplayName().getString(),
                        AttributeGearData.read(stack1),
                        stack1.getTag(),
                        stack2.getDisplayName().getString(),
                        AttributeGearData.read(stack2),
                        stack2.getTag(),
                        VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (SortingHelper.VAULT_CHARMS.contains(stack1.getItem().getRegistryName()))
        {
            if (!VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareCharms(stack1.getDisplayName().getString(),
                        AttributeGearData.read(stack1),
                        stack1.getTag(),
                        stack2.getDisplayName().getString(),
                        AttributeGearData.read(stack2),
                        stack2.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.VAULT_DOLL)
        {
            if (!VaultJewelSorting.CONFIGURATION.getDollSortingOptions(Configuration.SortBy.NAME).isEmpty())
            {
                cir.setReturnValue(
                    SortingHelper.compareVaultDolls(stack1.getDisplayName().getString(),
                        stack1.getTag(),
                        stack2.getDisplayName().getString(),
                        stack2.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(Configuration.SortBy.NAME),
                        true));
            }
        }
        else if (stack1.getItem() == ModItems.CARD)
        {
            cir.setReturnValue(
                SortingHelper.compareCards(stack1.getDisplayName().getString(),
                    stack1.getTag(),
                    stack2.getDisplayName().getString(),
                    stack2.getTag(),
                    VaultJewelSorting.CONFIGURATION.getCardSortingOptions(Configuration.SortBy.NAME),
                    true));
        } else {
            Integer simpleCmpRv = SortingHelper.simpleStackCompare(stack1, stack2, true);
            if (simpleCmpRv != null)
            {
                cir.setReturnValue(simpleCmpRv);
            }
        }
    }
}
