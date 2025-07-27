package com.wangyonglin.snappay;

import com.wangyonglin.snappay.common.modules.SnowflakeIdGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SnappayApplicationTests {

    private final static Logger log = LoggerFactory.getLogger(SnappayApplicationTests.class);
    SnowflakeIdGenerator snowflakeIdGenerator;
    @Test
    void contextLoads() {
      // System.out.println(snowflakeIdGenerator.nextId());

    }

}
