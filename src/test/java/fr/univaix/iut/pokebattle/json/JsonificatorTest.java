package fr.univaix.iut.pokebattle.json;

import com.google.common.collect.Lists;
import fr.univaix.iut.pokebattle.pokemon.*;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class JsonificatorTest {

    private static final Move POUND = new MoveBuilder().setName("Pound").setType(Type.NORMAL).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(40).setAccuracy(100).setPP(35).createMove();
    private static final Move KARATE_CHOP = new MoveBuilder().setName("Karate Chop").setType(Type.FIGHTING).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(50).setAccuracy(100).setPP(25).createMove();
    private static final Move FUSION_BOLT = new MoveBuilder().setName("Fusion Bolt").setType(Type.ELECTRIC).setCategory(Category.PHYSICAL).setContest(Contest.UNKNOWN).setPower(100).setAccuracy(100).setPP(5).createMove();

    private static final String POUND_JSON = "{\"name\":\"Pound\",\"type\":\"NORMAL\",\"category\":\"PHYSICAL\",\"contest\":\"TOUGH\",\"power\":40,\"accuracy\":100,\"PP\":35}";
    private static final String KARATE_CHOP_JSON = "{\"name\":\"Karate Chop\",\"type\":\"FIGHTING\",\"category\":\"PHYSICAL\",\"contest\":\"TOUGH\",\"power\":50,\"accuracy\":100,\"PP\":25}";
    private static final String FUSION_BOLT_JSON = "{\"name\":\"Fusion Bolt\",\"type\":\"ELECTRIC\",\"category\":\"PHYSICAL\",\"contest\":\"UNKNOWN\",\"power\":100,\"accuracy\":100,\"PP\":5}";

    private static final String LIST_MOVES_JSON = "[" + POUND_JSON + "," + KARATE_CHOP_JSON + "," + FUSION_BOLT_JSON + "]";

    private static List<Move> moveList = Lists.newArrayList(POUND, KARATE_CHOP, FUSION_BOLT);
    private Jsonificator jsonificator = new Jsonificator();

    @Test
    public void testFromMoveToJsonGivenAnEmptyMoveShouldReturnAnEmptyJsonMove() throws Exception {
        assertThat(jsonificator.FromMoveToJson(new Move())).isEqualTo("{\"power\":0,\"accuracy\":0,\"PP\":0}");
    }

    @Test
    public void testFromMoveListToJsonGivenThreeMoveShouldReturnJsonListWithTheThreeMove() {
        assertThat(jsonificator.FromMoveListToJson(moveList)).isEqualTo(LIST_MOVES_JSON);
    }

    @Test
    public void testFromMoveToJsonGivenMovePoundShouldReturnPoundJson() {
        assertThat(jsonificator.FromMoveToJson(POUND)).isEqualTo(POUND_JSON);
    }
}