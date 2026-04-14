package lv.id.bonne.vaulthunters.jewelsorting.config;

import lv.id.bonne.vaulthunters.jewelsorting.VaultJewelSorting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = "vault_hunters_jewel_sorting", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigEvent {
    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent event) {
        if (event.getConfig().getModId().equals("vault_hunters_jewel_sorting")) {
            VaultJewelSorting.CONFIGURATION.refreshCache();
        }
    }
}
