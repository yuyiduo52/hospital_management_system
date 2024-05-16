package edu.wku.hospital.management.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Appointment implements Serializable {
    private Person person;//person is the person make appointment
    private String desc;//description
    private String doctorId;
    private LocalDateTime appointDateTime;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointDateTime() {
        return appointDateTime;
    }

    public void setAppointDateTime(LocalDateTime appointDateTime) {
        this.appointDateTime = appointDateTime;
    }
}
