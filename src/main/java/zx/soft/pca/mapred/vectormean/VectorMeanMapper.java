package zx.soft.pca.mapred.vectormean;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.clustering.spectral.common.IntDoublePairWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class VectorMeanMapper extends Mapper<IntWritable, VectorWritable, NullWritable, IntDoublePairWritable> {

	private final IntDoublePairWritable outputValue = new IntDoublePairWritable();

	@Override
	public void map(IntWritable key, VectorWritable value, Context context) throws IOException, InterruptedException {
		Vector vector = value.get();

		for (Vector.Element e : vector.nonZeroes()) {
			this.outputValue.setKey(e.index());
			this.outputValue.setValue(e.get());

			context.write(NullWritable.get(), this.outputValue);
		}
	}

}