package services;

import data.Goal;
import exceptions.BadPathException;
import exceptions.DigitalSignatureException;
import exceptions.NullParameterException;
import publicadministration.Citizen;
import publicadministration.CriminalRecordCertf;

import java.io.IOException;

/**
 * External services involved in procedures from population
 */

public interface JusticeMinistry { // External service for the Justice Ministry

    CriminalRecordCertf getCriminalRecordCertf(Citizen persD, Goal g)
            throws DigitalSignatureException, IOException, BadPathException, NullParameterException;

}