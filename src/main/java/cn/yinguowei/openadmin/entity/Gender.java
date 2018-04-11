package cn.yinguowei.openadmin.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 创建 by 殷国伟 于 2018/3/5.
 */

public enum Gender {
    MALE, FEMALE;

    private static final List<Gender> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Gender randomGender() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}