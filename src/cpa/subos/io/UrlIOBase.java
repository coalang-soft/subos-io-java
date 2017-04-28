package cpa.subos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class UrlIOBase extends IOBaseImpl<UrlIOBase> {

	private URL url;

	public UrlIOBase(URL url){
		this.url = url;
	}
	
	public InputStream reader() throws IOException {
		return url.openStream();
	}

	public OutputStream writer() throws IOException {
		throw new UnsupportedOperationException("Unable to write to URL");
	}

}
