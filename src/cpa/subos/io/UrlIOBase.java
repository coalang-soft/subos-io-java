package cpa.subos.io;

import io.github.coalangsoft.lib.data.Func;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlIOBase extends IOBaseImpl<UrlIOBase> {

	private static Func<URL, Object> readerFactory = new Func<URL, Object>() {
		@Override
		public Object call(URL url) {
			try {
				URLConnection c = url.openConnection();
				c.setDoInput(true);
				return c.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
				return e;
			}
		}
	};

	private URL url;

	public UrlIOBase(URL url){
		this.url = url;
	}
	
	public InputStream reader() throws IOException {
		Object res = readerFactory.call(url);
		if(res instanceof InputStream){
			return (InputStream) res;
		}else{
			throw (IOException) res;
		}
	}

	public OutputStream writer() throws IOException {
		throw new UnsupportedOperationException("Unable to write to URL");
	}

	public String toString(){
		return url.toExternalForm();
	}

}
