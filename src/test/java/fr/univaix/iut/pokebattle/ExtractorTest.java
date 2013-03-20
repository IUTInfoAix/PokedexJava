package fr.univaix.iut.pokebattle;


import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.node.Node;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ExtractorTest {
    MechanizeMock agent = new MechanizeMock();
    final static String trempetteHtml = "<table><tr> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Trempette\" title=\"Trempette\">Trempette</a> </td> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Normal_(type)\" title=\"Normal (type)\"><img alt=\"normal\" src=\"/images/9/91/Type_miniat_normal.png\" width=\"32\" height=\"14\" /></a> </td> \n" +
            " <td style=\"vertical-align:middle\"> <a href=\"/index.php/Cat%C3%A9gories_d%27attaque\" title=\"Cat&eacute;gories d'attaque\"><img alt=\"Cat&eacute;gorie miniat statut.gif\" src=\"/images/6/66/Cat%C3%A9gorie_miniat_statut.gif\" width=\"32\" height=\"14\" /></a> </td> \n" +
            " <td style=\"vertical-align:middle\"> — </td> \n" +
            " <td style=\"vertical-align:middle\"> — </td> \n" +
            " <td style=\"vertical-align:middle\"> 40 </td>\n" +
            "</tr></table>";

    @Test
    public void testExtractAttackGivenKebabShouldReturnNull() {
        Extractor extractor = new Extractor();
        assertThat(extractor.ExtractAttack("Kebab")).isNull();
    }

    @Test
    public void testExtractAttackGivenChargeShouldReturnAttackCharge() {
        Extractor extractor = new Extractor();
        Attack attack = extractor.ExtractAttack("Charge");
        assertThat(attack.getName()).isEqualTo("Charge");
    }

    @Test
    public void testExtractAttackGivenTrempetteShouldReturnAttackTrempette() {
        Extractor extractor = new Extractor();
        Attack attack = extractor.ExtractAttack("Trempette");
        assertThat(attack.getName()).isEqualTo("Trempette");
    }

    @Test
    public void testExtractAttackGivenNodeTrempetteShouldReturnTrempette() {
        agent.addPageRequest(Extractor.LIST_ATTACK_URL, newHtml("Test Page", trempetteHtml));
        Document page = agent.get(Extractor.LIST_ATTACK_URL);

        Node tr = page.getRoot().find("tr");
        Extractor extractor = new Extractor();
        Attack attack = extractor.ExtractAttack(tr);
        assertThat(attack.getName()).isEqualTo("Trempette");
    }

    private String newHtml(String title, String bodyHtml) {
        return "<html><head><title>" + title + "</title></head><body>" + bodyHtml + "</body></html>";
    }
}
