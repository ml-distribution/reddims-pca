package zx.soft.pca.mapred.covariance;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.clustering.spectral.common.IntDoublePairWritable;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class CovarianceReducer extends Reducer<IntWritable, IntDoublePairWritable, IntWritable, VectorWritable> {

	private int cardinality;
	private Vector meanVector;

	private final VectorWritable outputValue = new VectorWritable();

	@Override
	protected void setup(Context context) throws IOException {
		Configuration conf = context.getConfiguration();
		FileSystem fs = FileSystem.get(conf);

		this.cardinality = conf.getInt(CovarianceJobOptions.CARDINALITY, 0);

		URI[] archives = DistributedCache.getCacheArchives(conf);
		Path meanPath = new Path(archives[0]);
		try (SequenceFile.Reader reader = new SequenceFile.Reader(fs, meanPath, conf)) {
			IntWritable key = new IntWritable();
			VectorWritable val = new VectorWritable();
			reader.next(key, val);
			this.meanVector = val.get();
		}
	}

	@Override
	public void reduce(IntWritable key, Iterable<IntDoublePairWritable> values, Context context) throws IOException,
			InterruptedException {
		double[] sum = new double[this.cardinality];

		for (IntDoublePairWritable value : values) {
			int i = value.getKey();
			double v = value.getValue();

			sum[i] += v;
		}

		for (int i = 0; i < this.cardinality; ++i) {
			sum[i] -= this.meanVector.get(key.get()) * this.meanVector.get(i);
		}

		outputValue.set(new DenseVector(sum));
		context.write(key, outputValue);
	}

}