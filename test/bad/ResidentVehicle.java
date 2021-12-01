public class ResidentVehicle extends Vehicle{
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
