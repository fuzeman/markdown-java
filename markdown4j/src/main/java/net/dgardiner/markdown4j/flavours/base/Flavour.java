package net.dgardiner.markdown4j.flavours.base;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Flavour {
    private final static Logger LOGGER = Logger.getLogger(Flavour.class.getName());

    private HashMap<String, Block> blocks = new HashMap<>();

    public HashMap<String, Block> getBlocks() {
        return blocks;
    }

    public Block getBlock(String id) {
        return blocks.get(id);
    }

    public boolean register(List<Block> blocks) {
        boolean result = true;

        for(Block block : blocks) {
            if(!register(block)) {
                result = false;
            }
        }

        return result;
    }

    public boolean register(Block block) {
        String id = block.getId();

        if(blocks.containsKey(id)) {
            LOGGER.warning("Block with identifier \"" + id + "\" has already been registered");
            return false;
        }

        blocks.put(id, block);
        return true;
    }
}
