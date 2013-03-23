package fr.univaix.iut.pokebattle.extractor;

import com.gistlabs.mechanize.document.node.Node;
import com.google.common.collect.Lists;
import fr.univaix.iut.pokebattle.parser.AttackParser;
import fr.univaix.iut.pokebattle.parser.AttackParserFactory;
import fr.univaix.iut.pokebattle.pokemon.Attack;

import java.util.List;

public class Extractor {
    private final AttackParser attackParser;
    private final Node root;

    public Extractor(AttackParserFactory factory) {
        this.root = factory.getRoot();
        this.attackParser = factory.createAttackParser();
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
        for (Node table : getTables()) {
            for (Node tr : getTableRowsExceptHeader(table)) {
                if (isValidAttackRow(tr)) attacks.add(ExtractAttack(tr));
            }
        }
        return attacks;
    }

    private Attack ExtractAttack(Node tr) {
        return attackParser.parse(tr);
    }

    private List<? extends Node> getTables() {
        return root.findAll("table");
    }

    private Node findAttackAnchor(String attackName) {
        return root.find("a[title^='" + attackName + "']");
    }

    private static boolean AttackNotFound(Node anchorAttackName) {
        return anchorAttackName.getName() == null;
    }

    private static List<? extends Node> getTableRowsExceptHeader(Node table) {
        return table.findAll("tr:nth-child(n+2)");
    }

    private static boolean isValidAttackRow(Node tr) {
        return tr.findAll("td").size() == 9;
    }

    private static Node getEnclosingTableRow(Node anchorAttackName) {
        return anchorAttackName.getParent().getParent();
    }
}