package cpa.subos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class BufferIOBase extends IOBaseImpl<BufferIOBase> {

	private ArrayList<Integer> buffer = new ArrayList<Integer>();
	
	public InputStream reader() throws IOException {
		return new InputStream() {
			
			int dex = 0;
			
			@Override
			public int read() throws IOException {
				int res = -1;
				if(dex < buffer.size()){
					res = buffer.get(dex);
					dex++;
				}
				return res;
			}
		};
	}

	public OutputStream writer() throws IOException {
		return new OutputStream() {
			
			@Override
			public void write(int i) throws IOException {
				buffer.add(i);
			}
			
		};
	}

}
