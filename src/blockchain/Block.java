package blockchain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Block extends BlockFactory0 implements Serializable {
    public String currentThread;
    public int N;
    public String NString;
    public UUID chainID;
    protected String hash;
    public static volatile int counter = 0;
    long timeStamp = new Date().getTime(); //seconds since Jan.1st.1970
    volatile int ID;
    public ChatHistory textHistoryPerBlock = new ChatHistory();
    int magicNum;
    long nanoTime = 0L;
    long secTime;

    public Block(int numOfZeros, String previousHash, UUID chainID) {
        super(numOfZeros, previousHash, chainID);
    }

    @Override
    public Block generateProvedBlock(){
        long startTime = System.nanoTime();
        Random rand = new Random();
        boolean proved = false;

        while (!proved) {
            this.magicNum = rand.nextInt(99999999);
            this.hash = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + this.magicNum);
            String zeros = hash.substring(0, numOfZeros);
            boolean construct = true;
            for (int i = 0; i < zeros.length(); i++) {
                if (zeros.charAt(i) != '0') {
                    construct = false;
                    break;
                }
            }
            if (hash.charAt(numOfZeros) == '0') {
                construct = false;
            }
            if (construct) {
                proved = true;
            }
        }
        long endTime = System.nanoTime();
        nanoTime = endTime - startTime;
        secTime = nanoTime / 1000000000;
        this.currentThread = Thread.currentThread().getName();
        return this;

    }

    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws Exception{
        ois.defaultReadObject();
    }

    public void print() {
        System.out.printf("Block:\n" +
                        "Id: %d\n" +
                        "Timestamp: %o\n" +
                        "Magic number: %d\n" +
                        "Hash of the previous block: \n%s\n" + "Hash of the block: \n%s\n" + "Block was generating" +
                        " for %d seconds\n" +
                        "\n",
                this.ID,
                this.timeStamp,
                this.magicNum,
                this.previousHash,
                this.hash,
                this.secTime);
    }
}
