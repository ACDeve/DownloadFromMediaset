package com.acdeve;


import com.acdeve.extractors.Extractors;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class DownloadMediasetSeriesMain {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        HashMap<Integer,String> episodeMapping = Extractors.extractListOfEpisodes("C:\\Users\\capor\\git\\LocalProjects\\DownloadFromMediaset\\src\\main\\resources\\TerraAmara.xml");

        episodeMapping.forEach((episodeNumber,guid)->{
            try {

                new Thread(new Downloader(Extractors.extractVideoUrl(guid), episodeNumber)).start();

            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
