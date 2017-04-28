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
		out.close();
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T writeString(String s) throws IOException{
		OutputStream out = writer();
		for(int i = 0; i < s.length(); i++){
			out.write(s.charAt(i));
		}
		out.close();
		return (T) this;
	}

	public String buildString() throws IOException {
		int[] vals = stream();
		return new String(vals,0,vals.length);
	}

}
