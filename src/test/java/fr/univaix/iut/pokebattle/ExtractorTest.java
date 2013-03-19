package fr.univaix.iut.pokebattle;


import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ExtractorTest {
    @Test
    public void testExtractAttackKebabShouldReturnNull() {
        Extractor extractor = new Extractor();
        assertThat(extractor.ExtractAttack("Kebab")).isNull();
    }
}
