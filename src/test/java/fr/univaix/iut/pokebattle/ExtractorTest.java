package fr.univaix.iut.pokebattle;


import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ExtractorTest {
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
}
