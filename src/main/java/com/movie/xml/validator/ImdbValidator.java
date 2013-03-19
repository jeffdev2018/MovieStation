package com.movie.xml.validator;

import com.movie.errors.MovieException;
import com.movie.xml.parser.ImdbXmlParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Aloren
 */
public abstract class ImdbValidator {

    protected final DocumentBuilder documentBuilder;

    public ImdbValidator() {
        this.documentBuilder = tryCreateDocumentBuilder();
    }

    public abstract Document validate(String responseToValidate) throws MovieException;

    protected final DocumentBuilder tryCreateDocumentBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = factory.newDocumentBuilder();
            docBuilder.setErrorHandler(new MyErrorHandler());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ImdbXmlParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new MovieException("Problem initializing DocumentBuilder.", ex);
        }
        return docBuilder;
    }

    private static class MyErrorHandler implements ErrorHandler {

        public MyErrorHandler() {
        }

        @Override
        public void warning(SAXParseException saxpe) throws SAXException {
//            System.out.println("Warning: " + saxpe.getMessage());
        }

        @Override
        public void error(SAXParseException saxpe) throws SAXException {
            throw saxpe;
        }

        @Override
        public void fatalError(SAXParseException saxpe) throws SAXException {
            throw saxpe;
        }
    }
}
