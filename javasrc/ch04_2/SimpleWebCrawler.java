package javasrc.ch04_2;

/*
* A simple barebone web crawler 
*/

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javasrc.ch01_3.LinkedListQueue;

import lib.*;

public class SimpleWebCrawler {

    private HashSet<String> urls;

    public SimpleWebCrawler(String rootUrl, int maxNumber) {
        this.urls = new HashSet<>();

        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enqueue(rootUrl);
        urls.add(rootUrl);
        
        while (!queue.isEmpty() && this.urls.size() <= maxNumber) {
            String url = queue.dequeue();
            // StdOut.println(url);
            try {
                In in = new In(url);
                String page = in.readAll();

                String regex = "http(s)?://(\\w+\\.)+(\\w+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(page);
                while (matcher.find()) {
                    String newUrl = matcher.group();
                    // StdOut.println(newUrl);
                    if (!urls.contains(newUrl)) {
                        urls.add(newUrl);
                        queue.enqueue(newUrl);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Iterable<String> getUrls(){
        return this.urls;
    }

    public static void main(String[] args) {
        var webc = new SimpleWebCrawler("http://www.sbcc.edu", 100);
        for(String url: webc.getUrls()){
            StdOut.println(url);
        }
    }   
}