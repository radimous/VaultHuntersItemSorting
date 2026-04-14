package lv.id.bonne.vaulthunters.jewelsorting.utils;

import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.VaultGearState;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.vaulthunters.mixin.InvokerGearDataCache;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CacheHelper {

    /**
     * This method populates jewel cache.
     * @param cache The cache to populate.
     * @param itemStack The item stack to populate cache from.
     */
    public static void populateJewelCache(GearDataCache cache, VaultGearData data)
    {

        List<VaultGearModifier<?>> affixes = new ArrayList<>();
        affixes.addAll(data.getModifiers(VaultGearModifier.AffixType.PREFIX));
        affixes.addAll(data.getModifiers(VaultGearModifier.AffixType.SUFFIX));

        ((InvokerGearDataCache) cache).callQueryIntCache(SortingHelper.EXTRA_ATTRIBUTE_INDEX,
            -1,
            (stack) ->
            {
                if (affixes.size() == 1)
                {
                    return AttributeHelper.getAttributeIndex(affixes.get(0).getAttribute());
                }
                else
                {
                    return -1;
                }
            });

        ((InvokerGearDataCache) cache).callQueryCache(SortingHelper.EXTRA_ATTRIBUTE_VALUE,
            tag -> ((DoubleTag) tag).getAsDouble(),
            DoubleTag::valueOf,
            null,
            Function.identity(),
            (stack) ->
            {
                if (affixes.size() == 1)
                {
                    VaultGearAttribute<?> attribute = affixes.get(0).getAttribute();

                    if (AttributeHelper.isDoubleAttribute(attribute))
                    {
                        Optional<Double> value = (Optional<Double>) data.getFirstValue(attribute);
                        return value.orElse(null);
                    }
                    else if (AttributeHelper.isFloatAttribute(attribute))
                    {
                        Optional<Float> value = (Optional<Float>) data.getFirstValue(attribute);
                        return value.map(Double::valueOf).orElse(null);
                    }
                    else if (AttributeHelper.isIntegerAttribute(attribute))
                    {
                        Optional<Integer> value = (Optional<Integer>) data.getFirstValue(attribute);
                        return value.map(Double::valueOf).orElse(null);
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            });


        ((InvokerGearDataCache) cache).callQueryIntCache(SortingHelper.EXTRA_JEWEL_SIZE, 0, (stack) ->
        {
            if (stack.getItem() instanceof JewelItem)
            {
                return data.getFirstValue(ModGearAttributes.JEWEL_SIZE).orElse(null);
            }
            else
            {
                return null;
            }
        });

        ((InvokerGearDataCache) cache).callQueryIntCache(SortingHelper.EXTRA_GEAR_LEVEL, 0, (stack) ->
            data.getItemLevel());

        ((InvokerGearDataCache) cache).callQueryCache(SortingHelper.EXTRA_CACHE_VERSION,
            Tag::getAsString,
            StringTag::valueOf,
            null,
            Function.identity(),
            stack -> VaultJewelSorting.VAULT_MOD_VERSION);
    }

    // single vgd::read version of createcache
    public static void initCache(ItemStack uninitializedStack){
        GearDataCache cache = GearDataCache.of(uninitializedStack);
        InvokerGearDataCache cacheI = (InvokerGearDataCache) cache;
        VaultGearData data = VaultGearData.read(uninitializedStack);
        cacheI.callQueryEnumCache("state", VaultGearState.class, (stack) -> data.getState().ordinal());
        cacheI.callQueryEnumCache("rarity", VaultGearRarity.class, (stack) -> data.getRarity().ordinal());
        cacheI.callQueryCache("model", Tag::getAsString, StringTag::valueOf, Optional.empty(), (modelStr) -> Optional.of(ResourceLocation.parse(modelStr)), (stack) -> data.getFirstValue(ModGearAttributes.GEAR_MODEL).map(ResourceLocation::toString).orElse(null));
        cacheI.callQueryIntCache("color", 0, (stack) ->  data.getFirstValue(ModGearAttributes.GEAR_COLOR).orElse(null));
        cacheI.callQueryCache("rollType", Tag::getAsString, StringTag::valueOf, null, Function.identity(), (stack) -> data.getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE).orElse(null));
        cacheI.callQueryCache("gearName", Tag::getAsString, StringTag::valueOf, null, Function.identity(), (stack) -> data.getFirstValue(ModGearAttributes.GEAR_NAME).orElse(null));

        cacheI.callQueryCache("colors", (tag) -> ((IntArrayTag)tag).getAsIntArray(), IntArrayTag::new, null, (components) -> Arrays.stream(components).boxed().toList(), (stack) -> {
                List<Integer> components = new ArrayList<>();
                components.add(data.getRarity().getColor().getValue());
                data.getAllAttributes().filter(VaultGearModifier.class::isInstance).map((attrInstance) -> (VaultGearModifier<?>)attrInstance).filter((modifier) -> modifier.hasCategory(
                    VaultGearModifier.AffixCategory.LEGENDARY)).findFirst().ifPresent((modifier) -> components.add(15853364));
                return components.stream().mapToInt(Integer::intValue).toArray();
        });
        cacheI.callQueryCache("jcolors", (tag) -> ((IntArrayTag)tag).getAsIntArray(), IntArrayTag::new, null, (components) -> Arrays.stream(components).boxed().toList(), (stack) -> {
            ArrayList<Integer> components = new ArrayList<>();
            for(VaultGearModifier<?> modifier : data.getAllModifierAffixes()) {
                components.add(modifier.getAttribute().getReader().getRgbColor());
            }
            return components.stream().mapToInt(Integer::intValue).toArray();
        });

        populateJewelCache(cache, data);
    }

}
