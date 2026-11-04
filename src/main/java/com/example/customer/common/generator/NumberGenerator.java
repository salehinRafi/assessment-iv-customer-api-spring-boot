package com.example.customer.common.generator;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Locale;

@Component
public class NumberGenerator {

	private final SecureRandom random = new SecureRandom();

	public String generateAccountNumber() {
		return String.format(Locale.ROOT, "%012d", random.nextLong(1_000_000_000_000L));
	}

	public String generateCardNumber() {

		return "0000" + String.format(Locale.ROOT, "%012d", random.nextLong(1_000_000_000_000L));
	}

	public String generateCvv() {
		return String.format(Locale.ROOT, "%03d", random.nextInt(1000));
	}

	public YearMonth generateCardExpiry() {
		return YearMonth.now(ZoneOffset.UTC).plusYears(3);
	}
}
