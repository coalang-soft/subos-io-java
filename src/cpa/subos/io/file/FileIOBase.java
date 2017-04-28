package cpa.subos.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import cpa.subos.io.IOBaseImpl;

public class FileIOBase extends IOBaseImpl<FileIOBase>{

	private File file;

	public FileIOBase(File f) {
		this.file = f;
	}

	public InputStream reader() throws IOException {
		return new FileInputStream(file);
	}

	public OutputStream writer() throws FileNotFoundException {
		return new FileOutputStream(file);
	}

	public boolean exists(){
		return file.exists();
	}

	public String getName(){
		return file.getName();
	}

	public String toUrl() throws MalformedURLException {
		return file.toURI().toURL().toExternalForm();
	}

	public boolean hasParent(){
		return file.getParent() != null;
	}

	public Directory getParent(){
		return new DirectoryImpl(file.getParentFile());
	}

	public FileIOBase createFile() throws IOException {
		file.createNewFile();
		return this;
	}

	public String getPath(){
		return file.getPath();
	}

}
