package fr.univaix.iut.pokebattle;


import fr.univaix.iut.pokebattle.extractor.Extractor;
import fr.univaix.iut.pokebattle.parser.AttackParser;
import fr.univaix.iut.pokebattle.pokemon.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class ExtractorTest {

    private final static String POUND_HTML = "<tr> \n" +
            " <td> 1 </td> \n" +
            " <td> <a href=\"/wiki/Pound_(move)\" title=\"Pound (move)\">Pound</a> </td> \n" +
            " <td align=\"center\" style=\"background:#A8A878\"><a href=\"/wiki/Normal_(type)\" title=\"Normal (type)\"><span style=\"color:#FFF;\">Normal</span></a> </td> \n" +
            " <td align=\"center\" style=\"background:#C92112\"><a href=\"/wiki/Physical_move\" title=\"Physical move\"><span style=\"color:#F67A1A;\">Physical</span></a> </td> \n" +
            " <td style=\"text-align:center; background:#F8D030\"><a href=\"/wiki/Tough_Contest\" title=\"Tough Contest\"><span style=\"color:#FFF;\">Tough</span></a> </td> \n" +
            " <td> 35 </td> \n" +
            " <td> 40 </td> \n" +
            " <td> 100% </td> \n" +
            " <td> I </td>\n" +
            "</tr>";

    private final static String KARATE_CHOP_HTML = "<tr> \n" +
            " <td> 2 </td> \n" +
            " <td> <a href=\"/wiki/Karate_Chop_(move)\" title=\"Karate Chop (move)\">Karate Chop</a><span class=\"explain\" title=\"Normal-type move in Generation I\">*</span> </td> \n" +
            " <td align=\"center\" style=\"background:#C03028\"><a href=\"/wiki/Fighting_(type)\" title=\"Fighting (type)\"><span style=\"color:#FFF;\">Fighting</span></a> </td> \n" +
            " <td align=\"center\" style=\"background:#C92112\"><a href=\"/wiki/Physical_move\" title=\"Physical move\"><span style=\"color:#F67A1A;\">Physical</span></a> </td> \n" +
            " <td style=\"text-align:center; background:#F8D030\"><a href=\"/wiki/Tough_Contest\" title=\"Tough Contest\"><span style=\"color:#FFF;\">Tough</span></a> </td> \n" +
            " <td> 25 </td> \n" +
            " <td> 50 </td> \n" +
            " <td> 100% </td> \n" +
            " <td> I </td>\n" +
            "</tr>";
    private final static String FUSION_BOLT_HTML = "<tr> \n" +
            " <td>559 </td> \n" +
            " <td><a href=\"/wiki/Fusion_Bolt_(move)\" title=\"Fusion Bolt (move)\">Fusion Bolt</a> </td> \n" +
            " <td align=\"center\" style=\"background:#F8D030\"><a href=\"/wiki/Electric_(type)\" title=\"Electric (type)\"><span style=\"color:#FFF;\">Electric</span></a> </td> \n" +
            " <td align=\"center\" style=\"background:#C92112\"><a href=\"/wiki/Physical_move\" title=\"Physical move\"><span style=\"color:#F67A1A;\">Physical</span></a> </td> \n" +
            " <td style=\"text-align:center; background:#68A090\"><a href=\"/wiki/%3F%3F%3F_Contest\" title=\"??? Contest\" class=\"mw-redirect\"><span style=\"color:#FFF;\">???</span></a> </td> \n" +
            " <td>5 </td> \n" +
            " <td>100 </td> \n" +
            " <td>100% </td> \n" +
            " <td> V </td>\n" +
            "</tr>";

    private static final Attack POUND = new AttackBuilder().setName("Pound").setType(Type.NORMAL).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(40).setAccuracy(100).setPP(35).createAttack();
    private static final Attack KARATE_CHOP = new AttackBuilder().setName("Karate Chop").setType(Type.FIGHTING).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(50).setAccuracy(100).setPP(25).createAttack();
    private static final Attack FUSION_BOLT = new AttackBuilder().setName("Fusion Bolt").setType(Type.ELECTRIC).setCategory(Category.PHYSICAL).setContest(Contest.UNKNOWN).setPower(100).setAccuracy(100).setPP(5).createAttack();

    private Extractor extractor;
    private MechanizeMock agent;

    @Before
    public void setUp() {
        agent = new MechanizeMock();
        agent.addPageRequest(AttackParser.LIST_ATTACK_URL, newHtml("Test Page", newTable(POUND_HTML + KARATE_CHOP_HTML + FUSION_BOLT_HTML)));
        extractor = new Extractor(agent);
    }

    @Test
    public void testExtractAttackGivenKebabShouldReturnNull() {
        Attack attack = extractor.ExtractAttack("Kebab");
        assertThat(attack).isNull();
    }

    @Test
    public void testExtractAttackGivenPoundShouldReturnAttackPound() {
        Attack attack = extractor.ExtractAttack(POUND.getName());
        assertThat(attack).isEqualTo(POUND);
    }

    @Test
    public void testExtractAttackGivenKarateChopShouldReturnAttackKarateChop() {
        Attack attack = extractor.ExtractAttack(KARATE_CHOP.getName());
        assertThat(attack).isEqualTo(KARATE_CHOP);
    }

    @Test
    public void testExtractAttackGivenContestUnknownShouldReturnValidAttack() {
        Attack attack = extractor.ExtractAttack(FUSION_BOLT.getName());
        assertThat(attack).isEqualTo(FUSION_BOLT);
    }

    @Test
    public void testExtractAttacksShouldReturnListOfAttacksGivenAHTMLDocument() throws Exception {
        List<Attack> attacks = extractor.ExtractAttacks();
        assertThat(attacks).containsExactly(POUND, KARATE_CHOP, FUSION_BOLT);
    }

    @Test
    public void testAttackPoundShouldHaveGoodStatistics() throws Exception {
        Attack attack = extractor.ExtractAttack(POUND.getName());

        assertThat(attack.getName()).isEqualTo(POUND.getName());
        assertThat(attack.getType()).isEqualTo(POUND.getType());
        assertThat(attack.getCategory()).isEqualTo(POUND.getCategory());
        assertThat(attack.getContest()).isEqualTo(POUND.getContest());
        assertThat(attack.getPower()).isEqualTo(POUND.getPower());
        assertThat(attack.getAccuracy()).isEqualTo(POUND.getAccuracy());
        assertThat(attack.getPP()).isEqualTo(POUND.getPP());
    }

    private String newHtml(String title, String bodyHtml) {
        return "<html><head><title>" + title + "</title></head><body>" + bodyHtml + "</body></html>";
    }

    private String newTable(String content) {
        return "<table>\n" + "<tr><th>Test</th></tr>\n" + content + "\n</table>";
    }
}
