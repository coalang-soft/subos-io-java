package cpa.subos.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;

import cpa.subos.io.IOBaseImpl;
import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.log.TimeLogger;
import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

public class FileIOBase extends IOBaseImpl<FileIOBase> implements Directory{

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

	public FileIOBase getParent(){
		return new FileIOBase(file.getParentFile());
	}

	public FileIOBase createFile() throws IOException {
		file.createNewFile();
		return this;
	}

	public String getPath(){
		return file.getAbsolutePath();
	}

	public void remove(){
		if(isDirectory()) {
			listFiles().forEach(new Func<FileIOBase, Void>() {
				@Override
				public Void call(FileIOBase fileIOBase) {
					fileIOBase.remove();
					return null;
				}
			});
		}
		file.delete();
	}

	public BasicSequence<FileIOBase> listFiles() {
		File[] list = file.listFiles();
		if(list == null){
			return new BasicSequence<FileIOBase>(FileIOBase.class);
		}
		FileIOBase[] wrap = new FileIOBase[list.length];
		for(int i = 0; i < list.length; i++){
			wrap[i] = new FileIOBase(list[i]);
		}
		return new BasicSequence<FileIOBase>(FileIOBase.class, wrap);
	}

	public BasicSequence<FileIOBase> listFilesDeep() {
		final ArrayList<FileIOBase> files = new ArrayList<FileIOBase>();
		listFiles().forEach(new Func<FileIOBase, Void>() {
			public Void call(FileIOBase p) {
				if(p.file.isFile()){
					files.add(p);
				}else if(p.file.isDirectory()){
					files.addAll(
							new FileIOBase(p.file).listFilesDeep().asList()
					);
				}
				return null;
			}
		});
		return new BasicSequence<FileIOBase>(FileIOBase.class, files.toArray(new FileIOBase[0]));
	}

	@Override
	public FileIOBase createDirectory() {
		file.mkdirs();
		return this;
	}

	public void copy(FileIOBase dest) throws IOException {
		copy0(file, dest.file);
	}

	private static void copy0(File src, File dest)
			throws IOException{

		if(src.isDirectory()){

			//if directory not exists, create it
			if(!dest.exists()){
				dest.mkdir();
				System.out.println("Directory copied from "
						+ src + "  to " + dest);
			}

			//list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				//construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				//recursive copy
				copy0(srcFile,destFile);
			}

		}else{
			//if file, then copy it
			//Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			//copy the file content in bytes
			while ((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	public boolean isFile(){
		return file.isFile();
	}

	public boolean isDirectory(){
		return file.isDirectory();
	}

	public FileIOBase child(String name){
		return new FileIOBase(new File(file, name));
	}

}
