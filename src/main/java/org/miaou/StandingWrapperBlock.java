package org.miaou;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class StandingWrapperBlock extends Block implements EntityBlock {

    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    // Keep this comfortably small; 0–255 is plenty for “all planks + extras”
    public static final IntegerProperty ORIGINAL_ID = IntegerProperty.create("original_id", 0, 255);

    public StandingWrapperBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(ROTATED, false)
                        .setValue(ORIGINAL_ID, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATED, ORIGINAL_ID);
    }

    public static BlockState withOriginal(BlockState wrapperState, BlockState original, boolean rotated) {
        int idx = StandingOriginals.indexOf(original);

        // Hard guard: if we ever exceed the property range, just don’t encode it
        if (idx > ORIGINAL_ID.getPossibleValues().stream().mapToInt(Integer::intValue).max().orElse(255)) {
            // Fallback: no original, no rotation
            return wrapperState
                    .setValue(ORIGINAL_ID, 0)
                    .setValue(ROTATED, false);
        }

        return wrapperState
                .setValue(ORIGINAL_ID, idx)
                .setValue(ROTATED, rotated);
    }

    @Nullable
    public static BlockState getOriginalState(BlockState wrapperState) {
        int idx = wrapperState.getValue(ORIGINAL_ID);
        return StandingOriginals.byIndex(idx);
    }

    public static boolean isRotated(BlockState wrapperState) {
        return wrapperState.getValue(ROTATED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StandingWrapperBlockEntity(pos, state);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockState original = getOriginalState(state);
        if (original != null && original.getBlock() != Blocks.AIR) {
            return original.getDestroyProgress(player, level, pos);
        }
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos,
                         BlockState newState, boolean moved) {

        if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StandingWrapperBlockEntity wrapper) {
                if (newState.equals(wrapper.getOriginalState())) {
                    super.onRemove(state, level, pos, newState, moved);
                    return;
                }
                if (newState.is(Blocks.MOVING_PISTON)) {
                    super.onRemove(state, level, pos, newState, moved);
                    return;
                }
                wrapper.dropOriginal(level, pos);
            }
        }

        super.onRemove(state, level, pos, newState, moved);
    }
}
