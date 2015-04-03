package zx.soft.pca.example.covmatrixevd;

import zx.soft.pca.example.Assume;
import Jama.Matrix;

/**
 * 基本的协方差矩阵特征值分解
 *
 * @author wanggang
 *
 */
public class EVDBased implements CovarianceMatrixEVDCalculator {

	@Override
	public EVDResult run(Matrix centeredData) {
		Matrix cov = calculateCovarianceMatrixOfCenteredData(centeredData);
		EVD evd = new EVD(cov);
		return new EVDResult(evd.d, evd.v);
	}

	/**
	 * 计算协方差矩阵，假设数据矩阵已经中心化了
	 * 例如，对于第i列: x_i' = x_i - E(x_i)
	 */
	public static Matrix calculateCovarianceMatrixOfCenteredData(Matrix data) {

		Assume.assume(data.getRowDimension() > 1, "Number of data samples is " + data.getRowDimension()
				+ ", but it has to be >1 to compute " + "covariances");
		int dimsNo = data.getColumnDimension();
		int samplesNo = data.getRowDimension();
		Matrix m = new Matrix(dimsNo, dimsNo);
		for (int r = 0; r < dimsNo; r++)
			for (int c = r; c < dimsNo; c++) {
				double sum = 0;
				for (int i = 0; i < samplesNo; i++)
					sum += data.get(i, r) * data.get(i, c);
				m.set(r, c, sum / (samplesNo - 1));
			}
		for (int r = 0; r < dimsNo; r++)
			for (int c = 0; c < r; c++)
				m.set(r, c, m.get(c, r));

		return m;
	}

}
