package zx.soft.pca.example.covmatrixevd;

import Jama.Matrix;

/**
 * EVD结果数据模型
 *
 * @author wanggang
 *
 */
public class EVDResult {

	public Matrix d;
	public Matrix v;

	public EVDResult(Matrix d, Matrix v) {
		this.d = d;
		this.v = v;
	}

}
