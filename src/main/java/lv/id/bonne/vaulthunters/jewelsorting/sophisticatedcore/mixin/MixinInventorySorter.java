package lv.id.bonne.vaulthunters.jewelsorting.sophisticatedcore.mixin;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.util.InventorySorter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Comparator;
import java.util.Map;

@Mixin(value = InventorySorter.class, remap = false)
public class MixinInventorySorter
{
    @ModifyArg(method = "sortHandler", at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"))
    private static Comparator<Map.Entry<ItemStackKey, Integer>> compareJewels(Comparator<Map.Entry<ItemStackKey, Integer>> original)
    {

        final Configuration.SortBy sortBy = original == InventorySorter.BY_COUNT ? Configuration.SortBy.AMOUNT : Configuration.SortBy.NAME;
        return original.thenComparing((first, second) ->
        {
            final ItemStack leftStack = first.getKey().getStack();
            final ItemStack rightStack = second.getKey().getStack();

            int registryOrder = SortingHelper.compareRegistryNames(
                leftStack.getItem().getRegistryName(),
                rightStack.getItem().getRegistryName(),
                true);

            if (registryOrder != 0 ||
                !SortingHelper.isSortable(leftStack.getItem().getRegistryName()))
            {
                // Use default string comparing
                return registryOrder;
            }
            else if (leftStack.getItem() == ModItems.JEWEL)
            {
                return SortingHelper.compareJewels(
                    leftStack.getDisplayName().getString(),
                    VaultGearData.read(leftStack),
                    leftStack.getOrCreateTag().getInt("freeCuts"),
                    rightStack.getDisplayName().getString(),
                    VaultGearData.read(rightStack),
                    rightStack.getOrCreateTag().getInt("freeCuts"),
                    VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(sortBy),
                    true);
            }
            else if (leftStack.getItem() == ModItems.TOOL)
            {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                return SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    true));
            }
            else if (SortingHelper.VAULT_GEAR_SET.contains(leftStack.getItem().getRegistryName()))
            {
                return SortingHelper.compareVaultGear(
                    leftStack.getDisplayName().getString(),
                    VaultGearData.read(leftStack),
                    rightStack.getDisplayName().getString(),
                    VaultGearData.read(rightStack),
                    VaultJewelSorting.CONFIGURATION.getGearSortingOptions(sortBy),
                    true);
            }
            else if (leftStack.getItem() == ModItems.INSCRIPTION)
            {
                return SortingHelper.compareInscriptions(
                    leftStack.getDisplayName().getString(),
                    InscriptionData.from(leftStack),
                    rightStack.getDisplayName().getString(),
                    InscriptionData.from(rightStack),
                    VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(sortBy),
                    true);
            }
            else if (leftStack.getItem() == ModItems.VAULT_CRYSTAL)
            {
                if (!VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareVaultCrystals(
                        leftStack.getDisplayName().getString(),
                        CrystalData.read(leftStack),
                        rightStack.getDisplayName().getString(),
                        CrystalData.read(rightStack),
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy),
                        true);
                }
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
                if (!VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(sortBy).isEmpty())
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
            }
            else if (leftStack.getItem() == ModItems.VAULT_CATALYST_INFUSED)
            {
                if (!VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareCatalysts(
                        leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy),
                        true);
                }
            }
            else if (leftStack.getItem() == ModItems.VAULT_DOLL)
            {
                if (!VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareVaultDolls(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy),
                        true);
                }
            }
            else if (leftStack.getItem() == ModItems.CARD)
            {
                if (!VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareCards(leftStack.getDisplayName().getString(),
                        leftStack.getTag(),
                        rightStack.getDisplayName().getString(),
                        rightStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy),
                        true);
                }
            }
            else {
                Integer simpleCmpRv = SortingHelper.simpleStackCompare(leftStack, rightStack, true);
                if (simpleCmpRv != null) {
                    return simpleCmpRv;
                }
            }

            return 0;
        });
    }
}
