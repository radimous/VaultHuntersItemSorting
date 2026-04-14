package lv.id.bonne.vaulthunters.jewelsorting.integratedterminals.mixin;

import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.world.item.ItemStack;
import org.cyclops.integratedterminals.capability.ingredient.sorter.ItemStackIdSorter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStackIdSorter.class, remap = false)
public class MixinItemStackIdSorter {
    
    @Inject(method = "compare(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)I", at = @At("RETURN"), cancellable = true)
    private void compare(ItemStack o1, ItemStack o2, CallbackInfoReturnable<Integer> cir) {
       var result = cir.getReturnValue();
       if (result != 0) {
           return;
       }

        var cmp = SortingHelper.compareItems(
            o1,
            o2,
            o1.getHoverName().getString(),
            o2.getHoverName().getString(),
            Configuration.SortBy.NAME,
            true
        );
       if (cmp != null) {
           cir.setReturnValue(cmp);
       }
    }
}
