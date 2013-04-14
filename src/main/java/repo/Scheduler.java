package repo;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */

import exception.ConnectionException;
import exception.NoFileInURLException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * DownloadThread scheduler
 */
public class Scheduler {

    static int currentDownloadedSize = 0;

    public void scheduleThreads () throws NoFileInURLException, IOException, ConnectionException {

        String outputFolder;
        String outputFileName;
        URL url;
        final int BUFFER_SIZE = 8192 * 1024;
        int downloadSize;

        outputFolder = "/home/subhash/Desktop/wowman/outputs/";
        //String strWebsite = "http://localhost/downloads/ic.pdf";
        //String strWebsite = "http://ignum.dl.sourceforge.net/project/jvi/archives/ancient/jbvi-0.6.4.jar";
        String strWebsite = "http://download.cdn.mozilla.net/pub/mozilla.org/firefox/releases/19.0.2/linux-i686/en-US/firefox-19.0.2.tar.bz2";
        String downloadFileName = strWebsite.substring(strWebsite.lastIndexOf("/") + 1, strWebsite.length());
        if (downloadFileName == null) {
            throw new NoFileInURLException("No file to download. Please check the URL of the download.");
        }
        outputFileName = outputFolder + downloadFileName;
        url = new URL(strWebsite);

        //set proxy first.
        String proxy = "192.16.3.254";
        String port = "8080";
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", proxy);
        systemProperties.setProperty("http.proxyPort", port);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connect.
        //connection.setChunkedStreamingMode(1024 * 1024* 14);
        int startByte = 0;
        int endByte = BUFFER_SIZE;
        String byteRange = startByte + "-" + endByte;
        connection.setRequestProperty("Range", "bytes=" + byteRange);
        connection.connect();

        Utils.printlnData("Response Code: " + connection.getResponseCode());
        if (connection.getResponseCode() /100 != 2) {
            throw new ConnectionException("Error! Response message: " + connection.getResponseMessage());
        }

        connection.disconnect();
        //downloadSize = connection.getContentLength();
        //Utils.printlnData("Total FileSize: " + downloadSize);
        downloadSize = 500 * 1024 * 1024;
        long totalThreads = downloadSize / BUFFER_SIZE;
        Utils.printlnData("Total threads: " + totalThreads);

        startByte = 0;
        endByte = BUFFER_SIZE;
        totalThreads = 5;
        int i = 0;
        for ( ; i<totalThreads-1; i++) {

            DownloadThread downloadThread = new DownloadThread(i, url, outputFileName, startByte, endByte, BUFFER_SIZE);
            downloadThread.download();

            startByte = endByte;
            endByte = endByte + BUFFER_SIZE;
        }
        endByte = downloadSize;

        DownloadThread downloadThread = new DownloadThread(i, url, outputFileName, startByte, endByte, BUFFER_SIZE);
        downloadThread.download();

        Utils.printlnData("Scheduling Done..");

    }

    public static synchronized void updateDownloadedSize(int size) {
        currentDownloadedSize += size;
        Utils.printlnData("=>Currently downloaded: " + currentDownloadedSize);
    }
}
