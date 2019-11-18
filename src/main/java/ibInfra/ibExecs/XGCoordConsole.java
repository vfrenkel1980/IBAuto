package ibInfra.ibExecs;

import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.windowscl.IWindowsService;
import ibInfra.windowscl.WindowsService;

public class XGCoordConsole implements IXGCoordConsole {

    IWindowsService winService = new WindowsService();

    @Override
    public int deallocatePackages(String packages){
        return winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE + "/DeallocatePackages=\""+packages);
    }
}
