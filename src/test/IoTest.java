package test;

import java.io.IOException;

import static cpa.subos.io.IO.*;

public class IoTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		file("test.html")
				.downloadFrom(
						buffer().writeString("abc")
						.downloadFrom(
							url("https://www.google.com")
						)
						.writeString("End")
					);
	}

}
