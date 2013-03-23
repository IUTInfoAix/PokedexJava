package fr.univaix.iut.pokebattle.extractor;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;
import com.google.common.collect.Lists;
import fr.univaix.iut.pokebattle.parser.AttackParser;
import fr.univaix.iut.pokebattle.pokemon.Attack;

import java.util.List;

public class Extractor {
    private final AttackParser attackParser;
    private final Node root;

    public Extractor(MechanizeAgent agent) {
        Document page = agent.get(AttackParser.LIST_ATTACK_URL);
        this.root = page.getRoot();
        this.attackParser = new AttackParser();
    }

    public Attack ExtractAttack(String attackName) {
        Node anchorAttackName = findAttackAnchor(attackName);

        if (AttackNotFound(anchorAttackName))
            return null;

        Node tr = getEnclosingTableRow(anchorAttackName);
        return ExtractAttack(tr);
    }

    public List<Attack> ExtractAttacks() {
        List<Attack> attacks = Lists.newArrayList();
        for (Node table : getTables(root)) {
            for (Node tr : getTableRowsExceptHeader(table)) {
                if (isValidAttackRow(tr)) attacks.add(ExtractAttack(tr));
            }
        }
        return attacks;
    }

    private Attack ExtractAttack(Node tr) {
        List<? extends Node> tds = getTableCells(tr);
        return attackParser.parseAttack(tds);
    }

    private List<? extends Node> getTableCells(Node tr) {
        return tr.findAll("td");
    }

    private boolean AttackNotFound(Node anchorAttackName) {
        return anchorAttackName.getName() == null;
    }

    private List<? extends Node> getTableRowsExceptHeader(Node table) {
        return table.findAll("tr:nth-child(n+2)");
    }

    private List<? extends Node> getTables(Node root) {
        return root.findAll("table");
    }

    private static boolean isValidAttackRow(Node tr) {
        return tr.findAll("td").size() == 9;
    }

    private Node findAttackAnchor(String attackName) {
        return root.find("a[title^='" + attackName + "']");
    }

    private Node getEnclosingTableRow(Node anchorAttackName) {
        return anchorAttackName.getParent().getParent();
    }
}