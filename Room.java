package com.company.CityLodge;

import java.util.ArrayList;

public abstract class Room {
    private String roomId;
    private String feature;
    protected int states;
    // For states, we have 1 for Available, 0 for Rented and -1 for Maintenance
    private ArrayList<HiringRecord> allRecord;
    private HiringRecord tempRecord;
    private int numOfRecord;
    private int numOfBed;
    private String roomType;
    private DateTime nextMaintenanceDate;

    public Room(String inputroomID, String inputfeature, String inputroomType, int numOfBed) {
        roomId = inputroomID;
        feature = inputfeature;
        states = 1;
        allRecord = new ArrayList<HiringRecord>();
        roomType = inputroomType;
        this.numOfBed = numOfBed;
    }

    public void setTempRecord() {
        String basicDetail = "Room ID: \t\t\t" + roomId + "\n" + "Number of bedrooms:\t" + numOfBed + "\n" + "Type:\t\t\t\t"
                + roomType + "\n" + "Feature summary:\t" + feature + "\n";
        tempRecord = new HiringRecord(basicDetail);
    }

    public void addRecord(HiringRecord newRecord) {
        if (numOfRecord == 10) {
            numOfRecord--;
            allRecord.remove(0);
        }
        allRecord.add(numOfRecord, newRecord);
        numOfRecord++;
    }

    public int getStates() {
        return states;
    }

    public String getDetail() {
        String detail = roomId + states + feature;
        return detail;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean rent(String customerId, DateTime inputrentDate, int numOfRentDay) {

        Boolean daysCheck = true;
        if (roomType.equals("Standard")) {
            if (inputrentDate.getNameOfDay().equals("Saturday") || inputrentDate.getNameOfDay().equals("Sunday")) {
                daysCheck = (numOfRentDay <= 10) && (numOfRentDay >= 3);
            } else {
                daysCheck = (numOfRentDay <= 10) && (numOfRentDay >= 2);
            }
        }
        if (roomType.equals("Suite")) {
            DateTime tempDateTime = new DateTime(inputrentDate, numOfRentDay);
            daysCheck = (nextMaintenanceDate.getTime() < tempDateTime.getTime()) && (numOfRentDay <= 10);
        }
        if (daysCheck) {
            tempRecord.setRentDate(inputrentDate);
            tempRecord.setCustom_Id(customerId);
            DateTime estRentDate = new DateTime(tempRecord.getRentDate(), numOfRentDay);
            tempRecord.setEstRentDate(estRentDate);
            states = 0;
            tempRecord.setStates("Rented");
            tempRecord.setRecordID(roomId);
            tempRecord.setRecordStage(1);
            return true;
        } else {
            return false;
        }
    }

    public boolean returnRoom(DateTime returnDate) {
        tempRecord.setAclRentDate(returnDate);
        double dailyPrice = 0;
        double latePrice=0;
        if (numOfBed == 1) {
            dailyPrice = 59;
        }
        if (numOfBed == 2) {
            dailyPrice = 99;
        }
        if (numOfBed == 4) {
            dailyPrice = 199;
        }
        latePrice=1.35*dailyPrice;
        if(numOfBed==6)
        {
            dailyPrice=999;
            latePrice=1099;
        }
        long rentDay;
        long lateDay;
        rentDay = (returnDate.getTime() - tempRecord.getRentDate().getTime()) / 86400000;
        lateDay = (returnDate.getTime() - tempRecord.getEstRentDate().getTime()) / 86400000;
        double totalPrice;
        if (lateDay <= 0) {
            totalPrice = rentDay * dailyPrice;
            tempRecord.setRentalFee(totalPrice);
            tempRecord.setLateFee(0);
        } else {
            totalPrice = (rentDay - lateDay) * dailyPrice;
            tempRecord.setRentalFee(totalPrice);
            totalPrice = lateDay *latePrice;
            tempRecord.setLateFee(totalPrice);
        }
        tempRecord.setRecordStage(2);
        states = 1;
        System.out.printf("%s is now returned by customer %s", roomId, tempRecord.getCustom_Id());
        return true;
    }

    public boolean performMaintenance()
    {
        if(states!=1)
        {
            return false;
        }
        else
        {
            tempRecord.setStates("Maintenance");
            states=-1;
        }
        return true;
    }

    public boolean completeMaintenance(DateTime completionDate)
    {
        if(states!=-1)
        {
            return false;
        }
        else
        {
            tempRecord.setStates("Available");
            states=1;
        }
        return true;
    }


    protected void setNextMaintenanceDate(DateTime nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public HiringRecord getTempRecord() {
        return tempRecord;
    }

    public String showAllRecord() {
        String fullRecord = tempRecord.getBasicDetail() + "States:\t\t\t\t" + tempRecord.getStates() + "\n";
        int i;
        if ((numOfRecord == 0) && (tempRecord.getRecordStage() == 0)) {
            fullRecord = fullRecord + "RENTAL RECORD: \t\tempty\n";
        }
        if (tempRecord.getRecordStage() != 2) {
            fullRecord = fullRecord + tempRecord.getDetail();
        }
        for (i = numOfRecord - 1; i >= 0; i--) {
            fullRecord = fullRecord + "--------------------------------------\n" + allRecord.get(i).getDetail();
        }

        return fullRecord;
    }

}