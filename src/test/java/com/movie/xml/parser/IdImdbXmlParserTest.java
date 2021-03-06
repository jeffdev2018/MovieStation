package com.movie.xml.parser;

import com.movie.jaxb.document.IdImdbDocument;
import com.movie.xml.utils.ResponseUtils;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class IdImdbXmlParserTest {

    @Test
    public void parseDocument() {
        ImdbXmlParser parser = new IdImdbXmlParser();
        IdImdbDocument expectedDoc = ResponseUtils.createIdImdbDocument();
        String xmlResponse = ResponseUtils.convertToString(expectedDoc);
        List<IdImdbDocument> actualDocsList = parser.parse(ResponseUtils.convertToDocument(xmlResponse));
        assertTrue(actualDocsList.size() == 1);
        assertEquals(expectedDoc, actualDocsList.get(0));
    }
    
    @Ignore(value = "Not implemented yet")
    @Test
    public void parseMalformedDocument() {
        
    }

}
