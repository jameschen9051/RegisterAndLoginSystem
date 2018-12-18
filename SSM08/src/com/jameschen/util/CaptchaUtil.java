package com.jameschen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaptchaUtil {
	public List<String> createCaptcha() {
		Random random = new Random();
		List<String> strList = new ArrayList<>();
		for(Integer i = 0;i < 4;i++) {
			Integer j = random.nextInt(10);
			strList.add(j.toString());
		}
		return strList;
	}
}
