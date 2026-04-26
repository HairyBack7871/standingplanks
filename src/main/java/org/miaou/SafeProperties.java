package org.miaou;

import net.minecraft.resources.DependantName;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;

import java.lang.reflect.Field;
import java.util.Optional;

public final class SafeProperties {

    private static final Field ID_FIELD;
    private static final Field DROPS_FIELD;

    static {
        try {
            ID_FIELD = BlockBehaviour.Properties.class.getDeclaredField("id");
            ID_FIELD.setAccessible(true);

            DROPS_FIELD = BlockBehaviour.Properties.class.getDeclaredField("drops");
            DROPS_FIELD.setAccessible(true);

        } catch (Exception e) {
            throw new RuntimeException("Failed to access Properties fields", e);
        }
    }

    public static BlockBehaviour.Properties copyFrom(Block base) {
        BlockBehaviour.Properties props = BlockBehaviour.Properties.ofFullCopy(base);

        try {
            // 1. Set id to base block's id (fixes constructor crash)
            ResourceKey<Block> key = base.builtInRegistryHolder().key();
            ID_FIELD.set(props, key);

            // 2. Remove loot table entirely (fixes oak/birch drop issues)
            DROPS_FIELD.set(props, (DependantName<Block, Optional<ResourceKey<LootTable>>>) (ignored) -> Optional.empty());

        } catch (Exception e) {
            throw new RuntimeException("Failed to patch Properties", e);
        }

        return props;
    }

    private SafeProperties() {}
}
