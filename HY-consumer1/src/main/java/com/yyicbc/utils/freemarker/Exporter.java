package com.yyicbc.utils.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 *@author vic fu
 */
public interface Exporter {

	/**
	 * convert html to pdf
	 * @param htmlFile htmlfile path
	 * @param targetFile outputFile path
	 */
	void exportHtml(String htmlFile, String targetFile);

	void exportContent(String htmlContent, OutputStream stream);

	File exportContent(String htmlContent, String targetFile) throws IOException;
}
