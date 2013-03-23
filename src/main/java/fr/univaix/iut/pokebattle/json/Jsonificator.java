package fr.univaix.iut.pokebattle.json;


import com.google.gson.Gson;
import fr.univaix.iut.pokebattle.pokemon.Attack;

import java.util.List;

public class Jsonificator {

    public String FromAttackListToJson(List<Attack> attacks) {
        Gson gson = new Gson();
        return gson.toJson(attacks);
    }

    public String FromAttackToJson(Attack attack) {
        Gson gson = new Gson();
        return gson.toJson(attack);
    }

}
