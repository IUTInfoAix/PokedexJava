package fr.univaix.iut.pokebattle.pokemon;

public class MoveBuilder {
    private String name;
    private Type type;
    private Category category;
    private int power;
    private int accuracy;
    private int pp;
    private Contest contest;

    public MoveBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MoveBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    public MoveBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public MoveBuilder setContest(Contest contest) {
        this.contest = contest;
        return this;
    }

    public MoveBuilder setPower(int power) {
        this.power = power;
        return this;
    }

    public MoveBuilder setAccuracy(int accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public MoveBuilder setPP(int pp) {
        this.pp = pp;
        return this;
    }

    public Move createMove() {
        return new Move(name, type, category, contest, power, accuracy, pp);
    }
}