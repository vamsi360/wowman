package repo;

import exception.ConnectionException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadThread implements Runnable {

    int threadId;
    Thread thread;
    URL url;
    String outputFolder;
    String outputFileName;
    int bufferSize;

    //start and end bytes of the to-be-downloaded parallel part.
    int startByte;
    int endByte;

    DownloadThread() {
        download();
    }

    DownloadThread (int threadId, URL url, String outputFileName, int startByte, int endByte, int bufferSize) {
        this.threadId = threadId;
        this.url = url;
        this.outputFileName = outputFileName;
        this.startByte = startByte;
        this.endByte = endByte;
        this.bufferSize = bufferSize;
    }

    public void download() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Utils.printlnData("Thread (" + threadId + ") started..");

        BufferedInputStream bufferedInputStream = null;
        RandomAccessFile outputFile = null;

        try {

            String proxy = "192.16.3.254";
            String port = "8080";
            Properties systemProperties = System.getProperties();
            systemProperties.setProperty("http.proxyHost", proxy);
            systemProperties.setProperty("http.proxyPort", port);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String byteRange = startByte + "-" + endByte;
            connection.setRequestProperty("Range", "bytes=" + byteRange);

            //connect.
            connection.connect();

            if (connection.getResponseCode() /100 != 2) {
                throw new ConnectionException("Error! Response message: " + connection.getResponseMessage());
            }

            //get the download input stream.
            bufferedInputStream = new BufferedInputStream(connection.getInputStream());

            //open the output-file for the download
            outputFile = new RandomAccessFile(outputFileName, "rw");
            outputFile.seek(startByte);

            byte data[] = new byte[bufferSize];
            int currData;

            while ( (currData = bufferedInputStream.read(data, 0, bufferSize)) != -1 ) {
                //Utils.printlnData("=>" + bufferSize + " bytes written..");
                outputFile.write(data, 0, currData);
            }

            connection.disconnect();
            Scheduler.updateDownloadedSize(bufferSize);
            Utils.printlnData("Thread ended..");

        } catch (MalformedURLException e) {
            Utils.printlnData(e.getMessage());
            Utils.printlnData (Utils.getStackTraceAsString(e));
        } catch (IOException e) {
            Utils.printlnData(e.getMessage());
            Utils.printlnData (Utils.getStackTraceAsString(e));
        } catch (ConnectionException e) {
            Utils.printlnData(e.getMessage());
            Utils.printlnData (Utils.getStackTraceAsString(e));
        }
        finally {
            if (outputFile != null) {
                try {
                    outputFile.close();
                } catch (IOException e) {
                    Utils.printlnData(e.getMessage());
                    Utils.printlnData(Utils.getStackTraceAsString(e));
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    Utils.printlnData(e.getMessage());
                    Utils.printlnData(Utils.getStackTraceAsString(e));
                }
            }
        }

    }

}
