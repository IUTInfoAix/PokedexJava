package fr.univaix.iut.pokebattle;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Extractor {
    private final AttackParser attackExtractor;
    private final Document page;

    public Extractor(MechanizeAgent agent) {
        this.page = agent.get(AttackParser.LIST_ATTACK_URL);
        this.attackExtractor = new AttackParser();
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
        return attackExtractor.parseAttack(tds);
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

    private List<? extends Node> getTableRowsExceptHeader(Node table) {
        return table.findAll("tr:nth-child(n+2)");
    }

    private List<? extends Node> getAttackTables() {
        return page.getRoot().findAll("table");
    }

    private static boolean isValidAttackRow(Node tr) {
        return tr.findAll("td").size() == 9;
    }
}