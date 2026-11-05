package com.example.customer.common.generator;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Locale;

@Component
public class NumberGenerator {

	private final SecureRandom random = new SecureRandom();
	private static final Long ACC_DIGIT_LIMIT = 1_000_000_000_000L;
	private static final int CVV_LIMIT = 1000;
	private static final int CARD_VALID_YEAR = 3;

	public String generateAccountNumber() {
		return String.format(Locale.ROOT, "%012d", random.nextLong(ACC_DIGIT_LIMIT));
	}

	public String generateCardNumber() {

		return "0000" + String.format(Locale.ROOT, "%012d", random.nextLong(ACC_DIGIT_LIMIT));
	}

	public String generateCvv() {
		return String.format(Locale.ROOT, "%03d", random.nextInt(CVV_LIMIT));
	}

	public YearMonth generateCardExpiry() {
		return YearMonth.now(ZoneOffset.UTC).plusYears(CARD_VALID_YEAR);
	}
}
