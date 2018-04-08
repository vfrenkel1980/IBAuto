import ibInfra.ibService.IbService;

public class TestMain {

    public static void main(String[] args) {

        IbService ibService = new IbService();
        ibService.installIB("Latest");

    }

}
