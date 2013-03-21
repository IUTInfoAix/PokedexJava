package fr.univaix.iut.pokebattle;

public class Attack {
    private String name;
    private Type type;
    private Category category;
    private int power;
    private int accuracy;
    private int PP;

    public Attack(String name, Type type, Category category, int power, int accuracy, int PP) {
        this.name = name;
        this.type = type;
        this.category = category;
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

        return !(name != null ? !name.equals(attack.name) : attack.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", category=" + category +
                ", power=" + power +
                ", accuracy=" + accuracy +
                ", PP=" + PP +
                '}';
    }
}
