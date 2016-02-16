package com.sphericalelephant.zeitgeistng.util;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Taken and adjusted from: https://github.com/TheFinestArtist/YouTubePlayerActivity/blob/master/library/src/main/java/com/thefinestartist/ytpa/utils/YoutubeUrlParser.java
 * MIT License
 */
public class YouTubeUrlParser {
	private static YouTubeUrlParser INSTANCE;

	private YouTubeUrlParser() {
	}

	// (?:youtube(?:-nocookie)?\.com\/(?:[^\/\n\s]+\/\S+\/|(?:v|e(?:mbed)?)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})
	final static String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";

	public synchronized static YouTubeUrlParser getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new YouTubeUrlParser();
		}
		return INSTANCE;
	}

	public String getVideoId(@NonNull String videoUrl) {
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(videoUrl);

		if (matcher.find())
			return matcher.group(1);
		return null;
	}

	public String getVideoUrl(@NonNull String videoId) {
		return "http://youtu.be/" + videoId;
	}
}