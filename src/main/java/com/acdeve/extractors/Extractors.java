package com.acdeve.extractors;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class Extractors {

    public static String extractVideoUrl(String guid) throws SAXException, ParserConfigurationException, IOException {

        String base_selector = "http://link.theplatform.eu/s/PR1GhC/media/guid/2702976343/[[GUID]]?mbr=true&formats=[[FORMATS]]&format=SMIL&assetTypes=HD,HBBTV,widevine,geoIT%7CgeoNo:HD,HBBTV,geoIT%7CgeoNo:HD,geoIT%7CgeoNo:SD,HBBTV,widevine,geoIT%7CgeoNo:SD,HBBTV,geoIT%7CgeoNo:SD,geoIT%7CgeoNo";

        if(guid.indexOf("FAFU") == 0){
            guid = guid.substring(guid.length()-6);
        }

        base_selector = base_selector.replace("[[GUID]]", guid).replace("[[FORMATS]]", "MPEG4");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(base_selector).openStream());

        return doc.getDocumentElement().getElementsByTagName("video").item(0).getAttributes().getNamedItem("src").getNodeValue();

    }

    public static HashMap<Integer, String> extractListOfEpisodes(String PathSeason) throws ParserConfigurationException, IOException, SAXException {

        HashMap<Integer, String> episodeMapping = new HashMap<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new FileInputStream("C:\\Users\\capor\\git\\LocalProjects\\DownloadFromMediaset\\src\\main\\resources\\TerraAmara.xml");
        Document doc = db.parse(is);

        NodeList a = doc.getDocumentElement().getElementsByTagName("a");

        for(int i = 0; i < a.getLength(); i++){

            String urlEpisode = a.item(i).getAttributes().getNamedItem("href").getNodeValue();
            String guid = urlEpisode.substring(urlEpisode.lastIndexOf("_") + 1);
            Integer episodeNumber = Integer.parseInt(urlEpisode.substring(urlEpisode.lastIndexOf("-") + 1 , urlEpisode.lastIndexOf("_")));
            episodeMapping.put(episodeNumber, guid);

        }

        return episodeMapping;

    }

}
