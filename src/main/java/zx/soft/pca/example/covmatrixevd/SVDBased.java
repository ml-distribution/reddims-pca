package zx.soft.pca.example.covmatrixevd;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 * 基于SVD的协方差矩阵特征值分解
 *
 * @author wanggang
 *
 */
public class SVDBased implements CovarianceMatrixEVDCalculator {

	@Override
	public EVDResult run(Matrix centeredData) {

		int m = centeredData.getRowDimension();
		int n = centeredData.getColumnDimension();
		SingularValueDecomposition svd = centeredData.svd();
		double[] singularValues = svd.getSingularValues();
		Matrix d = Matrix.identity(n, n);
		for (int i = 0; i < n; i++) {
			/** TODO: 根据SVD特性这里是正确的 **/
			double val;
			if (i < m)
				val = singularValues[i];
			else
				val = 0;

			d.set(i, i, 1.0 / (m - 1) * Math.pow(val, 2));
		}
		Matrix v = svd.getV();

		return new EVDResult(d, v);
	}

}
