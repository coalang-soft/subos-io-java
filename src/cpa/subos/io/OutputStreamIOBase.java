package cpa.subos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OutputStreamIOBase extends IOBaseImpl<InputStreamIOBase>{

	private OutputStream stream;

	public OutputStreamIOBase(OutputStream s){
		this.stream = s;
	}
	
	@Override
	public InputStream reader() throws IOException {
		throw new UnsupportedOperationException("OutputStreamIOBase does not allow reading!");
	}

	@Override
	public OutputStream writer() throws IOException {
		return stream;
	}

}
