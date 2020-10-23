package blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Chain extends ChainFactory0 implements Serializable {
    public static volatile int counter = 0;
    public ArrayList<Block> blockChain = new ArrayList<>();
    private UUID ChainID = UUID.randomUUID();
    public static HashMap<UUID, Chain>ChainStorage = new HashMap<>();
    public volatile String previousHashForNextBlockGeneration = "0";
    public volatile int numOfZeros = 0;
    public volatile boolean achieved = false;
    public ChatHistory textHistoryPerBlock = new ChatHistory();

    public Chain() {

        Block head = new Block(0, this.previousHashForNextBlockGeneration, this.getChainID());
        Block proved = head.generateProvedBlock();
        Block.counter++;
        proved.ID = Block.counter++;

        this.blockChain.add(proved);
        this.previousHashForNextBlockGeneration = proved.hash;
        this.numOfZeros++;
        proved.N = this.numOfZeros;
        proved.NString = "N was increased to " + Integer.toString(this.numOfZeros);
        proved.currentThread = Thread.currentThread().getName();
        proved.textHistoryPerBlock = this.textHistoryPerBlock;
    }

    public synchronized void addTOChain(Block proved) { //locks on each chain instance as there could be multiple
        // chains being mined in parallel

        this.blockChain.add(proved);
        proved.ID = Block.counter++;
        previousHashForNextBlockGeneration = proved.hash;
        this.achieved = true;
        if (proved.secTime <= 5) {
            this.numOfZeros += 1;
            proved.N = this.numOfZeros;
            proved.NString = "N was increased to " + Integer.toString(this.numOfZeros);

        } else if (proved.secTime >= 60) {
            this.numOfZeros -= 1;
            proved.N = this.numOfZeros;
            proved.NString = "N was decreased to " + Integer.toString(this.numOfZeros);
        } else {
            proved.N = this.numOfZeros;
            proved.NString = "N stays the same";
        }
        String filename = ChainID.toString() + ".block_chain";
//        try{
//            SerializationUtils.serialize(proved, filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    @Override
    void print() {
        for (int i = 0; i < blockChain.size(); i++) {
            System.out.printf("Block:\n" +
                    "Created by miner # %s\n" +
                    "Id: %d\n" +
                    "Timestamp: %o\n" +
                    "Magic number: %d\n" +
                    "Hash of the previous block: \n%s\n" +
                            "Hash of the block: \n%s\n" +
                            "Block data: ",
                    blockChain.get(i).currentThread,
                    blockChain.get(i).ID,
                    blockChain.get(i).timeStamp,
                    blockChain.get(i).magicNum,
                    blockChain.get(i).previousHash,
                    blockChain.get(i).hash);

            blockChain.get(i).textHistoryPerBlock.history
                            .stream()
                            .forEach(System.out::println);

            System.out.printf("Block was generating" +
                            " for %d seconds\n" +
                            "%s" + "\n" + "\n",
                    blockChain.get(i).secTime,
                    blockChain.get(i).NString);
        }
    }
    @Override
    boolean validate(Chain anyChain) {
        for (int i = 1; i < anyChain.blockChain.size(); i++) {
            if(!anyChain.blockChain.get(i - 1).hash.equals(anyChain.blockChain.get(i).previousHash)) {
                return false;
            }
        }
        return true;
    }

    public void addToChainStorage() {
        ChainStorage.put(this.ChainID, this);
    }

    public UUID getChainID() {
        return ChainID;
    }

    synchronized void saveToTextHistoryPerBlock(String text) {
        this.textHistoryPerBlock.history.add(text);
    }

    synchronized void reinitializeTextHistoryPerBlock(){
        this.textHistoryPerBlock.initialize();
    }
}
