package org.miaou.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.miaou.StandingWrapperBlock;

import java.util.List;

public class StandingWrapperBakedModel implements BakedModel {

    private final BakedModel original;

    public StandingWrapperBakedModel(BakedModel original) {
        this.original = original;
        System.out.println("StandingWrapperBakedModel WRAPPED: " + original);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
        if (state == null || !(state.getBlock() instanceof StandingWrapperBlock)) {
            return original.getQuads(state, side, rand);
        }

        BlockState originalState = StandingWrapperBlock.getOriginalState(state);
        if (originalState == null) {
            return original.getQuads(state, side, rand);
        }

        boolean rotated = StandingWrapperBlock.isRotated(state);

        BakedModel base = Minecraft.getInstance()
                .getBlockRenderer()
                .getBlockModel(originalState);

        List<BakedQuad> quads = base.getQuads(originalState, side, rand);
        if (!rotated) return quads;

        return quads.stream()
                .map(StandingWrapperUV::rotateQuad90)
                .toList();
    }

    @Override public boolean useAmbientOcclusion() { return original.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return original.isGui3d(); }
    @Override public boolean usesBlockLight() { return original.usesBlockLight(); }
    @Override public TextureAtlasSprite getParticleIcon() { return original.getParticleIcon(); }
    @Override public ItemTransforms getTransforms() { return original.getTransforms(); }
}
