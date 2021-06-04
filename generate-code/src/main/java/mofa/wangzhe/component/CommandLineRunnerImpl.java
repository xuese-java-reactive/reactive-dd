package mofa.wangzhe.component;

import mofa.wangzhe.Code;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author LD
 */

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Code.code();
        System.exit(-1);
    }
}
