package fr.univaix.iut.pokebattle;


import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ExtractorTest {

    final static String trempetteHtml = "<tr> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Trempette\" title=\"Trempette\">Trempette</a> </td> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Normal_(type)\" title=\"Normal (type)\"><img alt=\"normal\" src=\"/images/9/91/Type_miniat_normal.png\" width=\"32\" height=\"14\" /></a> </td> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Cat%C3%A9gories_d%27attaque\" title=\"Cat&eacute;gories d'attaque\"><img alt=\"Cat&eacute;gorie miniat statut.gif\" src=\"/images/6/66/Cat%C3%A9gorie_miniat_statut.gif\" width=\"32\" height=\"14\" /></a> </td> \n" +
            " <td style=\"vertical-align:middle\"> — </td> \n" +
            " <td style=\"vertical-align:middle\"> — </td> \n" +
            " <td style=\"vertical-align:middle\"> 40 </td>\n" +
            "</tr>";
    final static String ChargeHtml = "<tr> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Charge\" title=\"Charge\">Charge</a> </td> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Normal_(type)\" title=\"Normal (type)\"><img alt=\"normal\" src=\"/images/9/91/Type_miniat_normal.png\" width=\"32\" height=\"14\" /></a> </td> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Cat%C3%A9gories_d%27attaque\" title=\"Cat&eacute;gories d'attaque\"><img alt=\"Cat&eacute;gorie miniat physique.gif\" src=\"/images/c/c4/Cat%C3%A9gorie_miniat_physique.gif\" width=\"32\" height=\"14\" /></a> </td> \n" +
            " <td style=\"vertical-align:middle\"> 50<span class=\"explain\" title=\"35 de la premi&egrave;re &agrave; la quatri&egrave;me g&eacute;n&eacute;ration incluse\">*</span> </td> \n" +
            " <td style=\"vertical-align:middle\"> 100%<span class=\"explain\" title=\"95% de la premi&egrave;re &agrave; la quatri&egrave;me g&eacute;n&eacute;ration incluse\">*</span> </td> \n" +
            " <td style=\"vertical-align:middle\"> 30 </td>\n" +
            "</tr>";

    private Extractor extractor;
    private MechanizeMock agent;

    @Before
    public void setUp() {
        agent = new MechanizeMock();
        agent.addPageRequest(Extractor.LIST_ATTACK_URL, newHtml("Test Page", newTable(trempetteHtml + ChargeHtml)));
        extractor = new Extractor(agent);
    }

    @Test
    public void testExtractAttackGivenKebabShouldReturnNull() {

        assertThat(extractor.ExtractAttack("Kebab")).isNull();
    }

    @Test
    public void testExtractAttackGivenChargeShouldReturnAttackCharge() {

        Attack attack = extractor.ExtractAttack("Charge");
        assertThat(attack.getName()).isEqualTo("Charge");
    }

    @Test
    public void testExtractAttackGivenTrempetteShouldReturnAttackTrempette() {
        Attack attack = extractor.ExtractAttack("Trempette");
        assertThat(attack.getName()).isEqualTo("Trempette");
    }

    @Test
    public void testExtractAttackGivenNodeTrempetteShouldReturnTrempette() {
        Document page = agent.get(Extractor.LIST_ATTACK_URL);
        Node tr = page.getRoot().find("tr");
        Attack attack = extractor.ExtractAttack(tr);
        assertThat(attack.getName()).isEqualTo("Trempette");
    }

    private String newHtml(String title, String bodyHtml) {
        return "<html><head><title>" + title + "</title></head><body>" + bodyHtml + "</body></html>";
    }

    private String newTable(String content) {
        return "<table>\n" + content + "\n</table>";
    }
}
