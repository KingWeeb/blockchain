package blockchain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

public class MiningSim {
    public int POOL_SIZE;
    public Chain chain;
    public MiningSim(int POOL_SIZE, Chain chain) {
        this.POOL_SIZE = POOL_SIZE;
        this.chain = chain;
    }

    public void mineTheBlocks() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

        Callable<Block> generatingBlocks = () -> {
            return new Block(chain.numOfZeros, chain.previousHashForNextBlockGeneration
                    , chain.getChainID()).generateProvedBlock();
        };

        Collection<Callable<Block>> taskList = new ArrayList<>();

        for(int i = 0; i < POOL_SIZE; i++) {
            taskList.add(generatingBlocks);
        }


        try {
            Block winner = executor.invokeAny(taskList);

//            synchronized (Block.class) {
//                Chain.ChainStorage.get(chain.getChainID()).achieved = true;
//            }
            chain.addTOChain(winner);
            chain.previousHashForNextBlockGeneration = winner.hash;
            winner.print();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
            //executor.awaitTermination(60, TimeUnit.MILLISECONDS);
        }




    }
}
