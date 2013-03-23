package fr.univaix.iut.pokebattle.parser;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.node.Node;
import fr.univaix.iut.pokebattle.extractor.Extractor;
import fr.univaix.iut.pokebattle.pokemon.*;

import java.util.List;

public class AttackParser extends Parser {
    public final static String LIST_ATTACK_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_moves";

    public Attack parseAttack(List<? extends Node> tds) {
        AttackBuilder builder = new AttackBuilder();

        builder.setName(parseString(tds.get(1)));
        builder.setType(parseEnum(tds.get(2), Type.class));
        builder.setCategory(parseEnum(tds.get(3), Category.class));
        builder.setContest(parseEnum(tds.get(4), Contest.class));
        builder.setPP(parseInt(tds.get(5)));
        builder.setPower(parseInt(tds.get(6)));
        builder.setAccuracy(parseInt(tds.get(7)));

        return builder.createAttack();
    }

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Extractor extractor = new Extractor(agent);
        List<Attack> attacks = extractor.ExtractAttacks();
        for (Attack attack : attacks)
            System.out.println(attack.getHashTag().length());
    }
}
