package lv.id.bonne.vaulthunters.jewelsorting.sophisticatedcore.mixin;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.data.InscriptionData;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Comparator;
import java.util.Map;

@Mixin(value = InventoryHelper.class, remap = false)
public class MixinInventoryHelper {

    // used for backpack tooltips
    // same as MixinInventorySorter but just the `if (original == InventorySorter.BY_COUNT)` branch
    @ModifyArg(method = "getCompactedStacksSortedByCount", at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"))
    private static Comparator<Map.Entry<ItemStackKey, Integer>> compareJewels(Comparator<Map.Entry<ItemStackKey, Integer>> original) {
            return original.thenComparing((first, second) ->
            {
                final ItemStack firstStack = first.getKey().getStack();
                final ItemStack secondStack = second.getKey().getStack();

                int registryOrder = SortingHelper.compareRegistryNames(
                    firstStack.getItem().getRegistryName(),
                    secondStack.getItem().getRegistryName(),
                    true);

                if (registryOrder != 0 ||
                    !SortingHelper.isSortable(firstStack.getItem().getRegistryName())) {
                    // Use default string comparing
                    return registryOrder;
                } else if (firstStack.getItem() == ModItems.JEWEL) {
                    return SortingHelper.compareJewels(
                        firstStack.getDisplayName().getString(),
                        VaultGearData.read(firstStack),
                        firstStack.getOrCreateTag().getInt("freeCuts"),
                        secondStack.getDisplayName().getString(),
                        VaultGearData.read(secondStack),
                        secondStack.getOrCreateTag().getInt("freeCuts"),
                        VaultJewelSorting.CONFIGURATION.getJewelSortingByAmount(),
                        true);
                } else if (firstStack.getItem() == ModItems.TOOL) {
// TODO: Compare vault tools by their type? Currently is left just to filter out from VaultGearItem
//                return SortingHelper.compareTools(
//                    VaultGearData.read(leftStack),
//                    VaultGearData.read(rightStack),
//                    true));
                } else if (SortingHelper.VAULT_GEAR_SET.contains(firstStack.getItem().getRegistryName())) {
                    return SortingHelper.compareVaultGear(
                        firstStack.getDisplayName().getString(),
                        VaultGearData.read(firstStack),
                        secondStack.getDisplayName().getString(),
                        VaultGearData.read(secondStack),
                        VaultJewelSorting.CONFIGURATION.getGearSortingByAmount(),
                        true);
                } else if (firstStack.getItem() == ModItems.INSCRIPTION) {
                    return SortingHelper.compareInscriptions(
                        firstStack.getDisplayName().getString(),
                        InscriptionData.from(firstStack),
                        secondStack.getDisplayName().getString(),
                        InscriptionData.from(secondStack),
                        VaultJewelSorting.CONFIGURATION.getInscriptionSortingByAmount(),
                        true);
                } else if (firstStack.getItem() == ModItems.VAULT_CRYSTAL) {
                    if (!VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingByAmount().isEmpty()) {
                        return SortingHelper.compareVaultCrystals(
                            firstStack.getDisplayName().getString(),
                            CrystalData.read(firstStack),
                            secondStack.getDisplayName().getString(),
                            CrystalData.read(secondStack),
                            VaultJewelSorting.CONFIGURATION.getVaultCrystalSortingByAmount(),
                            true);
                    }
                } else if (firstStack.getItem() == ModItems.TRINKET) {
                    if (!VaultJewelSorting.CONFIGURATION.getTrinketSortingByAmount().isEmpty()) {
                        return SortingHelper.compareTrinkets(firstStack.getDisplayName().getString(),
                            AttributeGearData.read(firstStack),
                            firstStack.getTag(),
                            secondStack.getDisplayName().getString(),
                            AttributeGearData.read(secondStack),
                            secondStack.getTag(),
                            VaultJewelSorting.CONFIGURATION.getTrinketSortingByAmount(),
                            true);
                    }
                } else if (SortingHelper.VAULT_CHARMS.contains(firstStack.getItem().getRegistryName())) {
                    if (!VaultJewelSorting.CONFIGURATION.getCharmSortingByAmount().isEmpty()) {
                        return SortingHelper.compareCharms(firstStack.getDisplayName().getString(),
                            AttributeGearData.read(firstStack),
                            firstStack.getTag(),
                            secondStack.getDisplayName().getString(),
                            AttributeGearData.read(secondStack),
                            secondStack.getTag(),
                            VaultJewelSorting.CONFIGURATION.getCharmSortingByAmount(),
                            true);
                    }
                } else if (firstStack.getItem() == ModItems.VAULT_CATALYST_INFUSED) {
                    if (!VaultJewelSorting.CONFIGURATION.getCatalystSortingByAmount().isEmpty()) {
                        return SortingHelper.compareCatalysts(
                            firstStack.getDisplayName().getString(),
                            firstStack.getTag(),
                            secondStack.getDisplayName().getString(),
                            secondStack.getTag(),
                            VaultJewelSorting.CONFIGURATION.getCatalystSortingByAmount(),
                            true);
                    }
                } else if (firstStack.getItem() == ModItems.VAULT_DOLL) {
                    if (!VaultJewelSorting.CONFIGURATION.getDollSortingByAmount().isEmpty()) {
                        return SortingHelper.compareVaultDolls(firstStack.getDisplayName().getString(),
                            firstStack.getTag(),
                            secondStack.getDisplayName().getString(),
                            secondStack.getTag(),
                            VaultJewelSorting.CONFIGURATION.getDollSortingByAmount(),
                            true);
                    }
                } else if (firstStack.getItem() == ModItems.RELIC_FRAGMENT) {
                    return SortingHelper.compareRelicFragments(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.RESPEC_FLASK) {
                    return SortingHelper.compareRespecFlasks(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.FACETED_FOCUS) {
                    return SortingHelper.compareFacedFocus(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.AUGMENT) {
                    return SortingHelper.compareAugments(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.CARD) {
                    if (!VaultJewelSorting.CONFIGURATION.getCardSortingByAmount().isEmpty()) {
                        return SortingHelper.compareCards(firstStack.getDisplayName().getString(),
                            firstStack.getTag(),
                            secondStack.getDisplayName().getString(),
                            secondStack.getTag(),
                            VaultJewelSorting.CONFIGURATION.getCardSortingByAmount(),
                            true);
                    }
                } else if (firstStack.getItem() == ModItems.CARD_DECK) {
                    return SortingHelper.compareDecks(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.BOOSTER_PACK) {
                    return SortingHelper.compareBoosterPacks(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.ANTIQUE) {
                    return SortingHelper.compareAntique(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.JEWEL_POUCH) {
                    return SortingHelper.comparePouches(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);
                } else if (firstStack.getItem() == ModItems.COMPANION_RELIC) {
                    return SortingHelper.compareCompanionRelics(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);

                } else if (firstStack.getItem() == ModItems.COMPANION_PARTICLE_TRAIL) {
                    return SortingHelper.compareCompanionParticleTrails(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);

                } else if (firstStack.getItem() == ModItems.TEMPORAL_SHARD) {
                    return SortingHelper.compareTemporalShards(
                        firstStack.getTag(),
                        secondStack.getTag(),
                        true);

                }

                return 0;
            });
    }
}
