package fr.univaix.iut.pokebattle.pokemon;

public enum Tier {
    UBER("Uber", 42),
    OU("Overused", 51),
    BL("Borderline", 5),
    UU("Underused", 58),
    BL2("Borderline 2", 2),
    RU("Rarelyused", 62),
    BL3("Borderline 3", 1),
    NU("Neverused", 202),
    LC("Little Cup", 211),
    LIMBO("Limbo", 1),
    NFE("Not Fully Evolved", 50);

    String name;
    int numberOfPokemon;

    private Tier(String name, int numberOfPokemon) {
        this.name = name;
        this.numberOfPokemon = numberOfPokemon;
    }
}
