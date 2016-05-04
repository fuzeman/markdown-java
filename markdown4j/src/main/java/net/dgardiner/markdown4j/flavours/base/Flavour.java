package net.dgardiner.markdown4j.flavours.base;

import net.dgardiner.markdown4j.tokens.core.Token;

import java.util.*;
import java.util.logging.Logger;

public class Flavour {
    private final static Logger LOGGER = Logger.getLogger(Flavour.class.getName());

    private HashMap<String, Block> blocks = new HashMap<>();
    private HashMap<String, Token> tokens = new HashMap<>();

    private List<Block> blocksOrdered = new ArrayList<>();
    private List<Token> tokensOrdered = new ArrayList<>();

    //
    // Blocks
    //

    public HashMap<String, Block> getBlocks() {
        return blocks;
    }
    public List<Block> getBlocksOrdered() {
        return blocksOrdered;
    }
    public Block getBlock(String id) {
        return blocks.get(id);
    }

    public boolean register(Block block) {
        String id = block.getId();

        if(blocks.containsKey(id)) {
            LOGGER.warning("Block with identifier \"" + id + "\" has already been registered");
            return false;
        }

        // Update `blocks` map
        blocks.put(id, block);

        // Update `blockPriority` list
        blocksOrdered.add(block);
        Collections.sort(blocksOrdered);
        return true;
    }

    //
    // Tokens
    //

    public HashMap<String, Token> getTokens() {
        return tokens;
    }
    public List<Token> getTokensOrdered() {
        return tokensOrdered;
    }
    public Token getToken(String id) {
        return tokens.get(id);
    }

    public boolean register(Token token) {
        String id = token.getId();

        if(blocks.containsKey(id)) {
            LOGGER.warning("Token with identifier \"" + id + "\" has already been registered");
            return false;
        }

        // Update `blocks` map
        tokens.put(id, token);

        // Update `tokensOrdered` list
        tokensOrdered.add(token);
        Collections.sort(tokensOrdered);
        return true;
    }
}
