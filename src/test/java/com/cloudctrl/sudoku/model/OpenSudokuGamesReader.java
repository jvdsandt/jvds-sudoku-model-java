package com.cloudctrl.sudoku.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by jan on 16-04-17.
 */
public class OpenSudokuGamesReader {

    protected DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        return dbFactory.newDocumentBuilder();
    }

    public List<SudokuGame> read(InputStream stream) throws IOException, SAXException, ParserConfigurationException {

        Document doc = getDocumentBuilder().parse(stream);
        doc.getDocumentElement().normalize();
        List<SudokuGame> result = new ArrayList<>();

        NodeList gameElems = doc.getElementsByTagName("game");
        for (int i = 0; i < gameElems.getLength(); i++) {
            Element gameElem = (Element) gameElems.item(i);

            result.add(createGame(gameElem.getAttribute("data")));

        }
        return result;
    }

    protected SudokuGame createGame(String numberLine) {
        return SudokuGameBuilder.newGameFromNumberLine(numberLine);
    }
}
