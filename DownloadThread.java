package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DownloadThread extends Thread{
    private PDFDownloader pdfDownloader;
    public DownloadThread(PDFDownloader pdfDownloader) {
        this.pdfDownloader = pdfDownloader;
    }

    public void run() {
        while(pdfDownloader.hasPDF()) {
            String url = pdfDownloader.getPDF();
            pdfDownloader.downloadPdf(url);
        }
    }
}
