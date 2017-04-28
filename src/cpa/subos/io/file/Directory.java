package cpa.subos.io.file;

import java.io.File;

import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

public interface Directory {

	public BasicSequence<File> listFiles();
	public BasicSequence<File> listFilesDeep();
	public Directory createDirectory();

}
