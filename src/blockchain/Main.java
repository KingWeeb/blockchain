package blockchain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int POOL_SIZE = Runtime.getRuntime().availableProcessors();

        Chain chain0 = new Chain();
        chain0.addToChainStorage();

        MiningSim mineIt = new MiningSim(POOL_SIZE, chain0);
        for (int i = 0; i < 6; i++) {
            mineIt.mineTheBlocks();
        }

        chain0.print();//not printing after each block cuz of grader test

    }
}
