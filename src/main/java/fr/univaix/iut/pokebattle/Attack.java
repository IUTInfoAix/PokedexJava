package fr.univaix.iut.pokebattle;

public class Attack {
    private String name;

    public Attack(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "name='" + name + '\'' +
                '}';
    }
}
