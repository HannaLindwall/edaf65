package com.company;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class DownloadLinks {
    private List<String> links;
    public DownloadLinks(List<String> links) {
        this.links = links;
    }

    public void downloadLinks() {
        for(int i = 0; i < links.size(); i++) {
            try {
                System.out.println("opening connection");
                URL url = new URL(links.get(i));
                InputStream in = url.openStream();
                FileOutputStream fos = new FileOutputStream(new File( Integer.toString(i) + "file.pdf"));

                //System.out.println("reading from resource and writing to file...");
                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                //System.out.println("File downloaded");
            } catch (MalformedURLException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
