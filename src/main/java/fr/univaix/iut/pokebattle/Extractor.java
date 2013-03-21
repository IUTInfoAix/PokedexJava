package fr.univaix.iut.pokebattle;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

import java.util.List;

public class Extractor {
    public final static String LIST_ATTACK_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_moves";

    MechanizeAgent agent;

    Extractor(MechanizeAgent agent) {
        this.agent = agent;
    }

    public Attack ExtractAttack(String nomAttack) {

        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        Node anchorAttackName = page.getRoot().find("a[title^='" + nomAttack + "']");

        if (anchorAttackName.getName() == null)
            return null;

        Node tr = anchorAttackName.getParent().getParent();
        return ExtractAttack(tr);
    }

    public Attack ExtractAttack(Node tr) {
        List<? extends Node> tds = tr.findAll("td");
        return getAttack(tds);
    }

    private Attack getAttack(List<? extends Node> tds) {
        //int indexNumber = parseIndexNumber(tds.get(0));
        String Name = parseString(tds.get(1));
        Type type = parseType(tds.get(2));
        Category category = parseCategory(tds.get(3));
        //Contest contest = parseContest(tds.get(4));
        int PP = parseInt(tds.get(5));
        int power = parseInt(tds.get(6));
        int accuracy = parseInt(tds.get(7));
        return new Attack(Name, type, category, power, accuracy, PP);
    }

    private int parseInt(Node node) {
        String value = parseString(node);
        if (value.equals("—") || value.isEmpty())
            return 0;
        return Integer.parseInt(value);
    }

    private String parseString(Node node) {
        return node.getValue().replaceAll("\\*|%", "");
    }

    private String ParseEnum(Node node) {
        return parseString(node).toUpperCase();
    }

    private Contest parseContest(Node node) {
        String value = ParseEnum(node);
        return Contest.valueOf(value);
    }

    private Category parseCategory(Node node) {
        String value = ParseEnum(node);
        return Category.valueOf(value);
    }

    private Type parseType(Node node) {
        String value = ParseEnum(node);
        return Type.valueOf(value);
    }

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        List<? extends Node> tables = page.getRoot().findAll("table");
        Extractor extractor = new Extractor(agent);
        for (Node table : tables) {
            List<? extends Node> trs = table.findAll("tr:nth-child(n+2)");
            for (Node tr : trs) {
                if (tr.findAll("td").size() == 9) {
                    Attack attack = extractor.ExtractAttack(tr);
                    System.out.println(attack);
                    System.out.println(tr);
                }
            }
        }
    }
}
