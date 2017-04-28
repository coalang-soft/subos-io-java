package cpa.subos.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import cpa.subos.io.file.FileIOBase;

public class IO {

	public static FileIOBase file(File f){
		return new FileIOBase(f);
	}
	
	public static FileIOBase file(String p){
		return file(new File(p));
	}
	
	public static BufferIOBase buffer(){
		return new BufferIOBase();
	}
	
	public static UrlIOBase url(URL url){
		return new UrlIOBase(url);
	}
	
	public static UrlIOBase url(String url) throws MalformedURLException{
		return url(new URL(url));
	}
	
	public static InputStreamIOBase stream(InputStream s){
		return new InputStreamIOBase(s);
	}
	
	public static OutputStreamIOBase stream(OutputStream s){
		return new OutputStreamIOBase(s);
	}
	
	public static BufferIOBase string(String s) throws IOException{
		BufferIOBase b = buffer();
		OutputStream w = b.writer();
		w.write(s.getBytes());
		return b;
	}
	
}
