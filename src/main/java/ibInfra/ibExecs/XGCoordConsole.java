package ibInfra.ibExecs;

import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.windowscl.IWindowsService;
import ibInfra.windowscl.WindowsService;

public class XGCoordConsole implements IXGCoordConsole {

    IWindowsService winService = new WindowsService();

    @Override
    public int deallocatePackages(String packages[]){
        return winService.runCommandWaitForFinish(IbLocations.XGCOORDCONSOLE_USEFILE + "/DeallocatePackages="+parsePackages(packages));
    }

    public String parsePackages(String packages[]){
        String packagesString ="\"";
        for(int i=0;i<packages.length;i++){
            packagesString+=packages[i]+", ";
        }
        packagesString +="\"";
        return packagesString;
    }
}


