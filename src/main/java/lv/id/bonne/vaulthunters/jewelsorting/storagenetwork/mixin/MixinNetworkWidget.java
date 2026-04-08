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

            Integer cmpRv = SortingHelper.compareItems(
                leftStack,
                rightStack,
                sortBy,
                this.gui.getDownwards());
            if (cmpRv != null) {
                return cmpRv;
            }

            return 0;
        });
    }
}
