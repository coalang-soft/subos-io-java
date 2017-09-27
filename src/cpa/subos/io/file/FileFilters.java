package cpa.subos.io.file;

public interface FileFilters {

	FileExtensionFilter PICTURE = new FileExtensionFilter(
		"jpg","jpeg","gif","png","bmp"
	);
	
	FileExtensionFilter AUDIO = new FileExtensionFilter(
		"mp3","wav","midi"
	);

	FileExtensionFilter VIDEO = new FileExtensionFilter(
		"mp4"
	);
	
}
