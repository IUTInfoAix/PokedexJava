package fr.univaix.iut.pokebattle.pokemon;

public class AttackBuilder {
    private String name;
    private Type type;
    private Category category;
    private int power;
    private int accuracy;
    private int pp;
    private Contest contest;

    public AttackBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AttackBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    public AttackBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public AttackBuilder setContest(Contest contest) {
        this.contest = contest;
        return this;
    }

    public AttackBuilder setPower(int power) {
        this.power = power;
        return this;
    }

    public AttackBuilder setAccuracy(int accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public AttackBuilder setPP(int pp) {
        this.pp = pp;
        return this;
    }

    public Attack createAttack() {
        return new Attack(name, type, category, contest, power, accuracy, pp);
    }
}