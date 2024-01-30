package fr.esgi.motus.business;

public class Attemp {

    private static int nextId = 0;
    private final int id;

    private Word attemptedWord;

    public Attemp(Word attemptedWord) {
        this.id = nextId++;
        this.attemptedWord = attemptedWord;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }

    public Word getAttemptedWord() {
        return attemptedWord;
    }

    public void setAttemptedWord(Word attemptedWord) {
        this.attemptedWord = attemptedWord;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "id=" + id +
                ", attemptedWord=" + attemptedWord +
                '}';
    }
}