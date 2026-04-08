//
// Created by BONNe
// Copyright - 2023
//


package lv.id.bonne.vaulthunters.jewelsorting.config;


import com.google.common.base.Enums;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            comment("Supported Values: NAME, ATTRIBUTE, ATTRIBUTE_VALUE, SIZE, LEVEL, ATTRIBUTE_WEIGHT, CUTS").
            defineList("jewel_sorting_by_name",
                Arrays.asList(SortingHelper.JewelOptions.NAME.name(),
                    SortingHelper.JewelOptions.ATTRIBUTE.name(),
                    SortingHelper.JewelOptions.ATTRIBUTE_VALUE.name(),
                    SortingHelper.JewelOptions.SIZE.name(),
                    SortingHelper.JewelOptions.LEVEL.name(),
                    SortingHelper.JewelOptions.CUTS.name()),
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
            comment("Supported Values: NAME, STATE, RARITY, LEVEL, MODEL").
            defineList("gear_sorting_by_name",
                Arrays.asList(SortingHelper.GearOptions.NAME.name(),
                    SortingHelper.GearOptions.STATE.name(),
                    SortingHelper.GearOptions.RARITY.name(),
                    SortingHelper.GearOptions.LEVEL.name(),
                    SortingHelper.GearOptions.MODEL.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.GearOptions.class, value.toUpperCase()).isPresent());

        this.gearSortingByAmount = builder.
            comment("The order of Gear if they are sorted by the amount/size.").
            comment("Supported Values: NAME, STATE, RARITY, LEVEL, MODEL").
            defineList("gear_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.GearOptions.class, value.toUpperCase()).isPresent());

        this.gearSortingByMod = builder.
            comment("The order of Gear if they are sorted by the mod.").
            comment("Supported Values: NAME, STATE, RARITY, LEVEL, MODEL").
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

        builder.comment("This category holds options how Vault Charms are sorted");
        builder.push("Vault Charm Sorting");

        this.charmSortingByName = builder.
            comment("The order of charms if they are sorted by the name.").
            comment("Supported Values: NAME, USES, VALUE").
            defineList("charm_sorting_by_name",
                Arrays.asList(SortingHelper.CharmOptions.NAME.name(),
                    SortingHelper.CharmOptions.USES.name(),
                    SortingHelper.CharmOptions.VALUE.name()),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CharmOptions.class, value.toUpperCase()).isPresent());

        this.charmSortingByAmount = builder.
            comment("The order of charms if they are sorted by the amount/size.").
            comment("Supported Values: NAME, USES, VALUE").
            defineList("charm_sorting_by_amount",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CharmOptions.class, value.toUpperCase()).isPresent());

        this.charmSortingByMod = builder.
            comment("The order of charms if they are sorted by the mod.").
            comment("Supported Values: NAME, USES, VALUE").
            defineList("charm_sorting_by_mod",
                Collections.emptyList(),
                entry -> entry instanceof String value &&
                    Enums.getIfPresent(SortingHelper.CharmOptions.class, value.toUpperCase()).isPresent());

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
            case NAME -> this.convertStringToJewelEnum(this.jewelSortingByName.get());
            case AMOUNT -> this.convertStringToJewelEnum(this.jewelSortingByAmount.get());
            case MOD -> this.convertStringToJewelEnum(this.jewelSortingByMod.get());
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
            case NAME -> this.convertStringToGearEnum(this.gearSortingByName.get());
            case AMOUNT -> this.convertStringToGearEnum(this.gearSortingByAmount.get());
            case MOD -> this.convertStringToGearEnum(this.gearSortingByMod.get());
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
            case NAME -> this.convertStringToInscriptionEnum(this.inscriptionSortingByName.get());
            case AMOUNT -> this.convertStringToInscriptionEnum(this.inscriptionSortingByAmount.get());
            case MOD -> this.convertStringToInscriptionEnum(this.inscriptionSortingByMod.get());
        };
    }

    public List<SortingHelper.CrystalOptions> getVaultCrystalSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.convertStringToCrystalEnum(this.vaultCrystalSortingByName.get());
            case AMOUNT -> this.convertStringToCrystalEnum(this.vaultCrystalSortingByAmount.get());
            case MOD -> this.convertStringToCrystalEnum(this.vaultCrystalSortingByMod.get());
        };
    }

    public List<SortingHelper.TrinketOptions> getTrinketSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.convertStringToTrinketEnum(this.trinketSortingByName.get());
            case AMOUNT -> this.convertStringToTrinketEnum(this.trinketSortingByAmount.get());
            case MOD -> this.convertStringToTrinketEnum(this.trinketSortingByMod.get());
        };
    }

    public List<SortingHelper.DollOptions> getDollSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.convertStringToDollEnum(this.dollSortingByName.get());
            case AMOUNT -> this.convertStringToDollEnum(this.dollSortingByAmount.get());
            case MOD -> this.convertStringToDollEnum(this.dollSortingByMod.get());
        };
    }

    public List<SortingHelper.CharmOptions> getCharmSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.convertStringToCharmEnum(this.charmSortingByName.get());
            case AMOUNT -> this.convertStringToCharmEnum(this.charmSortingByAmount.get());
            case MOD -> this.convertStringToCharmEnum(this.charmSortingByMod.get());
        };
    }

    public List<SortingHelper.CatalystOptions> getCatalystSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.convertStringToCatalystEnum(this.catalystSortingByName.get());
            case AMOUNT -> this.convertStringToCatalystEnum(this.catalystSortingByAmount.get());
            case MOD -> this.convertStringToCatalystEnum(this.catalystSortingByMod.get());
        };
    }

    public List<SortingHelper.CardOptions> getCardSortingOptions(SortBy sortBy)
    {
        return switch (sortBy)
        {
            case NAME -> this.convertStringToCardEnum(this.cardSortingByName.get());
            case AMOUNT -> this.convertStringToCardEnum(this.cardSortingByAmount.get());
            case MOD -> this.convertStringToCardEnum(this.cardSortingByMod.get());
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
    private List<SortingHelper.CharmOptions> convertStringToCharmEnum(List<? extends String> value)
    {
        return value.stream().
            map(String::toUpperCase).
            filter(upperCase -> Enums.getIfPresent(SortingHelper.CharmOptions.class, upperCase).isPresent()).
            map(SortingHelper.CharmOptions::valueOf).
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
     * The config value for vault charm sorting by name.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> charmSortingByName;

    /**
     * The config value for vault charm sorting by amount.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> charmSortingByAmount;

    /**
     * The config value for vault charm sorting by mod.
     */
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> charmSortingByMod;

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
