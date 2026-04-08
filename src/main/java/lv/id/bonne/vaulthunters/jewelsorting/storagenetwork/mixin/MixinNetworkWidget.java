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
        return original.thenComparing((leftStack, rightStack) ->
        {

            if (Screen.hasShiftDown())
            {
                // Fast exit on shift+click.
                return 0;
            }

            int registryOrder = SortingHelper.compareRegistryNames(
                leftStack.getItem().getRegistryName(),
                rightStack.getItem().getRegistryName(),
                this.gui.getDownwards()); // TODO: use this for ascending?

            Configuration.SortBy sortBy = switch (this.gui.getSort()) {
                case NAME -> Configuration.SortBy.NAME;
                case AMOUNT -> Configuration.SortBy.AMOUNT;
                case MOD -> Configuration.SortBy.MOD;
            };

            if (registryOrder != 0 || !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
            {
                // Use default string comparing
                return registryOrder;
            }
            else if (leftStack.getItem() == ModItems.JEWEL)
            {
                return SortingHelper.compareJewels(leftStack.getDisplayName().getString(),
                        VaultGearData.read(leftStack),
                        leftStack.getOrCreateTag().getInt("freeCuts"),
                        rightStack.getDisplayName().getString(),
                        VaultGearData.read(rightStack),
                        rightStack.getOrCreateTag().getInt("freeCuts"),
                        VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(sortBy),
                        this.gui.getDownwards());
            }
            else if (leftStack.getItem() == ModItems.TOOL)
            {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                callbackInfoReturnable.setReturnValue(SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    sortingDirection == SortingDirection.ASCENDING));
            }
            else if (SortingHelper.VAULT_GEAR_SET.contains(leftStack.getItem().getRegistryName()))
            {

                return SortingHelper.compareVaultGear(leftStack.getDisplayName().getString(),
                        VaultGearData.read(leftStack),
                        rightStack.getDisplayName().getString(),
                        VaultGearData.read(rightStack),
                        VaultJewelSorting.CONFIGURATION.getGearSortingOptions(sortBy),
                        this.gui.getDownwards());
            }
            else if (leftStack.getItem() == ModItems.INSCRIPTION)
            {
                return SortingHelper.compareInscriptions(leftStack.getDisplayName().getString(),
                        InscriptionData.from(leftStack),
                        rightStack.getDisplayName().getString(),
                        InscriptionData.from(rightStack),
                        VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(sortBy),
                        true);
            }
            else if (leftStack.getItem() == ModItems.VAULT_CRYSTAL)
            {
                return SortingHelper.compareVaultCrystals(leftStack.getDisplayName().getString(),
                        CrystalData.read(leftStack),
                        rightStack.getDisplayName().getString(),
                        CrystalData.read(rightStack),
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy),
                        true);
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
                        true);
            }
            else if (SortingHelper.VAULT_CHARMS.contains(leftStack.getItem().getRegistryName()))
            {
                return SortingHelper.compareCharms(leftStack.getDisplayName().getString(),
                        AttributeGearData.read(leftStack),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        AttributeGearData.read(rightStack),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(sortBy),
                        true);

            }
            else if (leftStack.getItem() == ModItems.VAULT_CATALYST_INFUSED)
            {
                return SortingHelper.compareCatalysts(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy),
                        true);
            }
            else if (leftStack.getItem() == ModItems.VAULT_DOLL)
            {
                return SortingHelper.compareVaultDolls(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy),
                        true);
            }
            else if (leftStack.getItem() == ModItems.CARD)
            {
                return SortingHelper.compareCards(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy),
                        true);
            }
            else
            {
                Integer simpleCmpRv = SortingHelper.simpleStackCompare(leftStack, rightStack, true);
                if (simpleCmpRv != null) {
                    return simpleCmpRv;
                }
            }
            return 0;
        });
    }
}
