package cpa.subos.io.file;

import java.io.File;

import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.sequence.AbstractSequence;
import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

public class FileExtensionFilter implements Func<FileIOBase, Boolean>{

	private AbstractSequence<String, ?> sequence;

	public FileExtensionFilter(AbstractSequence<String,?> s){
		this.sequence = s;
	}
	
	public FileExtensionFilter(String... extensions){
		this(new BasicSequence<String>(String.class, extensions));
	}
	
	public Boolean call(FileIOBase p) {
		String[] split = p.getPath().split("\\.");
		return sequence.contains(split[split.length - 1]);
	}

}
