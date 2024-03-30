package com.metrostate.ics499.ers;

public class Types {

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
