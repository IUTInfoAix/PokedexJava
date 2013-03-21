package fr.univaix.iut.pokebattle;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Extractor {
    public final static String LIST_ATTACK_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_moves";

    MechanizeAgent agent;
    Document page;

    Extractor(MechanizeAgent agent) {
        this.agent = agent;
        this.page = agent.get(Extractor.LIST_ATTACK_URL);
    }

    public List<Attack> ExtractAttacks() {
        List<Attack> attacks = new ArrayList<Attack>();
        List<? extends Node> tables = getAttackTables();
        for (Node table : tables) {
            List<? extends Node> trs = getTableRowsExceptHeader(table);
            for (Node tr : trs) {
                if (isValidAttackRow(tr)) {
                    attacks.add(ExtractAttack(tr));
                }
            }
        }
        return attacks;
    }

    public Attack ExtractAttack(String nomAttack) {
        Node anchorAttackName = page.getRoot().find("a[title^='" + nomAttack + "']");

        if (anchorAttackName.getName() == null)
            return null;

        Node tr = anchorAttackName.getParent().getParent();
        return ExtractAttack(tr);
    }

    private Attack ExtractAttack(Node tr) {
        List<? extends Node> tds = tr.findAll("td");
        return parseAttack(tds);
    }

    private List<? extends Node> getTableRowsExceptHeader(Node table) {
        return table.findAll("tr:nth-child(n+2)");
    }

    private List<? extends Node> getAttackTables() {
        return page.getRoot().findAll("table");
    }

    private static boolean isValidAttackRow(Node tr) {
        return tr.findAll("td").size() == 9;
    }


    private Attack parseAttack(List<? extends Node> tds) {
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

    private int parseInt(Node node) {
        String value = parseString(node);
        if (value.equals("—") || value.isEmpty())
            return 0;
        return Integer.parseInt(value);
    }

    private String parseString(Node node) {
        return node.getValue().replaceAll("\\*|%", "");
    }

    private <T extends Enum<T>> T parseEnum(Node node, Class<T> tClass) {
        String value = parseString(node).toUpperCase();
        if (value.equals("???"))
            value = "UNKNOWN";
        return T.valueOf(tClass, value);
    }

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Extractor extractor = new Extractor(agent);
        List<Attack> attacks = extractor.ExtractAttacks();
        for (Attack attack : attacks)
            System.out.println(attack.getHashTag().length());
    }
}
