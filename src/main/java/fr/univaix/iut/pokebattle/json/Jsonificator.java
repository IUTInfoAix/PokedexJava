package fr.univaix.iut.pokebattle.json;


import com.gistlabs.mechanize.MechanizeAgent;
import com.google.gson.Gson;
import fr.univaix.iut.pokebattle.extractor.Extractor;
import fr.univaix.iut.pokebattle.parser.MoveParserFactory;
import fr.univaix.iut.pokebattle.pokemon.Move;

import java.util.List;

public class Jsonificator {

    public String FromMoveListToJson(List<Move> moves) {
        Gson gson = new Gson();
        return gson.toJson(moves);
    }

    public String FromMoveToJson(Move move) {
        Gson gson = new Gson();
        return gson.toJson(move);
    }

    public static void main(String[] args) {
        MechanizeAgent agent = new MechanizeAgent();
        Extractor extractor = new Extractor(new MoveParserFactory(agent));
        System.out.println(new Jsonificator().FromMoveListToJson(extractor.ExtractMoves()));
    }

}
