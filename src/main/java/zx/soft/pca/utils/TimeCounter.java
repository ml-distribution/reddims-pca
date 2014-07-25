package zx.soft.pca.utils;

import zx.soft.pca.utils.PCALog.LogType;

public class TimeCounter {

	private final String message;
	private final long startTime;

	public TimeCounter(String message) {
		this.message = message;
		this.startTime = System.currentTimeMillis();
		PCALog.info(LogType.TIME, message + ": Starting now.");
	}

	public void stop() {
		float elapsedTime = (System.currentTimeMillis() - startTime) / 1000F;
		PCALog.info(LogType.TIME, message + ": " + elapsedTime + " seconds.");
	}

}
