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
import java.util.Iterator;
import java.util.List;

import cpa.subos.io.IOBaseImpl;
import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.log.TimeLogger;
import io.github.coalangsoft.lib.pattern.AbstractMatcher;
import io.github.coalangsoft.lib.sequence.ISequence;
import io.github.coalangsoft.lib.sequence.basic.BasicModifiableSequence;
import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

public class FileIOBase extends IOBaseImpl<FileIOBase> implements Directory, ISequence<FileIOBase, BasicSequence<FileIOBase>>{

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
		forEach((f) -> {files.add(f); return null;});
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

	public String toString(){
		return file.getAbsolutePath();
	}

	@Override
	public BasicSequence<FileIOBase> filter(Func<FileIOBase, Boolean> condition) {
		ArrayList<FileIOBase> list = new ArrayList<>();
		forEach(condition, list::add);
		return new BasicSequence<FileIOBase>(FileIOBase.class, list.toArray(new FileIOBase[0]));
	}

	@Override
	public BasicSequence<FileIOBase> clone() {
		return listFilesDeep().clone();
	}

	@Override
	public FileIOBase[] toArray() {
		return listFilesDeep().toArray();
	}

	@Override
	public BasicSequence<FileIOBase> subSequence(int start, int end) {
		return listFilesDeep().subSequence(start,end);
	}

	@Override
	public BasicSequence<FileIOBase> subSequence(int start) {
		return listFilesDeep().subSequence(start);
	}

	@Override
	public FileIOBase[] getRaw() {
		return listFilesDeep().getRaw();
	}

	@Override
	public FileIOBase at(int index) {
		return listFilesDeep().at(0);
	}

	@Override
	public int length() {
		return listFilesDeep().length();
	}

	@Override
	public AbstractMatcher<FileIOBase> matcher(FileIOBase... toTest) {
		return listFilesDeep().matcher(toTest);
	}

	@Override
	public AbstractMatcher<FileIOBase> matcher(ISequence<FileIOBase, ?> toTest) {
		return listFilesDeep().matcher(toTest);
	}

	@Override
	public boolean contains(Object value) {
		return listFilesDeep().contains(value);
	}

	@Override
	public List<FileIOBase> asList() {
		return listFilesDeep().asList();
	}

	@Override
	public void forEach(Func<FileIOBase, ?> f) {
		listFiles().forEach((p) -> {
			if(p.file.isFile()){
				f.call(p);
			}else if(p.file.isDirectory()){
				new FileIOBase(p.file).forEach(f);
			}
			return null;
		});
	}

	@Override
	public <R> BasicModifiableSequence<R> forEach(Func<FileIOBase, R> f, Class<R> c) {
		return listFilesDeep().forEach(f,c);
	}

	@Override
	public BasicSequence<FileIOBase> sort() {
		return listFilesDeep().sort();
	}

	@Override
	public FileIOBase first(Func<FileIOBase, Boolean> rule) {
		return listFilesDeep().first(rule);
	}

	public static BasicSequence<FileIOBase> roots(){
		File[] rs = File.listRoots();
		FileIOBase[] r = new FileIOBase[rs.length];
		for(int i = 0; i < r.length; i++){
			r[i] = new FileIOBase(rs[i]);
		}
		return new BasicSequence<>(FileIOBase.class, r);
	}
	
	public long space(){
		return file.getUsableSpace();
	}

	@Override
	public BasicSequence<FileIOBase> sort(Func<FileIOBase, Long> f) {
		return listFilesDeep().sort(f);
	}

	@Override
	public Iterator<FileIOBase> iterator() {
		return listFilesDeep().iterator();
	}
}
