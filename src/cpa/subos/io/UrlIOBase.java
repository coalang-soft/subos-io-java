package cpa.subos.io;

import io.github.coalangsoft.lib.data.Func;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlIOBase extends IOBaseImpl<UrlIOBase> {

	private URLConnection connection;

	private static Func<URLConnection, Object> readerFactory = new Func<URLConnection, Object>() {
		@Override
		public Object call(URLConnection c) {
			try {
				return c.getInputStream();
			} catch (IOException e) {
				return e;
			}
		}
	};

	public static void setReaderFactory(Func<URLConnection, Object> f){
		readerFactory = f;
	}
	public static Func<URLConnection, Object> getReaderFactory(){
		return readerFactory;
	}
	
	private URL url;

	public UrlIOBase(URL url){
		this.url = url;
	}
	
	public InputStream reader() throws IOException {
		initConnection();
		Object res = readerFactory.call(connection);
		if(res instanceof InputStream){
			return (InputStream) res;
		}else{
			throw (IOException) res;
		}
	}

	public OutputStream writer() throws IOException {
		initConnection();
		return connection.getOutputStream();
	}

	public String toString(){
		return url.toExternalForm();
	}

	private void initConnection() throws IOException {
		if(connection == null){
			connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
		}
	}

}
