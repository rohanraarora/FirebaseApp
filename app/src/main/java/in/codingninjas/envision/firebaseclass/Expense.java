package in.codingninjas.envision.firebaseclass;

import java.util.Random;
import java.util.UUID;

public class Expense {

    private String id;
    private String name;
    private int amount;

    public Expense() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expense(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static Expense getRandomExpense(){

        return new Expense("Expense:" + UUID.randomUUID(),new Random().nextInt(100));
    }
}
