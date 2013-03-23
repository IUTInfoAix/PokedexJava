package fr.univaix.iut.pokebattle.parser;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

public class AttackParserFactory {
    public final static String LIST_ATTACK_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_moves";
    private final Node root;

    public AttackParserFactory(MechanizeAgent agent) {
        Document page = agent.get(LIST_ATTACK_URL);
        this.root = page.getRoot();
    }

    public Node getRoot() {
        return root;
    }

    public AttackParser createAttackParser() {
        return new AttackParser();
    }
}