package com.sulvic.net;

import static java.net.HttpURLConnection.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import com.sulvic.util.StringHelper;

public class SulvicNet{
	
	private static final String PROTOCOL = "http://", SECURE_PROTOCOL = "https://";
	
	private static boolean asciiPrintable(char ch){ return ch >= 0x20 && ch <= 0x7F; }
	
	private static boolean hasProtocol(String url){ return url.contains(PROTOCOL) || hasSecureProtocol(url); }
	
	private static boolean hasSecureProtocol(String url){ return url.contains(SECURE_PROTOCOL); }
	
	public static String sanitizeLink(String url){
		if(url == null) return null;
		String result = "";
		for(char ch: url.toCharArray()) result += asciiPrintable(ch)? ch: '.';
		return result;
	}
	
	public static String secureLink(String url){ return sanitizeLink(hasProtocol(url)? hasSecureProtocol(url)? url: url.replace(PROTOCOL, PROTOCOL): SECURE_PROTOCOL + url); }
	
	public static String githubGit(String user, String repoName){ return secureLink("github.com/" + user + '/' + repoName + ".git"); }
	
	public static String githubRaw(String user, String repoName, String path){ return "raw.githubusercontent.com/" + user + '/' + repoName + '/' + path; }
	
	public static String pastebinRaw(String rawTag){ return secureLink("pastebin.com/raw/" + rawTag); }
	
	public static HttpURLConnection findContent(String link, String cookies) throws IOException{
		URL url = new URL(secureLink(link));
		InetAddress netAddr = InetAddress.getByName(url.getHost());
		if(netAddr.isAnyLocalAddress() || netAddr.isLoopbackAddress() || netAddr.isLinkLocalAddress())
			throw new IOException("The given link is either a local address or a loopback address");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		if(!StringHelper.isNullOrEmpty(cookies)) conn.addRequestProperty("Cookie", cookies);
		conn.connect();
		int status = conn.getResponseCode();
		boolean redir = false;
		if(status != HTTP_OK) redir = status == HTTP_MOVED_PERM || status == HTTP_MOVED_TEMP || status == HTTP_SEE_OTHER;
		if(redir){
			String newLink = conn.getHeaderField("Location"), cookies1 = conn.getHeaderField("Set-Cookie");
			conn.disconnect();
			conn = findContent(newLink, cookies1);
		}
		return conn;
	}
	
}
