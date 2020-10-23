package blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public abstract class BlockFactory0 implements GenericBlockFactory {
    public UUID chainID;
    int numOfZeros;
    String previousHash;

    public BlockFactory0(int numOfZeros, String previousHash, UUID chainID) {
        this.numOfZeros = numOfZeros;
        this.previousHash = previousHash;
        this.chainID = chainID;
    }
    public int getNumOfZeros() {
        return numOfZeros;
    }

    public Block generateProvedBlock() {
        return null;
    }

}
