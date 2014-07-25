package zx.soft.pca.mapred.covariance;

import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;

public class CovarianceJobOptions {

	public static final String CARDINALITY = "cardinality";
	public static final String VECTOR_COUNT = "vectorCount";
	public static final String MEAN_VECTOR = "meanVector";

	public static DefaultOptionBuilder cardinalityOption() {
		return new DefaultOptionBuilder().withLongName(CARDINALITY).withRequired(true).withShortName("c")
				.withArgument(new ArgumentBuilder().withName(CARDINALITY).withMinimum(1).withMaximum(1).create())
				.withDescription("cardinality of the given vector set.");
	}

	public static DefaultOptionBuilder vectorCountOption() {
		return new DefaultOptionBuilder().withLongName(VECTOR_COUNT).withRequired(true).withShortName("n")
				.withArgument(new ArgumentBuilder().withName(VECTOR_COUNT).withMinimum(1).withMaximum(1).create())
				.withDescription("number of vectors in the given vector set.");
	}

}
