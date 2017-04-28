package cpa.subos.io.file;

import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

import java.io.File;
import java.util.ArrayList;

public class DirectoryImpl implements Directory{

	private File dir;

	public DirectoryImpl(File f){
		if(!f.isDirectory()){
			throw new IllegalStateException(f + " is no directory!");
		}
		this.dir = f;
	}
	
	public BasicSequence<File> listFiles() {
		return new BasicSequence<File>(File.class, dir.listFiles());
	}

	public BasicSequence<File> listFilesDeep() {
		final ArrayList<File> files = new ArrayList<File>();
		listFiles().forEach(new Func<File, Void>() {
			public Void call(File p) {
				if(p.isFile()){
					files.add(p);
				}else if(p.isDirectory()){
					files.addAll(
						new DirectoryImpl(p).listFilesDeep().asList()
					);
				}
				return null;
			}
		});
		return new BasicSequence<File>(File.class, files.toArray(new File[0]));
	}

	@Override
	public Directory createDirectory() {
		dir.mkdirs();
		return this;
	}

}
