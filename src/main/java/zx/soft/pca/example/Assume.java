package zx.soft.pca.example;

/**
 * 该类便于在条件不满足的情况下，抛出异常用于警告
 *
 * @author wanggang
 *
 */
public class Assume {

	public static void assume(boolean expression) {
		assume(expression, "SOS!!!");
	}

	public static void assume(boolean expression, String comment) {
		if (!expression) {
			throw new RuntimeException(comment);
		}
	}

}
