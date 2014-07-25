package zx.soft.pca.mapred.vectormean;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.clustering.spectral.common.IntDoublePairWritable;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.VectorWritable;

public class VectorMeanReducer extends Reducer<NullWritable, IntDoublePairWritable, IntWritable, VectorWritable> {

	private int numRows;
	private int numCols;

	private double[] sum;
	private final VectorWritable outputValue = new VectorWritable();

	@Override
	protected void setup(Context context) throws IOException {
		Configuration conf = context.getConfiguration();

		this.numRows = conf.getInt(VectorMeanJobOptions.NUM_ROWS, 1);
		this.numCols = conf.getInt(VectorMeanJobOptions.NUM_COLS, 1);
		this.sum = new double[this.numCols];
	}

	@Override
	public void reduce(NullWritable key, Iterable<IntDoublePairWritable> values, Context context) throws IOException,
			InterruptedException {
		for (IntDoublePairWritable value : values) {
			int i = value.getKey();
			this.sum[i] += value.getValue();
		}

		for (int i = 0; i < this.numCols; ++i) {
			this.sum[i] /= this.numRows;
		}

		this.outputValue.set(new DenseVector(this.sum));

		context.write(new IntWritable(0), this.outputValue);
	}

}