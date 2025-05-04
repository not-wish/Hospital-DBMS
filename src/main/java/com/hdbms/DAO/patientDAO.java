package com.hdbms.DAO;

import java.util.List;

import com.hdbms.models.Patient;

public interface patientDAO {

    void save(Patient patient);
    void update(Patient patient);
    void delete(Patient patient);
    Patient getPatientById(int id);
    List<Patient> getPatients();

}