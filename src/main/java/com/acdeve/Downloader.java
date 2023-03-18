package com.acdeve;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Downloader implements Runnable{

    String basePath = "D:\\TerraAmara\\";
    URL url;

    String fileName;

    Downloader(String url, Integer episodeNumber) throws MalformedURLException {
        this.url = new URL(url);
        this.fileName = basePath + episodeNumber + ".mp4";
    }

    @Override
    public void run() {
        ReadableByteChannel readableByteChannel = null;
        try {

            readableByteChannel = Channels.newChannel(url.openStream());

            FileOutputStream fileOutputStream = null;

            fileOutputStream = new FileOutputStream(fileName);
            FileChannel fileChannel = fileOutputStream.getChannel();

            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            fileOutputStream.close();

            System.out.println(fileName + " has completly downloaded!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
