package fr.univaix.iut.pokebattle;

public class Attack {
    private String name;
    private Type type;
    private Category category;
    private Contest contest;
    private int power;
    private int accuracy;
    private int PP;

    public Attack(String name, Type type, Category category, Contest contest, int power, int accuracy, int PP) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.contest = contest;
        this.power = power;
        this.accuracy = accuracy;
        this.PP = PP;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public Contest getContest() {
        return contest;
    }

    public int getPower() {
        return power;
    }


    public int getAccuracy() {
        return accuracy;
    }

    public int getPP() {
        return PP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attack attack = (Attack) o;

        if (PP != attack.PP) return false;
        if (accuracy != attack.accuracy) return false;
        if (power != attack.power) return false;
        if (category != attack.category) return false;
        if (contest != attack.contest) return false;
        if (name != null ? !name.equals(attack.name) : attack.name != null) return false;
        if (type != attack.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + type.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + contest.hashCode();
        result = 31 * result + power;
        result = 31 * result + accuracy;
        result = 31 * result + PP;
        return result;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", category=" + category +
                ", contest=" + contest +
                ", power=" + power +
                ", accuracy=" + accuracy +
                ", PP=" + PP +
                '}';
    }
}
