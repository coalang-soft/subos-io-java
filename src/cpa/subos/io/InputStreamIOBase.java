package cpa.subos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamIOBase extends IOBaseImpl<InputStreamIOBase>{

	private InputStream stream;

	public InputStreamIOBase(InputStream s){
		this.stream = s;
	}
	
	@Override
	public InputStream reader() throws IOException {
		return stream;
	}

	@Override
	public OutputStream writer() throws IOException {
		throw new UnsupportedOperationException("InputStreamIOBase does not allow writing!");
	}

}
