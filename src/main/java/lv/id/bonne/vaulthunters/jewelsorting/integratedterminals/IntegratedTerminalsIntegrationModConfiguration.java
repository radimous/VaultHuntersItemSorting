package lv.id.bonne.vaulthunters.jewelsorting.integratedterminals;

import lv.id.bonne.vaulthunters.jewelsorting.config.MixinConfigPlugin;
import net.minecraftforge.fml.loading.LoadingModList;


public class IntegratedTerminalsIntegrationModConfiguration extends MixinConfigPlugin
{
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        return LoadingModList.get().getModFileById("integratedterminals") != null;
    }
}
