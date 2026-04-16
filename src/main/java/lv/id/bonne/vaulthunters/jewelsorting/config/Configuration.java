//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.config;


import com.google.common.base.Enums;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import lv.id.bonne.vaulthunters.jewelsorting.utils.SortingHelper;
import net.minecraftforge.common.ForgeConfigSpec;


/**
 * The configuration handling class. Holds all the config values.
 */
public class Configuration
{
    /**
     * The constructor for the config.
     */
    public Configuration()
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("This category holds options how Jewels are sorted");
        builder.push("Jewel Sorting");

        this.jewelSortingByName = builder.
            comment("The order of Jewels if they are sorted by the name.").
            comment("Supported Values: NAME, ATTRIBUTE, ATTRIBUTE_VALUE, SIZE, LEVEL, ATTRIBUTE_WEIGHT, AFFIX_CATEGORY").
            defineList("jewel_sorting_by_name",
                Arrays.asList(SortingHelper.JewelOptions.NAME.name(),
                    SortingHelper.JewelOptions.ATTRIBUTE.name(),
                    SortingHelper.JewelOptions.ATTRIBUTE_VALUE.name(),
                    SortingHelper.JewelOptions.SIZE.name(),
                    SortingHelper.JewelOptions.AFFIX_CATEGORY.name(),
                    SortingHelper.JewelOptions.LEVEL.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.JewelOptions.class, value.toUpperCase()).isPresent());

        this.jewelSortingByAmount = builder.
            comment("The order of Jewels if they are sorted by the amount/size.").
            comment("Supported Values: NAME, ATTRIBUTE, ATTRIBUTE_VALUE, SIZE, LEVEL, ATTRIBUTE_WEIGHT, CUTS").
            defineList("jewel_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.JewelOptions.class, value.toUpperCase()).isPresent());

        this.jewelSortingByMod = builder.
            comment("The order of Jewels if they are sorted by the mod.").
            comment("Supported Values: NAME, ATTRIBUTE, ATTRIBUTE_VALUE, SIZE, LEVEL, ATTRIBUTE_WEIGHT, CUTS").
            defineList("jewel_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.JewelOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        builder.comment("This category holds options how Gear are sorted");
        builder.push("Gear Sorting");

        this.gearSortingByName = builder.
            comment("The order of Gear if they are sorted by the name.").
            comment("Supported Values: NAME, STATE, RARITY, LEVEL, AFFIX_CATEGORY, MODEL").
            defineList("gear_sorting_by_name",
                Arrays.asList(SortingHelper.GearOptions.NAME.name(),
                    SortingHelper.GearOptions.STATE.name(),
                    SortingHelper.GearOptions.RARITY.name(),
                    SortingHelper.GearOptions.LEVEL.name(),
                    SortingHelper.GearOptions.AFFIX_CATEGORY.name(),
                    SortingHelper.GearOptions.MODEL.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.GearOptions.class, value.toUpperCase()).isPresent());

        this.gearSortingByAmount = builder.
            comment("The order of Gear if they are sorted by the amount/size.").
            comment("Supported Values: NAME, STATE, RARITY, LEVEL, AFFIX_CATEGORY, MODEL").
            defineList("gear_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.GearOptions.class, value.toUpperCase()).isPresent());

        this.gearSortingByMod = builder.
            comment("The order of Gear if they are sorted by the mod.").
            comment("Supported Values: NAME, STATE, RARITY, LEVEL, AFFIX_CATEGORY, MODEL").
            defineList("gear_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.GearOptions.class, value.toUpperCase()).isPresent());

        this.rarityOrder = builder.
            comment("The order of Rarities Pools in the sorting for unidentified gear.").
            defineList("rarity_order",
                Arrays.asList("Scrappy",
                    "Scrappy+",
                    "Common",
                    "Common+",
                    "Rare",
                    "Rare+",
                    "Epic",
                    "Epic+",
                    "Omega",
                    "Beginner",
                    "Novice",
                    "Maker",
                    "Apprentice",
                    "Smith",
                    "Artisan",
                    "Master Artisan",
                    "Gladiator"),
                entry -> true);

        builder.pop();

        builder.comment("This category holds options how Inscriptions are sorted");
        builder.push("Inscription Sorting");

        this.inscriptionSortingByName = builder.
            comment("The order of Inscriptions if they are sorted by the name.").
            comment("Supported Values: NAME, INSTABILITY, TIME, COMPLETION, ROOMS").
            defineList("inscription_sorting_by_name",
                Arrays.asList(SortingHelper.InscriptionOptions.NAME.name(),
                    SortingHelper.InscriptionOptions.ROOMS.name(),
                    SortingHelper.InscriptionOptions.TIME.name(),
                    SortingHelper.InscriptionOptions.COMPLETION.name(),
                    SortingHelper.InscriptionOptions.INSTABILITY.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.InscriptionOptions.class, value.toUpperCase()).isPresent());

        this.inscriptionSortingByAmount = builder.
            comment("The order of Inscriptions if they are sorted by the amount/size.").
            comment("Supported Values: NAME, INSTABILITY, TIME, COMPLETION, ROOMS").
            defineList("inscription_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.InscriptionOptions.class, value.toUpperCase()).isPresent());

        this.inscriptionSortingByMod = builder.
            comment("The order of Inscriptions if they are sorted by the mod.").
            comment("Supported Values: NAME, INSTABILITY, TIME, COMPLETION, ROOMS").
            defineList("inscription_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.InscriptionOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        builder.comment("This category holds options how Vault Crystals are sorted");
        builder.push("Vault Crystal Sorting");

        this.vaultCrystalSortingByName = builder.
            comment("The order of Vault Crystal if they are sorted by the name.").
            comment("Supported Values: NAME, LEVEL, TYPE").
            defineList("crystal_sorting_by_name",
                Arrays.asList(SortingHelper.CrystalOptions.NAME.name(),
                    SortingHelper.CrystalOptions.TYPE.name(),
                    SortingHelper.CrystalOptions.LEVEL.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CrystalOptions.class, value.toUpperCase()).isPresent());

        this.vaultCrystalSortingByAmount = builder.
            comment("The order of Vault Crystal if they are sorted by the amount/size.").
            comment("Supported Values: NAME, LEVEL, TYPE").
            defineList("crystal_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CrystalOptions.class, value.toUpperCase()).isPresent());

        this.vaultCrystalSortingByMod = builder.
            comment("The order of Vault Crystal if they are sorted by the mod.").
            comment("Supported Values: NAME, LEVEL, TYPE").
            defineList("crystal_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CrystalOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        builder.comment("This category holds options how Trinkets are sorted");
        builder.push("Trinkets Sorting");

        this.trinketSortingByName = builder.
            comment("The order of Trinkets if they are sorted by the name.").
            comment("Supported Values: NAME, SLOT, TYPE, USES").
            defineList("trinket_sorting_by_name",
                Arrays.asList(SortingHelper.TrinketOptions.NAME.name(),
                    SortingHelper.TrinketOptions.SLOT.name(),
                    SortingHelper.TrinketOptions.TYPE.name(),
                    SortingHelper.TrinketOptions.USES.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.TrinketOptions.class, value.toUpperCase()).isPresent());

        this.trinketSortingByAmount = builder.
            comment("The order of Trinkets if they are sorted by the amount/size.").
            comment("Supported Values: NAME, SLOT, TYPE, USES").
            defineList("trinket_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.TrinketOptions.class, value.toUpperCase()).isPresent());

        this.trinketSortingByMod = builder.
            comment("The order of Trinkets if they are sorted by the mod.").
            comment("Supported Values: NAME, SLOT, TYPE, USES").
            defineList("trinket_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.TrinketOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        builder.comment("This category holds options how Vault Dolls are sorted");
        builder.push("Vault Doll Sorting");

        this.dollSortingByName = builder.
            comment("The order of dolls if they are sorted by the name.").
            comment("Supported Values: NAME, OWNER, COMPLETED, XP, LOOT").
            defineList("vault_doll_sorting_by_name",
                Arrays.asList(SortingHelper.DollOptions.NAME.name(),
                    SortingHelper.DollOptions.COMPLETED.name(),
                    SortingHelper.DollOptions.OWNER.name(),
                    SortingHelper.DollOptions.LOOT.name(),
                    SortingHelper.DollOptions.XP.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.DollOptions.class, value.toUpperCase()).isPresent());

        this.dollSortingByAmount = builder.
            comment("The order of dolls if they are sorted by the amount/size.").
            comment("Supported Values: NAME, OWNER, COMPLETED, XP, LOOT").
            defineList("vault_doll_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.DollOptions.class, value.toUpperCase()).isPresent());

        this.dollSortingByMod = builder.
            comment("The order of dolls if they are sorted by the mod.").
            comment("Supported Values: NAME, OWNER, COMPLETED, XP, LOOT").
            defineList("vault_doll_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.DollOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        builder.comment("This category holds options how Infused Catalysts are sorted");
        builder.push("Infused Catalysts Sorting");

        this.catalystSortingByName = builder.
            comment("The order of Infused Catalysts if they are sorted by the name.").
            comment("Supported Values: NAME, SIZE, MODIFIER").
            defineList("catalyst_sorting_by_name",
                Arrays.asList(SortingHelper.CatalystOptions.NAME.name(),
                    SortingHelper.CatalystOptions.MODIFIER.name(),
                    SortingHelper.CatalystOptions.SIZE.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CatalystOptions.class, value.toUpperCase()).isPresent());

        this.catalystSortingByAmount = builder.
            comment("The order of Infused Catalysts if they are sorted by the amount/size.").
            comment("Supported Values: NAME, SIZE, MODIFIER").
            defineList("catalyst_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CatalystOptions.class, value.toUpperCase()).isPresent());

        this.catalystSortingByMod = builder.
            comment("The order of Infused Catalysts if they are sorted by the mod.").
            comment("Supported Values: NAME, SIZE, MODIFIER").
            defineList("catalyst_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CatalystOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        builder.comment("This category holds options how Cards are sorted");
        builder.push("Cards Sorting");

        this.cardSortingByName = builder.
            comment("The order of Cards if they are sorted by the name.").
            comment("Supported Values: NAME, TIER, TYPE, COLOR, GROUPS, MODEL").
            defineList("card_sorting_by_name",
                Arrays.asList(
                    SortingHelper.CardOptions.TYPE.name(),
                    SortingHelper.CardOptions.COLOR.name(),
                    SortingHelper.CardOptions.TIER.name(),
                    SortingHelper.CardOptions.NAME.name(),
                    SortingHelper.CardOptions.GROUPS.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CardOptions.class, value.toUpperCase()).isPresent());

        this.cardSortingByAmount = builder.
            comment("The order of Cards if they are sorted by the amount/size.").
            comment("Supported Values: NAME, TIER, TYPE, COLOR, GROUPS, MODEL").
            defineList("card_sorting_by_amount",
                Arrays.asList(
                    SortingHelper.CardOptions.TYPE.name(),
                    SortingHelper.CardOptions.COLOR.name(),
                    SortingHelper.CardOptions.TIER.name(),
                    SortingHelper.CardOptions.NAME.name(),
                    SortingHelper.CardOptions.GROUPS.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CardOptions.class, value.toUpperCase()).isPresent());

        this.cardSortingByMod = builder.
            comment("The order of Cards if they are sorted by the mod.").
            comment("Supported Values: NAME, TIER, TYPE, COLOR, GROUPS, MODEL").
            defineList("card_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CardOptions.class, value.toUpperCase()).isPresent());

        builder.pop();

        Configuration.GENERAL_SPEC = builder.build();
        this.refreshCache();
    }

    public enum SortBy
    {
        NAME,
        AMOUNT,
        MOD
    }

    /**
     * Gets jewel sorting by the specified criteria.
     *
     * @param sortBy the criteria to sort by
     * @return the jewel sorting by the specified criteria
     */
    public List<SortingHelper.JewelOptions> getJewelSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.jewelSortingByNameCache;
            case AMOUNT -> this.jewelSortingByAmountCache;
            case MOD -> this.jewelSortingByModCache;
        };
    }

    /**
     * Gets gear sorting by the specified criteria.
     *
     * @param sortBy the criteria to sort by
     * @return the gear sorting by the specified criteria
     */
    public List<SortingHelper.GearOptions> getGearSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.gearSortingByNameCache;
            case AMOUNT -> this.gearSortingByAmountCache;
            case MOD -> this.gearSortingByModCache;
        };
    }


    /**
     * Gets rarity order.
     *
     * @return the rarity order
     */
    public List<? extends String> getRarityOrder()
    {
        return this.rarityOrder.get();
    }


    public List<SortingHelper.InscriptionOptions> getInscriptionSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.inscriptionSortingByNameCache;
            case AMOUNT -> this.inscriptionSortingByAmountCache;
            case MOD -> this.inscriptionSortingByModCache;
        };
    }

    public List<SortingHelper.CrystalOptions> getVaultCrystalSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.vaultCrystalSortingByNameCache;
            case AMOUNT -> this.vaultCrystalSortingByAmountCache;
            case MOD -> this.vaultCrystalSortingByModCache;
        };
    }

    public List<SortingHelper.TrinketOptions> getTrinketSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.trinketSortingByNameCache;
            case AMOUNT -> this.trinketSortingByAmountCache;
            case MOD -> this.trinketSortingByModCache;
        };
    }

    public List<SortingHelper.DollOptions> getDollSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.dollSortingByNameCache;
            case AMOUNT -> this.dollSortingByAmountCache;
            case MOD -> this.dollSortingByModCache;
        };
    }

    public List<SortingHelper.CatalystOptions> getCatalystSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.catalystSortingByNameCache;
            case AMOUNT -> this.catalystSortingByAmountCache;
            case MOD -> this.catalystSortingByModCache;
        };
    }

    public List<SortingHelper.CardOptions> getCardSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.cardSortingByNameCache;
            case AMOUNT -> this.cardSortingByAmountCache;
            case MOD -> this.cardSortingByModCache;
        };
    }

    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.JewelOptions> convertStringToJewelEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.JewelOptions.class, upperCase).isPresent()).
            map(SortingHelper.JewelOptions::valueOf).
            distinct().
            toList();
    }


    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.GearOptions> convertStringToGearEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.GearOptions.class, upperCase).isPresent()).
            map(SortingHelper.GearOptions::valueOf).
            distinct().
            toList();
    }


    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.InscriptionOptions> convertStringToInscriptionEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.InscriptionOptions.class, upperCase).isPresent()).
            map(SortingHelper.InscriptionOptions::valueOf).
            distinct().
            toList();
    }


    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.CrystalOptions> convertStringToCrystalEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.CrystalOptions.class, upperCase).isPresent()).
            map(SortingHelper.CrystalOptions::valueOf).
            distinct().
            toList();
    }


    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.TrinketOptions> convertStringToTrinketEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.TrinketOptions.class, upperCase).isPresent()).
            map(SortingHelper.TrinketOptions::valueOf).
            distinct().
            toList();
    }


    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.DollOptions> convertStringToDollEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.DollOptions.class, upperCase).isPresent()).
            map(SortingHelper.DollOptions::valueOf).
            distinct().
            toList();
    }



    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.CatalystOptions> convertStringToCatalystEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.CatalystOptions.class, upperCase).isPresent()).
            map(SortingHelper.CatalystOptions::valueOf).
            distinct().
            toList();
    }


    /**
     * This method converts String list to Enum list.
     * @param value The string list that need to be converted.
     * @return Converted Enum list.
     */
    private List<SortingHelper.CardOptions> convertStringToCardEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.CardOptions.class, upperCase).isPresent()).
            map(SortingHelper.CardOptions::valueOf).
            distinct().
            toList();
    }


    private List<SortingHelper.JewelOptions> jewelSortingByNameCache;
    private List<SortingHelper.JewelOptions> jewelSortingByAmountCache;
    private List<SortingHelper.JewelOptions> jewelSortingByModCache;

    private List<SortingHelper.GearOptions> gearSortingByNameCache;
    private List<SortingHelper.GearOptions> gearSortingByAmountCache;
    private List<SortingHelper.GearOptions> gearSortingByModCache;

    private List<SortingHelper.InscriptionOptions> inscriptionSortingByNameCache;
    private List<SortingHelper.InscriptionOptions> inscriptionSortingByAmountCache;
    private List<SortingHelper.InscriptionOptions> inscriptionSortingByModCache;

    private List<SortingHelper.CrystalOptions> vaultCrystalSortingByNameCache;
    private List<SortingHelper.CrystalOptions> vaultCrystalSortingByAmountCache;
    private List<SortingHelper.CrystalOptions> vaultCrystalSortingByModCache;

    private List<SortingHelper.TrinketOptions> trinketSortingByNameCache;
    private List<SortingHelper.TrinketOptions> trinketSortingByAmountCache;
    private List<SortingHelper.TrinketOptions> trinketSortingByModCache;

    private List<SortingHelper.DollOptions> dollSortingByNameCache;
    private List<SortingHelper.DollOptions> dollSortingByAmountCache;
    private List<SortingHelper.DollOptions> dollSortingByModCache;

    private List<SortingHelper.CatalystOptions> catalystSortingByNameCache;
    private List<SortingHelper.CatalystOptions> catalystSortingByAmountCache;
    private List<SortingHelper.CatalystOptions> catalystSortingByModCache;

    private List<SortingHelper.CardOptions> cardSortingByNameCache;
    private List<SortingHelper.CardOptions> cardSortingByAmountCache;
    private List<SortingHelper.CardOptions> cardSortingByModCache;

    public void refreshCache()
    {
        this.jewelSortingByNameCache = convertStringToJewelEnum(jewelSortingByName.get());
        this.jewelSortingByAmountCache = convertStringToJewelEnum(jewelSortingByAmount.get());
        if (jewelSortingByAmountCache.isEmpty()) {jewelSortingByAmountCache = List.copyOf(jewelSortingByNameCache);}
        this.jewelSortingByModCache = convertStringToJewelEnum(jewelSortingByMod.get());
        if (jewelSortingByModCache.isEmpty()) {jewelSortingByModCache = List.copyOf(jewelSortingByNameCache);}

        this.gearSortingByNameCache = convertStringToGearEnum(gearSortingByName.get());
        this.gearSortingByAmountCache = convertStringToGearEnum(gearSortingByAmount.get());
        if (gearSortingByAmountCache.isEmpty()) {gearSortingByAmountCache = List.copyOf(gearSortingByNameCache);}
        this.gearSortingByModCache = convertStringToGearEnum(gearSortingByMod.get());
        if (gearSortingByModCache.isEmpty()) {gearSortingByModCache = List.copyOf(gearSortingByNameCache);}

        this.inscriptionSortingByNameCache = convertStringToInscriptionEnum(inscriptionSortingByName.get());
        this.inscriptionSortingByAmountCache = convertStringToInscriptionEnum(inscriptionSortingByAmount.get());
        if (inscriptionSortingByAmountCache.isEmpty()) {inscriptionSortingByAmountCache = List.copyOf(inscriptionSortingByNameCache);}
        this.inscriptionSortingByModCache = convertStringToInscriptionEnum(inscriptionSortingByMod.get());
        if (inscriptionSortingByModCache.isEmpty()) {inscriptionSortingByModCache = List.copyOf(inscriptionSortingByNameCache);}

        this.vaultCrystalSortingByNameCache = convertStringToCrystalEnum(vaultCrystalSortingByName.get());
        this.vaultCrystalSortingByAmountCache = convertStringToCrystalEnum(vaultCrystalSortingByAmount.get());
        if (vaultCrystalSortingByAmountCache.isEmpty()) {vaultCrystalSortingByAmountCache = List.copyOf(vaultCrystalSortingByNameCache);}
        this.vaultCrystalSortingByModCache = convertStringToCrystalEnum(vaultCrystalSortingByMod.get());
        if (vaultCrystalSortingByModCache.isEmpty()) {vaultCrystalSortingByModCache = List.copyOf(vaultCrystalSortingByNameCache);}

        this.trinketSortingByNameCache = convertStringToTrinketEnum(trinketSortingByName.get());
        this.trinketSortingByAmountCache = convertStringToTrinketEnum(trinketSortingByAmount.get());
        if (trinketSortingByAmountCache.isEmpty()) {trinketSortingByAmountCache = List.copyOf(trinketSortingByNameCache);}
        this.trinketSortingByModCache = convertStringToTrinketEnum(trinketSortingByMod.get());
        if (trinketSortingByModCache.isEmpty()) {trinketSortingByModCache = List.copyOf(trinketSortingByNameCache);}

        this.dollSortingByNameCache = convertStringToDollEnum(dollSortingByName.get());
        this.dollSortingByAmountCache = convertStringToDollEnum(dollSortingByAmount.get());
        if (dollSortingByAmountCache.isEmpty()) {dollSortingByAmountCache = List.copyOf(dollSortingByNameCache);}
        this.dollSortingByModCache = convertStringToDollEnum(dollSortingByMod.get());
        if (dollSortingByModCache.isEmpty()) {dollSortingByModCache = List.copyOf(dollSortingByNameCache);}

        this.catalystSortingByNameCache = convertStringToCatalystEnum(catalystSortingByName.get());
        this.catalystSortingByAmountCache = convertStringToCatalystEnum(catalystSortingByAmount.get());
        if (catalystSortingByAmountCache.isEmpty()) {catalystSortingByAmountCache = List.copyOf(catalystSortingByNameCache);}
        this.catalystSortingByModCache = convertStringToCatalystEnum(catalystSortingByMod.get());
        if (catalystSortingByModCache.isEmpty()) {catalystSortingByModCache = List.copyOf(catalystSortingByNameCache);}

        this.cardSortingByNameCache = convertStringToCardEnum(cardSortingByName.get());
        this.cardSortingByAmountCache = convertStringToCardEnum(cardSortingByAmount.get());
        if (cardSortingByAmountCache.isEmpty()) {cardSortingByAmountCache = List.copyOf(cardSortingByNameCache);}
        this.cardSortingByModCache = convertStringToCardEnum(cardSortingByMod.get());
        if (cardSortingByModCache.isEmpty()) {cardSortingByModCache = List.copyOf(cardSortingByNameCache);}

        VaultJewelSorting.LOGGER.info("Config cache refreshed");
    }

// ---------------------------------------------------------------------
// Section: Variables
// ---------------------------------------------------------------------


    /**
     * The config value for jewel sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> jewelSortingByName;

    /**
     * The config value for jewel sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> jewelSortingByAmount;

    /**
     * The config value for jewel sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> jewelSortingByMod;

    /**
     * The config value for gear sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> gearSortingByName;

    /**
     * The config value for gear sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> gearSortingByAmount;

    /**
     * The config value for gear sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> gearSortingByMod;

    /**
     * The config value for rarity order.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> rarityOrder;

    /**
     * The config value for inscription sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> inscriptionSortingByName;

    /**
     * The config value for inscription sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> inscriptionSortingByAmount;

    /**
     * The config value for inscription sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> inscriptionSortingByMod;

    /**
     * The config value for vault crystal sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> vaultCrystalSortingByName;

    /**
     * The config value for vault crystal sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> vaultCrystalSortingByAmount;

    /**
     * The config value for vault crystal sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> vaultCrystalSortingByMod;

    /**
     * The config value for trinket sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> trinketSortingByName;

    /**
     * The config value for trinket sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> trinketSortingByAmount;

    /**
     * The config value for trinket sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> trinketSortingByMod;

    /**
     * The config value for vault doll sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> dollSortingByName;

    /**
     * The config value for vault doll sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> dollSortingByAmount;

    /**
     * The config value for vault doll sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> dollSortingByMod;

    /**
     * The config value for infused catalyst sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> catalystSortingByName;

    /**
     * The config value for infused catalyst sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> catalystSortingByAmount;

    /**
     * The config value for infused catalyst sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> catalystSortingByMod;

    /**
     * The config value for infused catalyst sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> cardSortingByName;

    /**
     * The config value for infused catalyst sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> cardSortingByAmount;

    /**
     * The config value for infused catalyst sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> cardSortingByMod;

    /**
     * The general config spec.
     */
    public static ForgeConfigSpec GENERAL_SPEC;
}
