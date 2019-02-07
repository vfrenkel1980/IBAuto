package cloudInfra.CloudServices;

import com.microsoft.azure.management.compute.VirtualMachine;

public interface ICloudService {

    void registerCloud();

    void createNetwork();

    void createVm(int vmCount);

    void startVm(VirtualMachine vm);

    void stopVm(VirtualMachine vm);

    void deleteVm(int vmCount);
}
