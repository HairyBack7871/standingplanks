package org.miaou.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class StandingPlanksClient implements ClientModInitializer {

	private static final ModelResourceLocation WRAPPER_MODEL_ID =
			new ModelResourceLocation(
					ResourceLocation.fromNamespaceAndPath("standing_planks", "block/standing_wrapper"),
					""
			);

	@Override
	public void onInitializeClient() {

		ModelLoadingPlugin.register(plugin -> {

			plugin.modifyModelAfterBake().register(
					ModelModifier.WRAP_PHASE,
					(model, ctx) -> {


						if (ctx.id().equals(ResourceLocation.fromNamespaceAndPath(
								"standing_planks", "block/standing_wrapper"))) {

									return new StandingWrapperBakedModel(model);
						}

						return model;
					}
			);
		});

	}
}
