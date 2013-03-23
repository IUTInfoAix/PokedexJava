package fr.univaix.iut.pokebattle.pokemon;

public class Move {
    private String name;
    private Type type;
    private Category category;
    private Contest contest;
    private int power;
    private int accuracy;
    private int PP;

    public Move() {

    }

    public Move(String name, Type type, Category category, Contest contest, int power, int accuracy, int PP) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.contest = contest;
        this.power = power;
        this.accuracy = accuracy;
        this.PP = PP;
    }

    public String getHashTag() {
        return "#" + name.replace(" ", "");
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

        Move move = (Move) o;

        if (PP != move.PP) return false;
        if (accuracy != move.accuracy) return false;
        if (power != move.power) return false;
        if (category != move.category) return false;
        if (contest != move.contest) return false;
        if (name != null ? !name.equals(move.name) : move.name != null) return false;
        return type == move.type;

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
        return "Move{" +
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
