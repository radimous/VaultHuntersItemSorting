//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.ae2.mixin;


import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Comparator;

import appeng.api.config.SortDir;
import appeng.api.config.SortOrder;
import appeng.api.stacks.AEItemKey;
import appeng.client.gui.me.common.Repo;
import appeng.menu.me.common.GridInventoryEntry;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.CustomVaultGearData;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;


/**
 * This mixin handles custom jewel sorting order for ae2.
 */
@Mixin(Repo.class)
public abstract class MixinRepo
{
    @Inject(method = "getComparator",
        at = @At("RETURN"),
        cancellable = true,
        remap = false)
    public final void comparator(SortOrder sortOrder,
        SortDir sortDir,
        CallbackInfoReturnable<Comparator<GridInventoryEntry>> cir)
    {
        if (Screen.hasShiftDown())
        {
            return;
        }

        boolean ascending = sortDir == SortDir.ASCENDING;
        Configuration.SortBy sortBy = switch (sortOrder) {
            case NAME -> Configuration.SortBy.NAME;
            case AMOUNT -> Configuration.SortBy.AMOUNT;
            case MOD -> Configuration.SortBy.MOD;
        };

        cir.setReturnValue(cir.getReturnValue().thenComparing((left, right) ->
        {
            AEItemKey leftWhat = left.getWhat() instanceof AEItemKey itemKey ? itemKey : null;
            AEItemKey rightWhat = right.getWhat() instanceof AEItemKey itemKey ? itemKey : null;

            if (leftWhat == null || rightWhat == null)
            {
                // Null-pointer check
                return leftWhat == null ? 1 : -1;
            }

            if (!leftWhat.getModId().equals(rightWhat.getModId()))
            {
                // some small cleanup. We want to sort only vault items.
                return String.CASE_INSENSITIVE_ORDER.compare(
                    leftWhat.getModId(),
                    rightWhat.getModId());
            }

            final ResourceLocation leftId = leftWhat.getId();

            final Item leftItem = leftWhat.getItem();
            final Item rightItem = rightWhat.getItem();

            final CompoundTag leftTag = leftWhat.getTag();
            final CompoundTag rightTag = rightWhat.getTag();

            int registryOrder = SortingHelper.compareRegistryNames(
                leftId,
                rightWhat.getId(),
                ascending);

            if (registryOrder != 0 || !SortingHelper.isSortable(leftId) || leftTag == null || rightTag == null)
            {
                // Use default string comparing
                return registryOrder;
            }

            ItemStack leftStack = new ItemStack(leftItem);
            leftStack.setTag(leftTag);
            ItemStack rightStack = new ItemStack(rightItem);
            rightStack.setTag(rightTag);

            Integer cmpRv = SortingHelper.compareItems(
                leftStack,
                rightStack,
                leftWhat.getDisplayName().getString(),
                rightWhat.getDisplayName().getString(),
                sortBy,
                ascending);
            return cmpRv == null ? 0 : cmpRv;
        }));
    }
}
