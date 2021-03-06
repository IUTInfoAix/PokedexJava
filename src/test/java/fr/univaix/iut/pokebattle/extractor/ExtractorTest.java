package fr.univaix.iut.pokebattle.extractor;


import fr.univaix.iut.pokebattle.MechanizeMock;
import fr.univaix.iut.pokebattle.parser.MoveParserFactory;
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

    private static final Move POUND = new MoveBuilder().setName("Pound").setType(Type.NORMAL).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(40).setAccuracy(100).setPP(35).createMove();
    private static final Move KARATE_CHOP = new MoveBuilder().setName("Karate Chop").setType(Type.FIGHTING).setCategory(Category.PHYSICAL).setContest(Contest.TOUGH).setPower(50).setAccuracy(100).setPP(25).createMove();
    private static final Move FUSION_BOLT = new MoveBuilder().setName("Fusion Bolt").setType(Type.ELECTRIC).setCategory(Category.PHYSICAL).setContest(Contest.UNKNOWN).setPower(100).setAccuracy(100).setPP(5).createMove();

    private Extractor extractor;
    private MechanizeMock agent;

    @Before
    public void setUp() {
        agent = new MechanizeMock();
        agent.addPageRequest(MoveParserFactory.LIST_MOVE_URL, newHtml("Test Page", newTable(POUND_HTML + KARATE_CHOP_HTML + FUSION_BOLT_HTML)));

        extractor = new Extractor(new MoveParserFactory(agent));
    }

    @Test
    public void testExtractMoveGivenKebabShouldReturnNull() {
        Move move = extractor.ExtractMove("Kebab");
        assertThat(move).isNull();
    }

    @Test
    public void testExtractMoveGivenPoundShouldReturnMovePound() {
        Move move = extractor.ExtractMove(POUND.getName());
        assertThat(move).isEqualTo(POUND);
    }

    @Test
    public void testExtractMoveGivenKarateChopShouldReturnMoveKarateChop() {
        Move move = extractor.ExtractMove(KARATE_CHOP.getName());
        assertThat(move).isEqualTo(KARATE_CHOP);
    }

    @Test
    public void testExtractMoveGivenContestUnknownShouldReturnValidMove() {
        Move move = extractor.ExtractMove(FUSION_BOLT.getName());
        assertThat(move).isEqualTo(FUSION_BOLT);
    }

    @Test
    public void testExtractMovesShouldReturnListOfMovesGivenAHTMLDocument() throws Exception {
        List<Move> moves = extractor.ExtractMoves();
        assertThat(moves).containsExactly(POUND, KARATE_CHOP, FUSION_BOLT);
    }

    @Test
    public void testMovePoundShouldHaveGoodStatistics() throws Exception {
        Move move = extractor.ExtractMove(POUND.getName());

        assertThat(move.getName()).isEqualTo(POUND.getName());
        assertThat(move.getType()).isEqualTo(POUND.getType());
        assertThat(move.getCategory()).isEqualTo(POUND.getCategory());
        assertThat(move.getContest()).isEqualTo(POUND.getContest());
        assertThat(move.getPower()).isEqualTo(POUND.getPower());
        assertThat(move.getAccuracy()).isEqualTo(POUND.getAccuracy());
        assertThat(move.getPP()).isEqualTo(POUND.getPP());
    }

    private String newHtml(String title, String bodyHtml) {
        return "<html><head><title>" + title + "</title></head><body>" + bodyHtml + "</body></html>";
    }

    private String newTable(String content) {
        return "<table>\n" + "<tr><th>Test</th></tr>\n" + content + "\n</table>";
    }
}
