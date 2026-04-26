package org.miaou;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class StandingPlankToggler {

    public static InteractionResult onUseBlock(
            Player player,
            Level level,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        // Server-side only
        if (level.isClientSide) return InteractionResult.PASS;

        // Only main hand
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        // Only stick
        if (player.getItemInHand(hand).getItem() != Items.STICK) return InteractionResult.PASS;

        BlockPos pos = hit.getBlockPos();
        BlockState state = level.getBlockState(pos);

        // -----------------------------------------
        // Case 1: Already a wrapper → restore original
        // -----------------------------------------
        if (state.getBlock() == StandingPlanks.STANDING_WRAPPER_BLOCK) {

            if (level.getBlockEntity(pos) instanceof StandingWrapperBlockEntity wrapper &&
                    wrapper.getOriginalState() != null) {

                level.setBlock(
                        pos,
                        wrapper.getOriginalState(),
                        net.minecraft.world.level.block.Block.UPDATE_ALL |
                                net.minecraft.world.level.block.Block.UPDATE_SUPPRESS_DROPS
                );

                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        }

        // -----------------------------------------
        // Case 2: Only toggle real planks
        // -----------------------------------------
        if (!state.is(BlockTags.PLANKS)) {
            return InteractionResult.PASS;
        }

        // Replace block with wrapper
        level.setBlockAndUpdate(pos, StandingPlanks.STANDING_WRAPPER_BLOCK.defaultBlockState());

        // Store original state in BE
        if (level.getBlockEntity(pos) instanceof StandingWrapperBlockEntity wrapper) {
            wrapper.setOriginalState(state);
            wrapper.setRotated(true);
        }

        return InteractionResult.SUCCESS;
    }
}
