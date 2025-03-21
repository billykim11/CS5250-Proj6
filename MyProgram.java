package myAssembler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class MyProgram {
	public static void main(String[] args) {
		// Exit with error if no source file specified.
		if (args.length == 0) {
			System.err.println("Provide a source file.");
			System.exit(1);
		}
		
		// Exit with error if invalid source file specified.
		File sourceFile = new File(args[0].trim());
		System.out.println(sourceFile);
		if (!sourceFile.exists()) {
			System.err.println("I can't find the source file.");
			System.exit(2);
		}
		
		// Get output file.
		String sourceAbsolutePath = sourceFile.getAbsolutePath();
		String fileName = sourceFile.getName();
		int fileNameExtensionIndex = fileName.lastIndexOf(".");
		String fileNameNoExtension =
				fileName.substring(0, fileNameExtensionIndex);
		int fileNameIndex =
				sourceFile.getAbsolutePath().indexOf(sourceFile.getName());
		String sourceDirectory = sourceAbsolutePath.substring(0, fileNameIndex);
		String outputFilePath =
				sourceDirectory + fileNameNoExtension + ".hack";
		File outputFile = new File(outputFilePath);
		
		try {
			// Create output file on disk.
			if (outputFile.exists()) {
				outputFile.delete();
			}
			outputFile.createNewFile();
			
			// Start timer.
			long startTime = System.currentTimeMillis();
			
			// Translate source file.
			Assembler assembler = new Assembler(sourceFile, outputFile);
			assembler.translate();
			
			// Stop timer.
			long endTime = System.currentTimeMillis();
			long elaspedTime = endTime - startTime;
			
			// Output success message if no errors occur.
			StringWriter status = new StringWriter();
			status.append("Translation completed successfully on ");
			status.append(sourceAbsolutePath);
			status.append(" ==> ");
			status.append(outputFilePath);
			status.append(" in ");
			status.append(Long.toString(elaspedTime));
			status.append("ms.");
			System.out.println(status.toString());
		} catch (IOException e) {
			// Exit with error if I/O error occurs.
			System.err.println("An unknown I/O error occurred.");
			System.exit(3);
		}
	}
}
