package com.company;

import com.company.PDFDownloader.*;

public class DownloadExe implements Runnable{
    private String url;
    public DownloadExe(String url) {
        this.url = url;
    }
    public void run() {
        PDFDownloader.downloadPdf(url);
    }
}
