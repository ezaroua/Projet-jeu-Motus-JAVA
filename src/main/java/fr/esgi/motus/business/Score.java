package fr.esgi.motus.business;

public class Score {

    private static int nextId = 0;
    private final int id;

    private int score;

    public Score() {
        this.id = nextId++;
        // Initialize score
        this.score = 0;
    }

    // Method to increase score
    public void increaseScore() {
        this.score++;
    }

    // Method to decrease score
    public void decreaseScore() {
        this.score--;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                '}';
    }
}