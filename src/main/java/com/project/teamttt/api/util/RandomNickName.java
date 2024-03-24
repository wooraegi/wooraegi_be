package com.project.teamttt.api.util;

import java.util.Random;

public class RandomNickName {

        private static final String BASE_NICKNAME = "wooraegi";
        private static final int MAX_RANDOM_NUMBER = 99999;

        public static String generateRandomNickname() {
            Random random = new Random();
            int randomNumber = random.nextInt(MAX_RANDOM_NUMBER) + 1; // 1부터 MAX_RANDOM_NUMBER까지의 난수 생성
            return BASE_NICKNAME + String.format("%05d", randomNumber); // 일련번호를 5자리로 맞추기 위해 포맷팅
        }

        public static void main(String[] args) {
            String randomNickname = generateRandomNickname();
            System.out.println("Random Nickname: " + randomNickname);
        }
}
