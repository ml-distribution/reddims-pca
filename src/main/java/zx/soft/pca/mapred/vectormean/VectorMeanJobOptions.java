package zx.soft.pca.mapred.vectormean;

import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;

public class VectorMeanJobOptions {

	public static final String NUM_ROWS = "numRows";
	public static final String NUM_COLS = "numCols";

	public static DefaultOptionBuilder numRowsOption() {
		return new DefaultOptionBuilder().withLongName(NUM_ROWS).withRequired(true).withShortName("nr")
				.withArgument(new ArgumentBuilder().withName(NUM_ROWS).withMinimum(1).withMaximum(1).create())
				.withDescription("Number of rows of the input matrix");
	}

	public static DefaultOptionBuilder numColsOption() {
		return new DefaultOptionBuilder().withLongName(NUM_COLS).withRequired(true).withShortName("nc")
				.withArgument(new ArgumentBuilder().withName(NUM_COLS).withMinimum(1).withMaximum(1).create())
				.withDescription("Number of columns of the input matrix");
	}

}
