package fr.univaix.iut.pokebattle;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

import java.util.List;

public class Extractor {
    public final static String LIST_ATTACK_URL = "http://www.pokepedia.fr/index.php/Liste_des_attaques";
    MechanizeAgent agent;

    Extractor(MechanizeAgent agent) {
        this.agent = agent;
    }

    public Attack ExtractAttack(String nomAttack) {

        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        Node baliseAttack = page.getRoot().find("a[title='" + nomAttack + "']");

        if (baliseAttack.getName() == null)
            return null;

        Node tr = baliseAttack.getParent().getParent();
        return ExtractAttack(tr);
    }

    public Attack ExtractAttack(Node tr) {
        List<? extends Node> a = tr.findAll("a");
        return new Attack(a.get(0).getValue());
    }

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        List<? extends Node> tables = page.getRoot().findAll("table.wikitable");
        Extractor extractor = new Extractor(agent);
        for (Node table : tables) {
            List<? extends Node> trs = table.findAll("tr:nth-child(n+2)");
            for (Node tr : trs) {
                Attack attack = extractor.ExtractAttack(tr);
                System.out.println(attack);
            }
        }
    }
}
