package zx.soft.pca.example.covmatrixevd;

import Jama.Matrix;

/**
 * 根据给定的数据，计算协方差矩阵的特征值分解
 *
 * @author wanggang
 *
 */
public interface CovarianceMatrixEVDCalculator {

	/**
	 * 计算给定数据的协方差矩阵
	 * @param centeredData 数据矩阵，每行代表一个实例或者样本，列代表维度；该矩阵必须被中心化
	 */
	public EVDResult run(Matrix centeredData);

}
