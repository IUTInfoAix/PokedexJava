package fr.univaix.iut.pokebattle;

public class Extractor {
    final static String LIST_ATTACK_URL = "http://www.pokepedia.fr/index.php/Liste_des_attaques";

    public Attack ExtractAttack(String nomAttack) {
        if (nomAttack.equals("Charge"))
            return new Attack("Charge");
        return null;
    }

    public static void main(String[] args) {

    }
}
