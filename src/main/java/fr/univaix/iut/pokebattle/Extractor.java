package fr.univaix.iut.pokebattle;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

import java.util.List;

public class Extractor {
    public final static String LIST_ATTACK_URL = "http://www.pokepedia.fr/index.php/Liste_des_attaques";

    public Attack ExtractAttack(String nomAttack) {
        Attack attack = new Attack(nomAttack);
        if (nomAttack.equals("Charge") || nomAttack.equals("Trempette"))
            return attack;
        return null;
    }

    public Attack ExtractAttack(Node tr) {
        List<? extends Node> a = tr.findAll("a");
        return new Attack(a.get(0).getValue());
    }

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        List<? extends Node> tables = page.getRoot().findAll("table.wikitable");
        Extractor extractor = new Extractor();
        for (Node table : tables) {
            List<? extends Node> trs = table.findAll("tr:nth-child(n+2)");
            for (Node tr : trs) {
                Attack attack = extractor.ExtractAttack(tr);
                System.out.println(attack);
            }
        }
    }
}
