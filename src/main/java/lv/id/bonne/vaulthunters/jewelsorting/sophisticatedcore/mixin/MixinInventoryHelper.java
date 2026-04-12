package lv.id.bonne.vaulthunters.jewelsorting.sophisticatedcore.mixin;

import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
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
    @ModifyArg(method = "getCompactedStacksSortedByCount", at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"))
    private static Comparator<Map.Entry<ItemStackKey, Integer>> compareJewels(Comparator<Map.Entry<ItemStackKey, Integer>> original) {
            return original.thenComparing((first, second) ->
            {
                final ItemStack leftStack = first.getKey().getStack();
                final ItemStack rightStack = second.getKey().getStack();

                Integer cmpRv = SortingHelper.compareItems(
                    leftStack,
                    rightStack,
                    Configuration.SortBy.AMOUNT,
                    true);
                return cmpRv == null ? 0 : cmpRv;
            });
    }
}
