package cpa.subos.io.file;

import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

public interface Directory {

	public BasicSequence<FileIOBase> listFiles();
	public BasicSequence<FileIOBase> listFilesDeep();
	public Directory createDirectory();

}
