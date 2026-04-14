//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.quark.mixin;


import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.quark.base.handler.SortingHandler;


/**
 * This mixin handles custom jewel sorting order for quark.
 */
@Mixin(SortingHandler.class)
public class MixinSortingHandler {
    /**
     * This method injects code at the start of stackCompare to sort jewel items.
     *
     * @param leftStack  The first stack item.
     * @param rightStack The second stack item.
     * @param cir        The callback info returnable.
     */
    @Inject(method = "stackCompare(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)I",
        at = @At("RETURN"),
        cancellable = true,
        remap = false)
    private static void stackCompare(ItemStack leftStack, ItemStack rightStack, CallbackInfoReturnable<Integer> cir) {
        Integer cmpRv = SortingHelper.compareItems(
            leftStack,
            rightStack,
            Configuration.SortBy.NAME,
            true);
        if (cmpRv != null && cmpRv != 0) {
            cir.setReturnValue(cmpRv);
        }
    }
}
