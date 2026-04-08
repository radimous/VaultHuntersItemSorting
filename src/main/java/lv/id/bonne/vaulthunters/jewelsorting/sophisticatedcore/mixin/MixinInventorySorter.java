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
            Integer cmpRv = SortingHelper.compareItems(
                leftStack,
                rightStack,
                sortBy,
                true);
            return cmpRv == null ? 0 : cmpRv;
        });
    }
}
