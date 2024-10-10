package com.example.myexpenses;

public class Expense {
    private int id;
    private String name;
    private String description;
    private int amount;
    private String category;

    public Expense(String name, String description, int amount, String category) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Expense{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
