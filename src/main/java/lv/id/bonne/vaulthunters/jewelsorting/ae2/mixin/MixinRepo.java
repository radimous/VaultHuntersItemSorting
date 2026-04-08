//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.ae2.mixin;


import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

            String leftName = leftWhat.getDisplayName().getString();
            String rightName = rightWhat.getDisplayName().getString();

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


            if (registryOrder != 0 || !SortingHelper.isSortable(leftId))
            {
                // Use default string comparing
                return registryOrder;
            }
            else if (leftId == ModItems.JEWEL.getRegistryName())
            {  //TODO: can we nuke this?

                if (!leftTag.contains("clientCache") ||
                    !leftTag.getCompound("clientCache").contains(SortingHelper.EXTRA_ATTRIBUTE_INDEX) ||
                    !leftTag.getCompound("clientCache").contains(SortingHelper.EXTRA_ATTRIBUTE_VALUE) ||
                    !leftTag.getCompound("clientCache").contains(SortingHelper.EXTRA_GEAR_LEVEL) ||
                    !leftTag.getCompound("clientCache").contains(SortingHelper.EXTRA_JEWEL_SIZE) ||
                    !(leftTag.getCompound("clientCache").contains(SortingHelper.EXTRA_CACHE_VERSION) &&
                        leftTag.getCompound("clientCache").getString(SortingHelper.EXTRA_CACHE_VERSION).
                            equals(VaultJewelSorting.VAULT_MOD_VERSION)) ||
                    !rightTag.contains("clientCache") ||
                    !rightTag.getCompound("clientCache").contains(SortingHelper.EXTRA_ATTRIBUTE_INDEX) ||
                    !rightTag.getCompound("clientCache").contains(SortingHelper.EXTRA_ATTRIBUTE_VALUE) ||
                    !rightTag.getCompound("clientCache").contains(SortingHelper.EXTRA_GEAR_LEVEL) ||
                    !rightTag.getCompound("clientCache").contains(SortingHelper.EXTRA_JEWEL_SIZE) ||
                    !(rightTag.getCompound("clientCache").contains(SortingHelper.EXTRA_CACHE_VERSION) &&
                        rightTag.getCompound("clientCache").getString(SortingHelper.EXTRA_CACHE_VERSION).
                            equals(VaultJewelSorting.VAULT_MOD_VERSION)))
                {
                    // Client cache is not generated. Process everything manually.
                    VaultGearData leftData = CustomVaultGearData.read(leftTag);
                    VaultGearData rightData = CustomVaultGearData.read(rightTag);

                    return SortingHelper.compareJewels(leftName,
                            leftData,
                            leftTag.getInt("freeCuts"),
                            rightName,
                            rightData,
                            rightTag.getInt("freeCuts"),
                            VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(sortBy),
                            ascending);
                }
                else
                {
                    return SortingHelper.compareJewels(leftName,
                            leftTag.getCompound("clientCache"),
                            leftTag.getInt("freeCuts"),
                            rightName,
                            rightTag.getCompound("clientCache"),
                            rightTag.getInt("freeCuts"),
                            VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(sortBy),
                            ascending);
                }
            }
            else if (leftId == ModItems.INSCRIPTION.getRegistryName())
            {
                InscriptionData leftData = InscriptionData.empty();
                leftData.deserializeNBT(leftWhat.getTag().getCompound("data"));

                InscriptionData rightData = InscriptionData.empty();
                rightData.deserializeNBT(rightWhat.getTag().getCompound("data"));

                return SortingHelper.compareInscriptions(leftName,
                        leftData,
                        rightName,
                        rightData,
                        VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(sortBy),
                        ascending);
            }
            else if (leftId == ModItems.VAULT_CRYSTAL.getRegistryName())
            {
                CrystalData leftData = CrystalData.empty();
                leftData.readNbt(leftWhat.getTag().getCompound("CrystalData"));

                CrystalData rightData = CrystalData.empty();
                rightData.readNbt(rightWhat.getTag().getCompound("CrystalData"));

                return SortingHelper.compareVaultCrystals(leftName,
                        leftData,
                        rightName,
                        rightData,
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy),
                        ascending);
            }
            else if (leftId == ModItems.TRINKET.getRegistryName())
            {
                AttributeGearData leftData = CustomVaultGearData.read(leftWhat.getTag());
                AttributeGearData rightData = CustomVaultGearData.read(rightWhat.getTag());

                return SortingHelper.compareTrinkets(leftName,
                        leftData,
                        leftWhat.getTag(),
                        rightName,
                        rightData,
                        rightWhat.getTag(),
                        VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(sortBy),
                        ascending);
            }
            else if (SortingHelper.VAULT_CHARMS.contains(leftId))
            {
                AttributeGearData leftData = CustomVaultGearData.read(leftWhat.getTag());
                AttributeGearData rightData = CustomVaultGearData.read(rightWhat.getTag());

                return SortingHelper.compareCharms(leftName,
                        leftData,
                        leftWhat.getTag(),
                        rightName,
                        rightData,
                        rightWhat.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(sortBy),
                        ascending);
            }
            else if (leftId == ModItems.VAULT_CATALYST_INFUSED.getRegistryName())
            {
                return SortingHelper.compareCatalysts(leftName,
                        leftWhat.getTag(),
                        rightName,
                        rightWhat.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy),
                        ascending);
            }
            else if (leftId == ModItems.VAULT_DOLL.getRegistryName())
            {
                return SortingHelper.compareVaultDolls(leftName,
                        leftWhat.getTag(),
                        rightName,
                        rightWhat.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy),
                        ascending);
            }

            else if (leftId == ModItems.CARD.getRegistryName())
            {
                return SortingHelper.compareCards(leftName,
                        leftWhat.getTag(),
                        rightName,
                        rightWhat.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy),
                        ascending);
            }
            //TODO:REFACTOR1[TAG]
            else
            {
                if (CustomVaultGearData.hasVaultGearData(leftTag) && CustomVaultGearData.hasVaultGearData(rightTag)) {
                    VaultGearData leftData = CustomVaultGearData.read(leftWhat.getTag());
                    VaultGearData rightData = CustomVaultGearData.read(rightWhat.getTag());
                    return SortingHelper.compareVaultGear(leftName,
                            leftData,
                            rightName,
                            rightData,
                            VaultJewelSorting.CONFIGURATION.getGearSortingOptions(sortBy),
                            ascending);
                }
                Integer simpleCmpRv = SortingHelper.simpleItemAndTagCompare(leftItem, leftTag, rightItem, rightTag, ascending);
                if (simpleCmpRv != null) {
                    return simpleCmpRv;
                }
            }
            return 0;
        }));
    }
}
