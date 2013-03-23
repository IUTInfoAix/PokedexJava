package fr.univaix.iut.pokebattle.extractor;

import com.gistlabs.mechanize.document.node.Node;
import com.google.common.collect.Lists;
import fr.univaix.iut.pokebattle.parser.MoveParser;
import fr.univaix.iut.pokebattle.parser.MoveParserFactory;
import fr.univaix.iut.pokebattle.pokemon.Move;

import java.util.List;

public class Extractor {
    private final MoveParser moveParser;
    private final Node root;

    public Extractor(MoveParserFactory factory) {
        this.root = factory.getRoot();
        this.moveParser = factory.createMoveParser();
    }

    public Move ExtractMove(String moveName) {
        Node anchorMoveName = findMoveAnchor(moveName);

        if (MoveNotFound(anchorMoveName))
            return null;

        Node tr = getEnclosingTableRow(anchorMoveName);
        return ExtractMove(tr);
    }

    public List<Move> ExtractMoves() {
        List<Move> moves = Lists.newArrayList();
        for (Node table : getTables()) {
            for (Node tr : getTableRowsExceptHeader(table)) {
                if (isValidMoveRow(tr)) moves.add(ExtractMove(tr));
            }
        }
        return moves;
    }

    private Move ExtractMove(Node tr) {
        return moveParser.parse(tr);
    }

    private List<? extends Node> getTables() {
        return root.findAll("table");
    }

    private Node findMoveAnchor(String moveName) {
        return root.find("a[title^='" + moveName + "']");
    }

    private static boolean MoveNotFound(Node anchorMoveName) {
        return anchorMoveName.getName() == null;
    }

    private static List<? extends Node> getTableRowsExceptHeader(Node table) {
        return table.findAll("tr:nth-child(n+2)");
    }

    private static boolean isValidMoveRow(Node tr) {
        return tr.findAll("td").size() == 9;
    }

    private static Node getEnclosingTableRow(Node anchorMoveName) {
        return anchorMoveName.getParent().getParent();
    }
}