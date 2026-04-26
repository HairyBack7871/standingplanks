package org.miaou;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class StandingPlanks implements ModInitializer {
	public static final String MODID = "standing_planks";

	public static Block STANDING_WRAPPER_BLOCK;
	public static BlockEntityType<StandingWrapperBlockEntity> STANDING_WRAPPER_BE;

	@Override
	public void onInitialize() {

		// 1. Create block AFTER Minecraft is ready
		STANDING_WRAPPER_BLOCK =
				Registry.register(
						BuiltInRegistries.BLOCK,
						id("standing_wrapper"),
						new StandingWrapperBlock(
								SafeProperties.copyFrom(Blocks.OAK_PLANKS)
										.noOcclusion()
						)
				);

		// 2. Register block entity AFTER block exists
		STANDING_WRAPPER_BE =
				Registry.register(
						BuiltInRegistries.BLOCK_ENTITY_TYPE,
						id("standing_wrapper"),
						FabricBlockEntityTypeBuilder
								.create(StandingWrapperBlockEntity::new, STANDING_WRAPPER_BLOCK)
								.build()
				);

		// 3. Register toggler
		UseBlockCallback.EVENT.register(StandingPlankToggler::onUseBlock);
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}
}
