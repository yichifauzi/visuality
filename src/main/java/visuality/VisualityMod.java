package visuality;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import visuality.config.VisualityConfig;
import visuality.config.VisualityClothConfig;
import visuality.registry.*;

public class VisualityMod implements ClientModInitializer {
	public static final String MOD_ID = "visuality";
	public static VisualityConfig config = VisualityClothConfig.init();

	@Override
	public void onInitializeClient() {
		VisualityParticles.init();
		VisualityEvents.init();
		HitParticleRegistry.reload();
		ShinyArmorRegistry.reload();
		ShinyBlockRegistry.reload();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
