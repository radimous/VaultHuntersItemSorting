package lv.id.bonne.vaulthunters.jewelsorting.storagenetwork.mixin;


import com.lothrazar.storagenetwork.api.IGuiNetwork;
import com.lothrazar.storagenetwork.gui.NetworkWidget;
import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import java.util.Comparator;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;


/**
 * This mixin is used to sort jewels in Simple Storage Network mod.
 */
@Mixin(value = NetworkWidget.class, remap = false)
public class MixinNetworkWidget
{
    /**
     * The gui that is used to sort jewels.
     */
    @Shadow
    @Final
    private IGuiNetwork gui;


    /**
     * This method modifies the sortStackWrappers method to sort jewels.
     * It is done by modifying second parameter in Collections#sort call inside sortStackWrappers method.
     * @param original The original comparator.
     * @return The modified comparator that sorts jewels.
     */
    @ModifyArg(method = "sortStackWrappers", at = @At(value = "INVOKE", target = "Ljava/util/Collections;sort(Ljava/util/List;Ljava/util/Comparator;)V"), index = 1)
    private Comparator<ItemStack> sortStackWrappersJewelCompare(Comparator<ItemStack> original)
    {
        return original.thenComparing((first, second) ->
        {


            if (Screen.hasShiftDown())
            {
                // Fast exit on shift+click.
                return 0;
            }

            int registryOrder = SortingHelper.compareRegistryNames(
                first.getItem().getRegistryName(),
                second.getItem().getRegistryName(),
                this.gui.getDownwards()); // TODO: use this for ascending?

            Configuration.SortBy sortBy = switch (this.gui.getSort()) {
                case NAME -> Configuration.SortBy.NAME;
                case AMOUNT -> Configuration.SortBy.AMOUNT;
                case MOD -> Configuration.SortBy.MOD;
            };

            if (registryOrder != 0 || !SortingHelper.isSortable(first.getItem().getRegistryName()))
            {
                // Use default string comparing
                return registryOrder;
            }
            else if (first.getItem() == ModItems.JEWEL)
            {
                String leftName = first.getDisplayName().getString();
                String rightName = second.getDisplayName().getString();
                VaultGearData leftData = VaultGearData.read(first);
                VaultGearData rightData = VaultGearData.read(second);

                return SortingHelper.compareJewels(leftName,
                        leftData,
                        first.getOrCreateTag().getInt("freeCuts"),
                        rightName,
                        rightData,
                        second.getOrCreateTag().getInt("freeCuts"),
                        VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(sortBy),
                        this.gui.getDownwards());
            }
            else if (first.getItem() == ModItems.TOOL)
            {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                callbackInfoReturnable.setReturnValue(SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    sortingDirection == SortingDirection.ASCENDING));
            }
            else if (SortingHelper.VAULT_GEAR_SET.contains(first.getItem().getRegistryName()))
            {
                String leftName = first.getDisplayName().getString();
                String rightName = second.getDisplayName().getString();
                VaultGearData leftData = VaultGearData.read(first);
                VaultGearData rightData = VaultGearData.read(second);

                return SortingHelper.compareVaultGear(leftName,
                        leftData,
                        rightName,
                        rightData,
                        VaultJewelSorting.CONFIGURATION.getGearSortingOptions(sortBy),
                        this.gui.getDownwards());
            }
            else if (first.getItem() == ModItems.INSCRIPTION)
            {
                String leftName = first.getDisplayName().getString();
                String rightName = second.getDisplayName().getString();
                InscriptionData leftData = InscriptionData.from(first);
                InscriptionData rightData = InscriptionData.from(second);

                return SortingHelper.compareInscriptions(leftName,
                        leftData,
                        rightName,
                        rightData,
                        VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(sortBy),
                        true);
            }
            else if (first.getItem() == ModItems.VAULT_CRYSTAL)
            {
                String leftName = first.getDisplayName().getString();
                String rightName = second.getDisplayName().getString();
                CrystalData leftData = CrystalData.read(first);
                CrystalData rightData = CrystalData.read(second);

                return SortingHelper.compareVaultCrystals(leftName,
                        leftData,
                        rightName,
                        rightData,
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy),
                        true);
            }
            else if (first.getItem() == ModItems.TRINKET)
            {
                return SortingHelper.compareTrinkets(first.getDisplayName().getString(),
                        AttributeGearData.read(first),
                        first.getTag(),
                        second.getDisplayName().getString(),
                        AttributeGearData.read(second),
                        second.getTag(),
                        VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(sortBy),
                        true);
            }
            else if (SortingHelper.VAULT_CHARMS.contains(first.getItem().getRegistryName()))
            {
                return  SortingHelper.compareCharms(first.getDisplayName().getString(),
                        AttributeGearData.read(first),
                        first.getTag(),
                        second.getDisplayName().getString(),
                        AttributeGearData.read(second),
                        second.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(sortBy),
                        true);

            }
            else if (first.getItem() == ModItems.VAULT_CATALYST_INFUSED)
            {
                return SortingHelper.compareCatalysts(first.getDisplayName().getString(),
                        first.getTag(),
                        second.getDisplayName().getString(),
                        second.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy),
                        true);
            }
            else if (first.getItem() == ModItems.VAULT_DOLL)
            {
                return SortingHelper.compareVaultDolls(first.getDisplayName().getString(),
                        first.getTag(),
                        second.getDisplayName().getString(),
                        second.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy),
                        true);
            }
            else if (first.getItem() == ModItems.CARD)
            {
                return SortingHelper.compareCards(first.getDisplayName().getString(),
                        first.getTag(),
                        second.getDisplayName().getString(),
                        second.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy),
                        true);
            }
            else
            {
                Integer simpleCmpRv = SortingHelper.simpleStackCompare(first, second, true);
                if (simpleCmpRv != null) {
                    return simpleCmpRv;
                }
            }
            return 0;
        });
    }
}
