package readCsv;

public class FlowEngine extends Thread {
    public void run() {
        while (ApparelSEO.flag == true) {
            UserInput userInput = new UserInput();
            userInput.setParameters();
            DBUtil dbUtil = new DBUtil();
            dbUtil.fetchQueries(userInput.getColur(), userInput.getSize(), userInput.getGender(), userInput.getPrice(), userInput.getRating(), userInput.getBothpref());
        }
    }
}