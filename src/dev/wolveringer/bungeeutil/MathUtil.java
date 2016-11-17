package dev.wolveringer.bungeeutil;

import java.math.BigDecimal;

public class MathUtil {
	private static final BigDecimal PERCENT_MULTIPLYER = new BigDecimal(100);
	
	public static float calculatePercent(int count, int max) {
		BigDecimal bc = new BigDecimal(count);
		BigDecimal bmax = new BigDecimal(max);
		BigDecimal temp = bc.divide(bmax, 20, BigDecimal.ROUND_HALF_UP);
		temp = temp.multiply(PERCENT_MULTIPLYER);
		return temp.floatValue();
	}
	
	public static float pitchNormalizer(float pitch) {
		pitch %= 360.0F;
		if (pitch >= 180.0F) {
			pitch -= 360.0F;
		}
		if (pitch < -180.0F) {
			pitch += 360.0F;
		}
		return pitch;
	}
}
