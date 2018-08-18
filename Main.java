package com.company;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String url2 = "172.217.21.163";
        String url3 = "http://cs.lth.se/edaf65/foerelaesningar/?no_cache=1";
        String type = "exe";
        if(args.length > 0) {
            url3 = args[0];
            if(args.length > 1) {
                type = args[1];
            }
        }

        //InetAddress addr = InetAddress.getLocalHost();
        //System.out.println("Local address: " + addr);
        //InetAddress addr2 = InetAddress.getByName(url);
        //System.out.println("Address: " + addr2);
        String page = buildPage(url3);

        List<String> aLinks = getALinks(page);

        List<String> links = getPdfLinks(aLinks);

        checkFilesExist(links);

        PDFDownloader pdfDownloader = new PDFDownloader(links, type);

    }

    public static String buildPage(String url) {
        String page = "";
        try {
            URL u1 = new URL(url);
            InputStream in = u1.openStream();
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = in.read()) != -1) {
                builder.append((char) c);
                //System.out.print((char) c);
            }
            page = builder.toString();
            in.close();

        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }

        return page;
    }

    public static List<String> getALinks(String page) {
        String aRegex = "<a(.*?)/a>";
        Pattern aPattern = Pattern.compile(aRegex);
        Matcher aMatcher = aPattern.matcher(page);

        String hrefRegex = "href=\"(.*?)\"";
        Pattern hrefPattern = Pattern.compile(hrefRegex);
        //System.out.println(page);
        Set setLinks = new HashSet<String>();
        while (aMatcher.find()) {
            String aLink = aMatcher.group();
            //System.out.println("Group: " + aMatcher.group());

            Matcher hrefMatcher = hrefPattern.matcher(aLink);
            while (hrefMatcher.find()) {
                String href = hrefMatcher.group();
                String hrefLink = href.substring(6, href.length()-1);
                //System.out.println(hrefLink);
                setLinks.add(hrefLink);
            }
        }
        List<String> arrayLinks = new ArrayList<String>(setLinks);
        return arrayLinks;
    }

    public static List<String> getPdfLinks(List<String> aLinks) {
        List<String> links = new ArrayList<String>();
        for (String link : aLinks) {
            if (link.endsWith(".pdf")) {
                links.add(link);
            }
        }
        return  links;
    }

    public static void checkFilesExist(List<String> links) {
        for(int i = 0; i < links.size(); i++) {

            File f = new File(Integer.toString(i) + "file.pdf");
            if(f.isFile()) {
                //System.out.println("file exists");
                if(f.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
            }
        }
    }
}
