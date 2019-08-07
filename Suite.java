package com.company.CityLodge;

public class Suite extends Room {

    private DateTime maintenanceDate;
    private DateTime nextMaintenanceDate;

    public Suite(String inputroomID, String inputfeature, DateTime inputmaintenanceDate) {
        super(inputroomID, inputfeature, "Suite", 6);
        maintenanceDate = inputmaintenanceDate;
        DateTime nextMaintenanceDate = new DateTime(inputmaintenanceDate, 10);
        super.setNextMaintenanceDate(nextMaintenanceDate);
    }

    public boolean completeMaintenance(DateTime completionDate)
    {
        if(states!=-1)
        {
            return false;
        }
        else
        {
            super.getTempRecord().setStates("Available");
            states=1;
            maintenanceDate=completionDate;
            nextMaintenanceDate=new DateTime(maintenanceDate,10);
        }
        return true;
    }

}