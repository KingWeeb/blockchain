package blockchain;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class ChainFactory0 implements GenericChainFactory, Serializable {

    static Chain generateChain(int numberOfBlocks){
        return null;
    }

    abstract void print();

    abstract boolean validate(Chain anyChain);

}
