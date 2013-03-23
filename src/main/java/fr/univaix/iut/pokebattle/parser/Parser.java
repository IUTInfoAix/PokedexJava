package fr.univaix.iut.pokebattle.parser;

import com.gistlabs.mechanize.document.node.Node;

public interface Parser<T> {
    public T parse(Node node);
}
