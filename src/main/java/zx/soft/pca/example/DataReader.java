package zx.soft.pca.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Jama.Matrix;

/**
 * 从CSV文件中读取数据矩阵
 *
 * @author wanggang
 *
 */
public class DataReader {

	/**
	 * 从CSV文件读取数据矩阵
	 * 其中第一行（和头相关的）被忽略掉，通过'#'将一些行注释掉。
	 *
	 * 可以通过这个类方便的封装 {@link #read(BufferedReader br, boolean ignoreLastColumn)}
	 *
	 * @param inStream          CSV文件
	 * @param ignoreLastColumn  如果为True，最后一列就被忽略；当最后一列与向量的类相关时比较有帮助
	 * @return data matrix      数据矩阵
	 */
	public static Matrix read(InputStream inStream, boolean ignoreLastColumn) throws IOException {
		DataInputStream in = new DataInputStream(inStream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		return read(br, ignoreLastColumn);
	}

	/**
	 * 从CSV文件中读取数据矩阵
	 * 首行（与头相关）被忽略T，通过'#'将一些行注释掉。
	 *
	 * @param br               CSV文件的BufferedReader对象
	 * @param ignoreLastColumn 如果为True，最后一列被忽略；当最后一列与向量的类相关时比较有帮助
	 * @return data matrix     数据矩阵
	 */
	public static Matrix read(BufferedReader br, boolean ignoreLastColumn) throws IOException {

		String line;
		int lineNo = 0;
		ArrayList<double[]> vectors = new ArrayList<double[]>();
		while ((line = br.readLine()) != null) {
			lineNo++;
			/** 忽略首行 **/
			if (lineNo == 1) {
				continue;
			}
			/** 忽略注释 **/
			int commentIndex = line.indexOf('#');
			if (commentIndex != -1) {
				line.substring(0, commentIndex);
			}
			line = line.trim();
			/** Ignore empty lines */
			if (line.length() == 0) {
				continue;
			}
			String[] elems = line.split(",");
			int elemsNo = elems.length;
			if (ignoreLastColumn) {
				elemsNo = elems.length - 1;
			}
			double[] vector = new double[elemsNo];
			for (int i = 0; i < elemsNo; i++) {
				vector[i] = Double.parseDouble(elems[i]);
			}
			vectors.add(vector);
		}

		double[][] vectorsArray = new double[vectors.size()][];
		for (int r = 0; r < vectors.size(); r++) {
			vectorsArray[r] = vectors.get(r);
		}
		Matrix m = new Matrix(vectorsArray);

		return m;
	}

}
