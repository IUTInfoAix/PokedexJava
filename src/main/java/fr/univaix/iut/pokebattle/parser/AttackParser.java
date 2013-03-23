package fr.univaix.iut.pokebattle.parser;

import com.gistlabs.mechanize.document.node.Node;
import fr.univaix.iut.pokebattle.pokemon.*;

import java.util.List;

public class AttackParser extends AbstractParser<Attack> {

    AttackParser() {
    }

    @Override
    public Attack parse(Node tr) {
        List<? extends Node> tds = getTableCells(tr);
        return parseAttack(tds);
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

    private List<? extends Node> getTableCells(Node tr) {
        return tr.findAll("td");
    }

}
