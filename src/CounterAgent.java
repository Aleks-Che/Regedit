import java.util.ArrayList;

public class CounterAgent {
    String name;
    String fullName;
    String INN;
    ArrayList<Contract> contractList;

    public CounterAgent(String name, String fullName, String INN, ArrayList<Contract> contractList) {
        this.name = name;
        this.fullName = fullName;
        this.INN = INN;
        this.contractList = contractList;
    }
}
