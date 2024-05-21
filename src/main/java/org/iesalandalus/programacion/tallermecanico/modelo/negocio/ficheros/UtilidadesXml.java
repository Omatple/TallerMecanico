package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UtilidadesXml {

    private UtilidadesXml() {
        // Evito que se creen instancias.
    }

    public static void escribirDocumentoXml(Document documentoXml, String salida) {
        try (FileWriter ficheroSalida = new FileWriter(salida)) {
            TransformerFactory factoria = TransformerFactory.newInstance();
            factoria.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factoria.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer conversor = factoria.newTransformer();
            conversor.setOutputProperty(OutputKeys.INDENT, "yes");
            conversor.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            conversor.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StreamResult destino = new StreamResult(ficheroSalida);
            DOMSource fuente = new DOMSource(documentoXml);
            conversor.transform(fuente, destino);
            System.out.printf("Fichero %s escrito correctamente.%n", salida);
        } catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
            System.out.println("Imposible crear el conversor.");
        } catch (TransformerException e) {
            System.out.println("Error irrecuperable en la conversión.");
        } catch (IOException e) {
            System.out.printf("No se ha podido escribir el fichero %s.%n", salida);
        }
    }

    public static Document leerDocumentoXml(String ficheroXml) {
        Document documentoXml = null;
        try {
            DocumentBuilder constructor = crearConstructorDocumentoXml();
            if (constructor != null) {
                documentoXml = constructor.parse(ficheroXml);
                documentoXml.getDocumentElement().normalize();
            }
        } catch (SAXException e) {
            System.out.println("Documento XML mal formado.");
        } catch (IOException e) {
            System.out.printf("No se ha podido leer el fichero %s.%n", ficheroXml);
        }
        return documentoXml;
    }

    public static DocumentBuilder crearConstructorDocumentoXml() {
        DocumentBuilder constructor = null;
        try {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            constructor = fabrica.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.out.println("Error al crear el constructor.");
        }
        return constructor;
    }

}
