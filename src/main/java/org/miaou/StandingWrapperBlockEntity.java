package org.miaou;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StandingWrapperBlockEntity extends BlockEntity {

    private BlockState originalState;
    private boolean rotated;

    public StandingWrapperBlockEntity(BlockPos pos, BlockState state) {
        super(StandingPlanks.STANDING_WRAPPER_BE, pos, state);
    }

    public void setOriginalState(BlockState original) {
        this.originalState = original;
        syncToBlockState();
    }

    public BlockState getOriginalState() {
        return originalState;
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
        syncToBlockState();
    }

    public boolean isRotated() {
        return rotated;
    }

    private void syncToBlockState() {
        if (level == null) return;
        BlockState state = getBlockState();
        BlockState updated = StandingWrapperBlock.withOriginal(state, originalState, rotated);
        level.setBlock(worldPosition, updated, 3);
    }

    public void dropOriginal(Level level, BlockPos pos) {
        if (originalState != null) {
            Block.dropResources(originalState, level, pos);
        }
    }
}
