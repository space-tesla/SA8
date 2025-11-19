import java.util.Scanner;

// State Interface
interface State {
    void insertCoin();
    void ejectCoin();
    void turnCrank();
}

// -------------------- Concrete States --------------------

class NoCoinState implements State {
    GumballMachine machine;

    NoCoinState(GumballMachine m) { machine = m; }

    public void insertCoin() {
        System.out.println("You inserted a coin.");
        machine.setState(machine.hasCoinState);
    }

    public void ejectCoin() {
        System.out.println("No coin to eject.");
    }

    public void turnCrank() {
        System.out.println("Insert coin first.");
    }
}

class HasCoinState implements State {
    GumballMachine machine;

    HasCoinState(GumballMachine m) { machine = m; }

    public void insertCoin() {
        System.out.println("Coin already inserted.");
    }

    public void ejectCoin() {
        System.out.println("Coin returned.");
        machine.setState(machine.noCoinState);
    }

    public void turnCrank() {
        System.out.println("Crank turned...");
        machine.releaseBall();
        if (machine.count > 0)
            machine.setState(machine.noCoinState);
        else
            machine.setState(machine.soldOutState);
    }
}

class SoldOutState implements State {
    GumballMachine machine;

    SoldOutState(GumballMachine m) { machine = m; }

    public void insertCoin() {
        System.out.println("Machine is sold out.");
    }

    public void ejectCoin() {
        System.out.println("No coin inserted.");
    }

    public void turnCrank() {
        System.out.println("No gumballs left.");
    }
}

// -------------------- Gumball Machine --------------------

class GumballMachine {
    State noCoinState;
    State hasCoinState;
    State soldOutState;
    State state;
    int count;

    GumballMachine(int numBalls) {
        noCoinState = new NoCoinState(this);
        hasCoinState = new HasCoinState(this);
        soldOutState = new SoldOutState(this);

        count = numBalls;
        state = (count > 0) ? noCoinState : soldOutState;
    }

    void setState(State s) { state = s; }

    void insertCoin() { state.insertCoin(); }
    void ejectCoin() { state.ejectCoin(); }
    void turnCrank() { state.turnCrank(); }

    void releaseBall() {
        if (count > 0) {
            System.out.println("A Gumball comes rolling out...");
            count--;
        }
    }
}

// -------------------- Demo + User Input --------------------

public class GumballDemo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GumballMachine machine = new GumballMachine(3);

        int choice;

        do {
            System.out.println("\n---- Gumball Machine ----");
            System.out.println("Gumballs left: " + machine.count);
            System.out.println("1. Insert Coin");
            System.out.println("2. Eject Coin");
            System.out.println("3. Turn Crank");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: machine.insertCoin(); break;
                case 2: machine.ejectCoin(); break;
                case 3: machine.turnCrank(); break;
                case 4: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        sc.close();
    }
}
