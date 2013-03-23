package fr.univaix.iut.pokebattle.json;


import com.gistlabs.mechanize.MechanizeAgent;
import com.google.gson.Gson;
import fr.univaix.iut.pokebattle.extractor.Extractor;
import fr.univaix.iut.pokebattle.parser.AttackParserFactory;
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

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Extractor extractor = new Extractor(new AttackParserFactory(agent));
        System.out.println(new Jsonificator().FromAttackListToJson(extractor.ExtractAttacks()));
    }

}
