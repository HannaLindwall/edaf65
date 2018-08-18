package com.company;

public class DownloadRunnable implements Runnable {
    private PDFDownloader pdfDownloader;
    public DownloadRunnable(PDFDownloader pdfDownloader) {
        this.pdfDownloader = pdfDownloader;
    }
    public void run(){
        while(pdfDownloader.hasPDF()) {
            String url = pdfDownloader.getPDF();
            pdfDownloader.downloadPdf(url);
        }
    }
}
