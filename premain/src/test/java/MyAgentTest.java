import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhongjing on 2017/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MyAgentTest {
    @Test
    public void test() throws Exception {
        sayHello();
    }


    public static void sayHello() {
        System.out.println("say hello");
    }
}
