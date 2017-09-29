package cpa.subos.io.file.nio;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NioUtil {
	
	public static Path path(URL u) throws URISyntaxException{
		return Paths.get(u.toURI().getPath());
	}
	
}
