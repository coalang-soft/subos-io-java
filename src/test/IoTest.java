package test;

import cpa.subos.io.IO;
import cpa.subos.io.file.FileExtensionFilter;
import cpa.subos.io.file.FileIOBase;
import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.sequence.basic.BasicSequence;

import java.io.File;
import java.io.IOException;

import static cpa.subos.io.IO.*;

public class IoTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileIOBase.roots().at(0).forEach((f) -> {
			System.out.println(f);
		});
	}

}
