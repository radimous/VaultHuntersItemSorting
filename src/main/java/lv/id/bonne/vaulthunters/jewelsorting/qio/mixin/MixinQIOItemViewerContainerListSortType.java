//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.qio.mixin;


import lv.id.bonne.vaulthunters.jewelsorting.config.Configuration;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import mekanism.common.inventory.ISlotClickHandler;
import mekanism.common.inventory.container.QIOItemViewerContainer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Comparator;


/**
 * This mixin handles custom jewel sorting order for Mekanism QIO.
 */
@Mixin(value = QIOItemViewerContainer.ListSortType.class, remap = false)
public class MixinQIOItemViewerContainerListSortType
{
    /**
     * This shadow field stores ascendingComparator.
     */
    @Shadow
    @Final
    private Comparator<ISlotClickHandler.IScrollableSlot> ascendingComparator;

    /**
     * This shadow field stores descendingComparator.
     */
    @Shadow
    @Final
    private Comparator<ISlotClickHandler.IScrollableSlot> descendingComparator;


    /**
     * This method redirects ascendingComparator to a comparator that sorts jewels.
     * @param instance Instance of ListSortType
     * @return new comparator that also handles jewels in ascending order
     */
    @Redirect(method = "sort", at = @At(value = "FIELD", target = "Lmekanism/common/inventory/container/QIOItemViewerContainer$ListSortType;ascendingComparator:Ljava/util/Comparator;", opcode = Opcodes.GETFIELD))
    private Comparator<ISlotClickHandler.IScrollableSlot> redirectAscending(QIOItemViewerContainer.ListSortType instance)
    {
        return this.ascendingComparator.thenComparing((stack1, stack2) ->
        {
            if (Screen.hasShiftDown())
            {
                return 0;
            }

            ItemStack leftStack = stack1.getItem().getStack();
            ItemStack rightStack = stack2.getItem().getStack();

            if (!stack1.getModID().equals(stack2.getModID()))
            {
                // some small cleanup. We want to sort only vault items.
                return String.CASE_INSENSITIVE_ORDER.compare(
                    stack1.getModID(),
                    stack2.getModID());
            }
            else
            {
                Configuration.SortBy sortBy = switch (instance) {
                    case NAME -> Configuration.SortBy.NAME;
                    case SIZE -> Configuration.SortBy.AMOUNT;
                    case MOD -> Configuration.SortBy.MOD;
                };
                Integer cmpRv = SortingHelper.compareItems(leftStack, rightStack,stack1.getDisplayName(), stack2.getDisplayName(), sortBy, true);
                return cmpRv == null ? 0 : cmpRv;
            }
        });
    }


    /**
     * This method redirects descendingComparator to a comparator that sorts jewels.
     * @param instance Instance of ListSortType
     * @return new comparator that also handles jewels in descending order
     */
    @Redirect(method = "sort", at = @At(value = "FIELD", target = "Lmekanism/common/inventory/container/QIOItemViewerContainer$ListSortType;descendingComparator:Ljava/util/Comparator;", opcode = Opcodes.GETFIELD))
    private Comparator<ISlotClickHandler.IScrollableSlot> redirectDescending(QIOItemViewerContainer.ListSortType instance)
    {
        return this.descendingComparator.thenComparing((stack1, stack2) ->
        {
            if (Screen.hasShiftDown())
            {
                return 0;
            }

            ItemStack leftStack = stack1.getItem().getStack();
            ItemStack rightStack = stack2.getItem().getStack();

            if (!stack1.getModID().equals(stack2.getModID()))
            {
                // some small cleanup. We want to sort only vault items.
                return String.CASE_INSENSITIVE_ORDER.compare(
                    stack2.getModID(),
                    stack1.getModID());
            }

            else
            {
                Configuration.SortBy sortBy = switch (instance) {
                    case NAME -> Configuration.SortBy.NAME;
                    case SIZE -> Configuration.SortBy.AMOUNT;
                    case MOD -> Configuration.SortBy.MOD;
                };
                Integer cmpRv = SortingHelper.compareItems(leftStack, rightStack, stack1.getDisplayName(), stack2.getDisplayName(), sortBy, false);
                return cmpRv == null ? 0 : cmpRv;
            }
        });
    }

}
