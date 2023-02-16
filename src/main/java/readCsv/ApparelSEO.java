package readCsv;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApparelSEO {
    public static boolean flag = true;

    public static void main(String[] args) {
        log.info(" Starting the Apparel search engine optimisation ");
        FlowEngine flowEngine = new FlowEngine();
        flowEngine.start();
    }
}
