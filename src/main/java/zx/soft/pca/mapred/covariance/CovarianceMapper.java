package zx.soft.pca.mapred.covariance;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.clustering.spectral.common.IntDoublePairWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class CovarianceMapper extends Mapper<IntWritable, VectorWritable, IntWritable, IntDoublePairWritable> {

	private int cardinality;
	private int vectorCount;

	private final IntWritable outputKey = new IntWritable();
	private final IntDoublePairWritable outputValue = new IntDoublePairWritable();

	@Override
	protected void setup(Context context) throws IOException {
		Configuration conf = context.getConfiguration();

		this.cardinality = conf.getInt(CovarianceJobOptions.CARDINALITY, 0);
		this.vectorCount = conf.getInt(CovarianceJobOptions.VECTOR_COUNT, 1);
	}

	@Override
	public void map(IntWritable key, VectorWritable value, Context context) throws IOException, InterruptedException {
		Vector vector = value.get();

		for (int i = 0; i < this.cardinality; ++i) {
			for (int j = 0; j < this.cardinality; ++j) {
				this.outputKey.set(i);
				this.outputValue.setKey(j);
				this.outputValue.setValue(vector.get(i) * vector.get(j) / this.vectorCount);

				context.write(this.outputKey, this.outputValue);
			}
		}
	}

}