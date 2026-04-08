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
            final ItemStack firstStack = first.getKey().getStack();
            final ItemStack secondStack = second.getKey().getStack();

            int registryOrder = SortingHelper.compareRegistryNames(
                firstStack.getItem().getRegistryName(),
                secondStack.getItem().getRegistryName(),
                true);

            if (registryOrder != 0 ||
                !SortingHelper.isSortable(firstStack.getItem().getRegistryName()))
            {
                // Use default string comparing
                return registryOrder;
            }
            else if (firstStack.getItem() == ModItems.JEWEL)
            {
                return SortingHelper.compareJewels(
                    firstStack.getDisplayName().getString(),
                    VaultGearData.read(firstStack),
                    firstStack.getOrCreateTag().getInt("freeCuts"),
                    secondStack.getDisplayName().getString(),
                    VaultGearData.read(secondStack),
                    secondStack.getOrCreateTag().getInt("freeCuts"),
                    VaultJewelSorting.CONFIGURATION.getJewelSortingOptions(sortBy),
                    true);
            }
            else if (firstStack.getItem() == ModItems.TOOL)
            {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                return SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    true));
            }
            else if (SortingHelper.VAULT_GEAR_SET.contains(firstStack.getItem().getRegistryName()))
            {
                return SortingHelper.compareVaultGear(
                    firstStack.getDisplayName().getString(),
                    VaultGearData.read(firstStack),
                    secondStack.getDisplayName().getString(),
                    VaultGearData.read(secondStack),
                    VaultJewelSorting.CONFIGURATION.getGearSortingOptions(sortBy),
                    true);
            }
            else if (firstStack.getItem() == ModItems.INSCRIPTION)
            {
                return SortingHelper.compareInscriptions(
                    firstStack.getDisplayName().getString(),
                    InscriptionData.from(firstStack),
                    secondStack.getDisplayName().getString(),
                    InscriptionData.from(secondStack),
                    VaultJewelSorting.CONFIGURATION.getInscriptionSortingOptions(sortBy),
                    true);
            }
            else if (firstStack.getItem() == ModItems.VAULT_CRYSTAL)
            {
                if (!VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareVaultCrystals(
                        firstStack.getDisplayName().getString(),
                        CrystalData.read(firstStack),
                        secondStack.getDisplayName().getString(),
                        CrystalData.read(secondStack),
                        VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingOptions(sortBy),
                        true);
                }
            }
            else if (firstStack.getItem() == ModItems.TRINKET)
            {
                if (!VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareTrinkets(firstStack.getDisplayName().getString(),
                        AttributeGearData.read(firstStack),
                        firstStack.getTag(),
                        secondStack.getDisplayName().getString(),
                        AttributeGearData.read(secondStack),
                        secondStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getTrinketSortingOptions(sortBy),
                        true);
                }
            }
            else if (SortingHelper.VAULT_CHARMS.contains(firstStack.getItem().getRegistryName()))
            {
                if (!VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareCharms(firstStack.getDisplayName().getString(),
                        AttributeGearData.read(firstStack),
                        firstStack.getTag(),
                        secondStack.getDisplayName().getString(),
                        AttributeGearData.read(secondStack),
                        secondStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCharmSortingOptions(sortBy),
                        true);
                }
            }
            else if (firstStack.getItem() == ModItems.VAULT_CATALYST_INFUSED)
            {
                if (!VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareCatalysts(
                        firstStack.getDisplayName().getString(),
                        firstStack.getTag(),
                        secondStack.getDisplayName().getString(),
                        secondStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCatalystSortingOptions(sortBy),
                        true);
                }
            }
            else if (firstStack.getItem() == ModItems.VAULT_DOLL)
            {
                if (!VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareVaultDolls(firstStack.getDisplayName().getString(),
                        firstStack.getTag(),
                        secondStack.getDisplayName().getString(),
                        secondStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getDollSortingOptions(sortBy),
                        true);
                }
            }
            else if (firstStack.getItem() == ModItems.CARD)
            {
                if (!VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy).isEmpty())
                {
                    return SortingHelper.compareCards(firstStack.getDisplayName().getString(),
                        firstStack.getTag(),
                        secondStack.getDisplayName().getString(),
                        secondStack.getTag(),
                        VaultJewelSorting.CONFIGURATION.getCardSortingOptions(sortBy),
                        true);
                }
            }
            else {
                Integer simpleCmpRv = SortingHelper.simpleStackCompare(firstStack, secondStack, true);
                if (simpleCmpRv != null) {
                    return simpleCmpRv;
                }
            }

            return 0;
        });
    }
}
