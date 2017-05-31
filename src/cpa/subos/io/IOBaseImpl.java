package cpa.subos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public abstract class IOBaseImpl<T extends IOBaseImpl<T>> implements IOBase<T>{
	
	public int[] stream() throws IOException {
		ArrayList<Integer> res = new ArrayList<Integer>();
		InputStream s = reader();
		int val = s.read();
		while(val != -1){
			res.add(val);
			val = s.read();
		}
		s.close();
		int[] ret = new int[res.size()];
		for(int i = 0; i < ret.length; i++){
			ret[i] = res.get(i);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public T downloadFrom(IOBase<?> other) throws IOException {
		InputStream in = other.reader();
		OutputStream out = writer();
		
		int val = in.read();
		while(val != -1){
			out.write(val);
			val = in.read();
		}
		
		in.close();
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T writeString(String s, String charset) throws IOException{
		OutputStream out = writer();
		out.write(s.getBytes(charset));
		out.close();
		return (T) this;
	}

	public T writeString(String s) throws IOException {
		return writeString(s,"ASCII");
	}

	public String buildString() throws IOException {
		return buildString("ASCII");
	}
	public String buildString(String charset) throws IOException {
		return new String(bytes(stream()), charset);
	}

	private byte[] bytes(int[] stream) {
		byte[] b = new byte[stream.length];
		for(int i = 0; i < b.length; i++){
			b[i]=(byte)stream[i];
		}
		return b;
	}

	public int read() throws IOException {
		return reader().read();
	}

	public void write(int i) throws IOException {
		writer().write(i);
	}

}
