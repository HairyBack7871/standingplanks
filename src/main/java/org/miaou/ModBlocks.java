package org.miaou;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final Block STANDING_WRAPPER_BLOCK =
            Registry.register(
                    BuiltInRegistries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath("standing-planks", "standing_wrapper"),
                    new StandingWrapperBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    )
            );

    public static void register() {
        // static init
    }
}
