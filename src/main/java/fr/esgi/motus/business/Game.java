package fr.esgi.motus.business;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private static int nextId = 0;
    private final int id;

    private Word word;
    private Score score;
    private List<Attemp> attempts;

    public Game() {
        this.id = nextId++;
        this.score = new Score();
        this.attempts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public List<Attemp> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attemp> attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", word=" + word +
                ", score=" + score +
                ", attempts=" + attempts +
                '}';
    }
}