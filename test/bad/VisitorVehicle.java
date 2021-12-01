package ParkingSystem.Service;

public class VisitorVehicle{
    private String regNumber;
    private String ownerName;
    private long mobileNo;

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
    public String VehicletoString() {
        return   "regNumber= " + regNumber + "   "+
                 ", ownerName= " + ownerName + "   " +
                 ", mobileNo=" + mobileNo+ "   ";
    }

    private int visitingFlatNumber;
    private String inTime;
    private String outTime;

    public VisitorVehicle(String regNumber, String ownerName, long mobileNo, int visitingFlatNumber, String inTime) {
        super(regNumber, ownerName, mobileNo);
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
    }
}

