package blockchain;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MinerDeprecated implements Runnable{
    public int POOL_SIZE;
    public UUID chainID;

    @Override
    public void run() {

            Chain chain0 = Chain.ChainStorage.get(this.chainID);


            ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

            executor.submit(() -> {
                for (int i = 0; i < POOL_SIZE; i++) {
                    Block temp =
                            new Block(chain0.numOfZeros, chain0.previousHashForNextBlockGeneration, chainID).generateProvedBlock();
                    chain0.addTOChain(temp);
                }
            });

            Thread daemon0 = new Thread(() -> {
                while (true) {
                    if (Chain.ChainStorage.get(chainID).achieved) {
                        executor.shutdownNow();
                    }
                }
            });
            daemon0.setDaemon(true);
            daemon0.start();

            if (executor.isShutdown()) {
                daemon0.stop();
            }
    }
}
