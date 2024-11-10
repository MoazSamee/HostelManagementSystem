package model.User;

public class StudentModel extends UserModel {
    private String address;
    private String universityOrJob;
    private String organizationAddress;
    

    public StudentModel(String userId, String name, String email, String phoneNumber, String hostelId, String address, String universityOrJob, String organizationAddress) {
        super(userId, name, email, phoneNumber, hostelId);
        this.address = address;
        this.universityOrJob = universityOrJob;
        this.organizationAddress = organizationAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniversityOrJob() {
        return universityOrJob;
    }

    public void setUniversityOrJob(String universityOrJob) {
        this.universityOrJob = universityOrJob;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }
}
