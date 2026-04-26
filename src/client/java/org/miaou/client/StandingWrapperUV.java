package org.miaou.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public final class StandingWrapperUV {

    private StandingWrapperUV() {}

    public static BakedQuad rotateQuad90(BakedQuad quad) {
        int[] data = quad.getVertices().clone();
        TextureAtlasSprite sprite = quad.getSprite();

        for (int i = 0; i < 4; i++) {
            int uIndex = i * 8 + 4;
            int vIndex = i * 8 + 5;

            float u = Float.intBitsToFloat(data[uIndex]);
            float v = Float.intBitsToFloat(data[vIndex]);

            // Convert atlas UV → sprite-local UV (0–1)
            float su = sprite.getU0();
            float sv = sprite.getV0();
            float du = sprite.getU1() - su;
            float dv = sprite.getV1() - sv;

            float localU = (u - su) / du;
            float localV = (v - sv) / dv;

            // Rotate 90° clockwise inside sprite
            float rotatedU = 1.0f - localV;
            float rotatedV = localU;

            // Convert back to atlas UV
            float newU = su + rotatedU * du;
            float newV = sv + rotatedV * dv;

            data[uIndex] = Float.floatToRawIntBits(newU);
            data[vIndex] = Float.floatToRawIntBits(newV);
        }

        return new BakedQuad(
                data,
                quad.getTintIndex(),
                quad.getDirection(),
                sprite,
                quad.isShade(),
                quad.getLightEmission()
        );
    }
}
