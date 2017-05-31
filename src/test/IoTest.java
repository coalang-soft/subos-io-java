package test;

import cpa.subos.io.IO;
import cpa.subos.io.file.FileExtensionFilter;
import cpa.subos.io.file.FileIOBase;
import io.github.coalangsoft.lib.data.Func;

import java.io.IOException;

import static cpa.subos.io.IO.*;

public class IoTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		IO.file("./").listFilesDeep().filter(new FileExtensionFilter("java")).forEach(new Func<FileIOBase, Void>() {
			@Override
			public Void call(FileIOBase fileIOBase) {
				System.out.println(fileIOBase.getPath());
				return null;
			}
		});
	}

}
