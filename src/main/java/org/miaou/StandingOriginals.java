package org.miaou;

import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public final class StandingOriginals {
    private static final List<BlockState> STATES = new ArrayList<>();

    public static int indexOf(BlockState state) {
        int idx = STATES.indexOf(state);
        if (idx >= 0) return idx;
        STATES.add(state);
        return STATES.size() - 1;
    }

    public static BlockState byIndex(int index) {
        if (index < 0 || index >= STATES.size()) {
            return null;
        }
        return STATES.get(index);
    }

    public static int maxIndex() {
        return STATES.size() - 1;
    }
}
