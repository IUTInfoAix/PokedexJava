package fr.univaix.iut.pokebattle.parser;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;

public class MoveParserFactory {
    public final static String LIST_MOVE_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_moves";
    private final Node root;

    public MoveParserFactory(MechanizeAgent agent) {
        Document page = agent.get(LIST_MOVE_URL);
        this.root = page.getRoot();
    }

    public Node getRoot() {
        return root;
    }

    public MoveParser createMoveParser() {
        return new MoveParser();
    }
}