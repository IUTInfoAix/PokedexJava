package fr.univaix.iut.pokebattle.json;

import com.google.common.collect.Lists;
import fr.univaix.iut.pokebattle.pokemon.*;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class JsonificatorTest {

    private static final Attack POUND = new AttackBuilder().setName("Pound").setType(Type.NORMAL).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(40).setAccuracy(100).setPP(35).createAttack();
    private static final Attack KARATE_CHOP = new AttackBuilder().setName("Karate Chop").setType(Type.FIGHTING).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(50).setAccuracy(100).setPP(25).createAttack();
    private static final Attack FUSION_BOLT = new AttackBuilder().setName("Fusion Bolt").setType(Type.ELECTRIC).setCategory(Category.PHYSICAL).setContest(Contest.UNKNOWN).setPower(100).setAccuracy(100).setPP(5).createAttack();

    private static final String POUND_JSON = "{\"name\":\"Pound\",\"type\":\"NORMAL\",\"category\":\"PHYSICAL\",\"contest\":\"TOUGH\",\"power\":40,\"accuracy\":100,\"PP\":35}";
    private static final String KARATE_CHOP_JSON = "{\"name\":\"Karate Chop\",\"type\":\"FIGHTING\",\"category\":\"PHYSICAL\",\"contest\":\"TOUGH\",\"power\":50,\"accuracy\":100,\"PP\":25}";
    private static final String FUSION_BOLT_JSON = "{\"name\":\"Fusion Bolt\",\"type\":\"ELECTRIC\",\"category\":\"PHYSICAL\",\"contest\":\"UNKNOWN\",\"power\":100,\"accuracy\":100,\"PP\":5}";

    private static final String LIST_ATTACKS_JSON = "[" + POUND_JSON + "," + KARATE_CHOP_JSON + "," + FUSION_BOLT_JSON + "]";

    private static List<Attack> attackList = Lists.newArrayList(POUND, KARATE_CHOP, FUSION_BOLT);
    private Jsonificator jsonificator = new Jsonificator();

    @Test
    public void testFromAttackToJsonGivenAnEmptyAttackShouldReturnAnEmptyJsonAttack() throws Exception {
        assertThat(jsonificator.FromAttackToJson(new Attack())).isEqualTo("{\"power\":0,\"accuracy\":0,\"PP\":0}");
    }

    @Test
    public void testFromAttackListToJsonGivenThreeAttackShouldReturnJsonListWithTheThreeAttack() {
        assertThat(jsonificator.FromAttackListToJson(attackList)).isEqualTo(LIST_ATTACKS_JSON);
    }

    @Test
    public void testFromAttackToJsonGivenAttackPoundShouldReturnPoundJson() {
        assertThat(jsonificator.FromAttackToJson(POUND)).isEqualTo(POUND_JSON);
    }
}