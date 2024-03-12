package com.metrostate.ics499.ers;

public class Types {
    public enum Designation {
        ADOPTEE,
        SHELTER_STAFF,
        FOSTER_STAFF
    }

    public enum ExitCode{
        DEAD,
        RUNAWAY,
        ADOPT,
        IN_TRANSIT
    }

    public enum LocType {
        SHELTER,
        FOSTER_HOME
    }

    public enum SpeciesAvailable {
        CAT,
        DOG,
        RABBIT,
        BIRD
    }

    public enum RecordType{
        MEDICAL,
        BEHAVIORAL,
        OTHER
    }
}
