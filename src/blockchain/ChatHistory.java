package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
    public List<String> history = new ArrayList<>();

    public ChatHistory() {
        this.history.add("no messages");
    }
    public synchronized void initialize(){ //locks on each chatHistory object for multi-chain
        this.history = new ArrayList<>();
        this.history.add("no messages");
    }

}
