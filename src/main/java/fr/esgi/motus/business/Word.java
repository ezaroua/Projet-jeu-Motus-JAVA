package fr.esgi.motus.business;

public class Word {

    private static int nextId = 0;
    private final int id;
    private String word;

    public Word(String word) {
        this.id = nextId++;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                '}';
    }
}