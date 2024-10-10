package com.example.myexpenses;

public class Insight {
    private int id;
    private String content;

    public Insight(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Insight{" +
                "content='" + content + '\'' +
                '}';
    }
}
