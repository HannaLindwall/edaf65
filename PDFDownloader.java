package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PDFDownloader {
    private LinkedList<String> pdfLinks;

    public PDFDownloader(List<String> links, String type) {
        LinkedList<String> pdfs = new LinkedList<String>();
        pdfs.addAll(links);
        this.pdfLinks = pdfs;

        switch (type) {
            case "default":
                DownloadLinks downloadLinks = new DownloadLinks(links);
                downloadLinks.downloadLinks();
                break;
            case "thread":
                DownloadThread downloadLinksThread1 = new DownloadThread(this);
                DownloadThread downloadLinksThread2 = new DownloadThread(this);
                downloadLinksThread1.start();
                downloadLinksThread2.start();
                break;
            case "runnable":
                DownloadRunnable downloadRunnable1 = new DownloadRunnable(this);
                DownloadRunnable downloadRunnable2 = new DownloadRunnable(this);
                downloadRunnable1.run();
                downloadRunnable2.run();
                break;
            case "exe":
                ExecutorService exe = Executors.newFixedThreadPool(5);
                //DownloadRunnable downloadRunnable3 = new DownloadRunnable(this);
                //DownloadRunnable downloadRunnable4 = new DownloadRunnable(this);
                //exe.submit(downloadRunnable3);
                //exe.submit(downloadRunnable4);
                for(String url: pdfs) {
                    DownloadExe downloadExe = new DownloadExe(url);
                    exe.submit(downloadExe);
                }
                exe.shutdown();
                break;
            default:
                System.out.println("Incorrect mode: " + type);
        }
    }

    public synchronized boolean hasPDF() {
        return !pdfLinks.isEmpty();
    }

    public synchronized String getPDF() {
        return pdfLinks.poll();
    }


    public static void downloadPdf(String pdf) {
        try {
            System.out.println("opening connection");
            URL url = new URL(pdf);
            InputStream in = url.openStream();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            FileOutputStream fos = new FileOutputStream(new File( timestamp.toString() + ".pdf"));

            System.out.println("reading from resource and writing to file...");
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
