
/*
* Site: https://github.com/rohinth076/Java-Projects/tree/main/ParkingSystem
*/


import java.util.*;
import java.util.stream.Collectors;

class ParkingManagementDemo
{
    public static void main(String[] args) throws ParkingSlotNotAvailableException, VehicleNotFoundException {
        ParkingManagement pm = new ParkingManagement();
        Vehicle v1 = new ResidentVehicle("Tn472","Dharanie",12345667,100,true);
        Vehicle v2 = new ResidentVehicle("Tn572","Simren",98765432,200,false);
        Vehicle v3 = new ResidentVehicle("Tn123","Nila",45367281,300,true);
        VisitorVehicle v4 = new VisitorVehicle("Tn879","Dharanie",12345667,100,"15");
        VisitorVehicle v5 = new VisitorVehicle("Tn784","Simren",98765432,200,"12");
        VisitorVehicle v6 = new VisitorVehicle("Tn908","Nila",45367281,300,"18");
        System.out.println(pm.addVehicle(v1));
        System.out.println(pm.addVehicle(v2));
        System.out.println(pm.addVehicle(v3));
        System.out.println(pm.addVehicle(v4));
        System.out.println(pm.addVehicle(v5));
        System.out.println(pm.addVehicle(v6));
        System.out.println(pm.setVisitorVehicleOutTime("Tn879","18"));
        System.out.println(pm.setVisitorVehicleOutTime("Tn908","19"));
        System.out.println(pm.getParkedResidentVehicleCount());
        System.out.println(pm.displayAllVehicles());
    }
}


public class Vehicle {
    private String regNumber;
    private String ownerName;
    private long mobileNo;
    private long vehicleModel;

    public Vehicle(String regNumber, String ownerName, long mobileNo) {
        this.regNumber = regNumber;
        this.ownerName = ownerName;
        this.mobileNo = mobileNo;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return   "regNumber= " + regNumber + "   "+
                 ", ownerName= " + ownerName + "   " +
                 ", mobileNo=" + mobileNo+ "   ";
    }
}


class VisitorVehicle{
    private String regNumber;
    private String ownerName;
    private long mobileNo;
    private long vehicleModel;

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    private int visitingFlatNumber;
    private String inTime;
    private String outTime;

    public VisitorVehicle(String regNumber, String ownerName, long mobileNo, int visitingFlatNumber, String inTime) {
        this.regNumber=regNumber;
        this.ownerName=ownerName;
        this.mobileNo=mobileNo;
        
        this.visitingFlatNumber = visitingFlatNumber;
        this.inTime = inTime;
        this.outTime = null;
    }

    public int getVisitingFlatNumber() {
        return visitingFlatNumber;
    }

    public void setVisitingFlatNumber(int visitingFlatNumber) {
        this.visitingFlatNumber = visitingFlatNumber;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return  super.toString()+
                "visitingFlatNumber= " + visitingFlatNumber +"  "+
                ", inTime= " + inTime + "   "+
                ", outTime= " + outTime+"\n" ;
    }
}



class ResidentVehicle extends Vehicle{
    private int flatNo;
    private boolean parkingStatus;

    public ResidentVehicle(String regNumber, String ownerName, long mobileNo, int flatNo, boolean parkingStatus) {
        super(regNumber, ownerName, mobileNo);
        this.flatNo = flatNo;
        this.parkingStatus = parkingStatus;
    }

    public int getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(int flatNo) {
        this.flatNo = flatNo;
    }

    public boolean isParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(boolean parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    @Override
    public String toString() {
        return   super.toString()+
                "flatNo= " + flatNo +"   "+
                ", parkingStatus= " + parkingStatus+"\n";
    }
}

class ParkingManagement {
    private List<Vehicle> allVehicles;
    private int slot =10,s=0;
    public ParkingManagement() {
        allVehicles = new ArrayList<Vehicle>();
    }
    ArrayList<String> ResidentVehicleList=new ArrayList<String>();
    ArrayList<String> VisitorVehicleList=new ArrayList<String>();
    public String addVehicle(Vehicle vehicle) throws ParkingSlotNotAvailableException {
        if(vehicle instanceof ResidentVehicle){
            allVehicles.add(vehicle);
            return "Vehicle parked at parking slot No :R"+(allVehicles.size()-s);
        }
        else if(slot == 0){
            throw new ParkingSlotNotAvailableException();
        }
        allVehicles.add(vehicle);
        slot--;
        s++;
        return "Vehicle parked at parking slot No :V"+allVehicles.size();
    }
    public String addVehicle(VisitorVehicle vehicle) throws ParkingSlotNotAvailableException {
        if(ResidentVehicleList.contains(vehicle.getRegNumber())){
            // allVehicles.add(vehicle);
            return "Vehicle parked at parking slot No :R"+(allVehicles.size()-s);
        }
        else if(slot == 0){
            throw new ParkingSlotNotAvailableException();
        }
        
        slot--;
        s++;
        return "Vehicle parked at parking slot No :V"+allVehicles.size();
    }
    public String setVisitorVehicleOutTime(String regNumber, String outTime) throws VehicleNotFoundException {
      List<VisitorVehicle> ve =  (List)VisitorVehicleList;
      if(ve.isEmpty())
          throw new VehicleNotFoundException();
        ((VisitorVehicle)ve.get(0)).setOutTime(outTime);
        slot++;
      return "Vehicle with RegNo: "+regNumber+" updated successfully";
    }
    public int getParkedResidentVehicleCount()
    {
     return (int) allVehicles.stream().filter(i->i instanceof ResidentVehicle)
                .filter(j->((ResidentVehicle) j).isParkingStatus()).count();
    }

    public  boolean setParkingStatus(String regNumber,boolean flag){
        List<Vehicle> l= allVehicles.stream()
                                    .filter(i->i.getRegNumber().equalsIgnoreCase(regNumber))
                                    .limit(1).collect(Collectors.toList());
        if(l.isEmpty())return false;
        ((ResidentVehicle)l.get(0)).setParkingStatus(flag);
        return true;
    }

    public int getParkedVisitorVehicleCount()
    {
        return (int) allVehicles.stream().filter(i->i instanceof ResidentVehicle)
                .filter(j-> ((ResidentVehicle)j).getRegNumber() != null).count();
    }

    public String displayAllVehicles()
    {
        String s = "Resident Vehicle\n\n";
        int j = 1;
        for(Vehicle i: allVehicles)
            if(i instanceof ResidentVehicle) {
                s += j+".   ";
                s += i;
                j++;
            }
        j = 1;
        s += "\n\nVisitor Vehicle\n\n";
        
        return s;
    }

    public void randomOne(){
    	;
    }
    
    public void randomTwo(){
    	;
    }
    
    public void randomThree(){
    	;
    }
    
    public void randomFour(){
    	;
    }
}


class VehicleNotFoundException extends Throwable {
    public VehicleNotFoundException() {
    }

    public VehicleNotFoundException(String message) {
        super(message);
    }
}


class ParkingSlotNotAvailableException extends Throwable {
    public ParkingSlotNotAvailableException() {
    }

    public ParkingSlotNotAvailableException(String message) {
        super(message);
    }
}

class ParkingManagementSQL {

    private ParkingManagementSQL(){
        ;
    }
}