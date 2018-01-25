package ibInfra.windowscl;


public interface IWindowsCL {

    int runCommandWaitForFinish(String command);
    int cleanAndBuild(String command);

}
