//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.quark.mixin;


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
     * @param leftStack The first stack item.
     * @param rightStack The second stack item.
     * @param cir The callback info returnable.
     */
    @Inject(method = "stackCompare(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)I",
        at = @At("RETURN"),
        cancellable = true,
        remap = false)
    private static void stackCompare(ItemStack leftStack, ItemStack rightStack, CallbackInfoReturnable<Integer> cir)
    {
        if (leftStack == rightStack ||
            !leftStack.getItem().equals(rightStack.getItem()) ||
            !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
        {
            // Leave to original code.
            return;
        }

        // Deal with Jewels
        if (leftStack.getItem() == ModItems.JEWEL)
        {

            cir.setReturnValue(
                SortingHelper.compareJewels(leftStack.getDisplayName().getString(),
                    VaultGearData.read(leftStack),
                    leftStack.getOrCreateTag().getInt("freeCuts"),
                    rightStack.getDisplayName().getString(),
                    VaultGearData.read(rightStack),
                    rightStack.getOrCreateTag().getInt("freeCuts"),
                    Configuration.SortBy.NAME,
                    true));

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
            cir.setReturnValue(
                SortingHelper.compareVaultGear(leftStack.getDisplayName().getString(),
                    VaultGearData.read(leftStack),
                    rightStack.getDisplayName().getString(),
                    VaultGearData.read(rightStack),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (leftStack.getItem() == ModItems.INSCRIPTION)
        {
            cir.setReturnValue(
                SortingHelper.compareInscriptions(leftStack.getDisplayName().getString(),
                    InscriptionData.from(leftStack),
                    rightStack.getDisplayName().getString(),
                    InscriptionData.from(rightStack),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (leftStack.getItem() == ModItems.VAULT_CATALYST_INFUSED)
        {

            cir.setReturnValue(
                SortingHelper.compareCatalysts(leftStack.getDisplayName().getString(),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    rightStack.getTag(),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (leftStack.getItem() == ModItems.VAULT_CRYSTAL)
        {
            cir.setReturnValue(
                SortingHelper.compareVaultCrystals(leftStack.getDisplayName().getString(),
                    CrystalData.read(leftStack),
                    rightStack.getDisplayName().getString(),
                    CrystalData.read(rightStack),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (leftStack.getItem() == ModItems.TRINKET)
        {
            cir.setReturnValue(
                SortingHelper.compareTrinkets(leftStack.getDisplayName().getString(),
                    AttributeGearData.read(leftStack),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    AttributeGearData.read(rightStack),
                    rightStack.getTag(),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (SortingHelper.VAULT_CHARMS.contains(leftStack.getItem().getRegistryName()))
        {

            cir.setReturnValue(
                SortingHelper.compareCharms(leftStack.getDisplayName().getString(),
                    AttributeGearData.read(leftStack),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    AttributeGearData.read(rightStack),
                    rightStack.getTag(),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (leftStack.getItem() == ModItems.VAULT_DOLL)
        {
            cir.setReturnValue(
                SortingHelper.compareVaultDolls(leftStack.getDisplayName().getString(),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    rightStack.getTag(),
                    Configuration.SortBy.NAME,
                    true));

        }
        else if (leftStack.getItem() == ModItems.CARD)
        {
            cir.setReturnValue(
                SortingHelper.compareCards(leftStack.getDisplayName().getString(),
                    leftStack.getTag(),
                    rightStack.getDisplayName().getString(),
                    rightStack.getTag(),
                    Configuration.SortBy.NAME,
                    true));
        } else {
            Integer simpleCmpRv = SortingHelper.simpleStackCompare(leftStack, rightStack, true);
            if (simpleCmpRv != null)
            {
                cir.setReturnValue(simpleCmpRv);
            }
        }
    }
}
