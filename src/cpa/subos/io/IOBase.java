package cpa.subos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOBase<T extends IOBase<T>> {
	
	int[] stream() throws IOException;
	T downloadFrom(IOBase<?> other) throws IOException;
	InputStream reader() throws IOException;
	OutputStream writer() throws IOException;
	T writeString(String s) throws IOException;
	T writeString(String s, String charset) throws IOException;
	String buildString() throws IOException;
	String buildString(String charset) throws IOException;
	void write(int i) throws IOException;
	int read() throws IOException;
	
}
