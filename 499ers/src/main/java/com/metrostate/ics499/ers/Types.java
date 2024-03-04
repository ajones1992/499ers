package com.metrostate.ics499.ers;

public class Types {
    public enum designation{
        Adoptee,
        ShelterStaff,
        FosterStaff
    }

    public enum ExitCode{
        Dead,
        Runaway,
        Adopt,
        inTransit
    }

    public enum LocType {
        Shelter,
        FosterHome
    }

    public enum SpeciesAvailable {
        Cat,
        Dog,
        Rabbit,
        Bird
    }

    enum RecordType{
        Medical,
        Behavioral,
        Other
    }
}
