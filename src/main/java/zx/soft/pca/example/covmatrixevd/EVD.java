package zx.soft.pca.example.covmatrixevd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * 特征值分解，根据特征值的降序将特征向量排序。
 * 矩阵中的特征值也是按照相同的方式排序，与R语言中的特征值重组方式一样。
 *
 * @author wanggang
 *
 */
public class EVD implements Serializable {

	private static final long serialVersionUID = 1L;

	public final Matrix d;
	public final Matrix v;

	public EVD(Matrix m) {
		EigenvalueDecomposition evd = m.eig();

		double[] diagonal = getDiagonal(evd.getD());
		PermutationResult result = calculateNondecreasingPermutation(diagonal);
		int[] permutation = result.permutation;
		double[] newDiagonal = result.values;
		this.v = permutateColumns(evd.getV(), permutation);
		this.d = createDiagonalMatrix(newDiagonal);
		assert eigenvaluesAreNonIncreasing(this.d);
	}

	private static Matrix createDiagonalMatrix(double[] diagonal) {
		Matrix m = new Matrix(diagonal.length, diagonal.length);
		for (int i = 0; i < diagonal.length; i++)
			m.set(i, i, diagonal[i]);
		return m;
	}

	private static double[] getDiagonal(Matrix m) {
		assert m.getRowDimension() == m.getColumnDimension();
		double[] diag = new double[m.getRowDimension()];
		for (int i = 0; i < m.getRowDimension(); i++)
			diag[i] = m.get(i, i);
		return diag;
	}

	private static PermutationResult calculateNondecreasingPermutation(double[] vals) {
		ArrayList<ValuePlace> list = new ArrayList<ValuePlace>();
		for (int i = 0; i < vals.length; i++)
			list.add(new ValuePlace(vals[i], i));
		Collections.sort(list);
		double[] newVals = new double[vals.length];
		int[] permutation = new int[vals.length];
		for (int i = 0; i < vals.length; i++) {
			newVals[i] = list.get(i).value;
			permutation[i] = list.get(i).place;
		}
		return new PermutationResult(permutation, newVals);
	}

	private static Matrix permutateColumns(Matrix m, int[] permutation) {
		assert m.getColumnDimension() == permutation.length;

		Matrix newM = new Matrix(m.getRowDimension(), m.getColumnDimension());
		for (int c = 0; c < newM.getColumnDimension(); c++) {
			int copyFrom = permutation[c];
			for (int r = 0; r < newM.getRowDimension(); r++) {
				newM.set(r, c, m.get(r, copyFrom));
			}
		}
		return newM;
	}

	private static boolean eigenvaluesAreNonIncreasing(Matrix d) {
		for (int i = 0; i < d.getRowDimension() - 1; i++)
			if (d.get(i, i) < d.get(i + 1, i + 1))
				return false;
		return true;
	}
}

class PermutationResult {

	public int[] permutation;
	public double[] values;

	public PermutationResult(int[] permutation, double[] values) {
		this.permutation = permutation;
		this.values = values;
	}

}

class ValuePlace implements Comparable<ValuePlace> {

	public double value;
	public int place;

	public ValuePlace(double value, int place) {
		this.value = value;
		this.place = place;
	}

	/**
	 * 反转对比值，使得排序方式为降序
	 */
	@Override
	public int compareTo(ValuePlace other) {
		if (this.value < other.value)
			return 1;
		if (this.value == other.value)
			return 0;
		return -1;
	}

}
