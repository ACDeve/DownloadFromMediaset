package com.acdeve;


import com.acdeve.extractors.Extractors;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloadMediasetSeriesMain {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        HashMap<Integer,String> episodeMapping = Extractors.extractListOfEpisodes("C:\\Users\\capor\\git\\LocalProjects\\DownloadFromMediaset\\src\\main\\resources\\TerraAmara.xml");

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(14);

        episodeMapping.forEach((episodeNumber,guid)->{
            try {

                executor.execute(new Downloader(Extractors.extractVideoUrl(guid), episodeNumber));

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
