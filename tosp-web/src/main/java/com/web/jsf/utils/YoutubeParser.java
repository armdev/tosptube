package com.web.jsf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author armenar
 */
public class YoutubeParser {

    public static void main(String args[]) {
        //String url = "https://www.youtube.com/watch?v=igcsNG4aruA";
        String url ="https://www.youtube.com/embed/Woq5iX9XQhA?html5=1";

        YoutubeParser.extractYTId(url);
    }

    public static String extractYTId(String ytUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if (matcher.find()) {
            System.out.println("matcher.group() " + matcher.group());
            return matcher.group();
        } else {
            return "error";
        }

    }

}
