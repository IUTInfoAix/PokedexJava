package fr.univaix.iut.pokebattle;


import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;
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

    private static final Attack POUND = new Attack("Pound", Type.NORMAL, Category.PHYSICAL, 40, 100, 35);
    private static final Attack KARATE_CHOP = new Attack("Karate Chop", Type.FIGHTING, Category.PHYSICAL, 50, 100, 25);

    private Extractor extractor;
    private MechanizeMock agent;

    @Before
    public void setUp() {
        agent = new MechanizeMock();
        agent.addPageRequest(Extractor.LIST_ATTACK_URL, newHtml("Test Page", newTable(POUND_HTML + KARATE_CHOP_HTML)));
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
    public void testExtractAttackGivenNodeKarateChopAndPoundShouldReturnAttackKarateChopAndPound() {
        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        List<? extends Node> trs = page.getRoot().findAll("tr");

        Attack attack = extractor.ExtractAttack(trs.get(0));
        assertThat(attack).isEqualTo(POUND);

        attack = extractor.ExtractAttack(trs.get(1));
        assertThat(attack).isEqualTo(KARATE_CHOP);
    }

    @Test
    public void testAttackPoundShouldHaveGoodStatistics() throws Exception {
        Attack attack = extractor.ExtractAttack(POUND.getName());

        assertThat(attack.getName()).isEqualTo(POUND.getName());
        assertThat(attack.getType()).isEqualTo(POUND.getType());
        assertThat(attack.getCategory()).isEqualTo(POUND.getCategory());
        assertThat(attack.getPower()).isEqualTo(POUND.getPower());
        assertThat(attack.getAccuracy()).isEqualTo(POUND.getAccuracy());
        assertThat(attack.getPP()).isEqualTo(POUND.getPP());
    }

    private String newHtml(String title, String bodyHtml) {
        return "<html><head><title>" + title + "</title></head><body>" + bodyHtml + "</body></html>";
    }

    private String newTable(String content) {
        return "<table>\n" + content + "\n</table>";
    }
}
