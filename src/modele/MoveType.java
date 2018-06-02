package modele;

public enum MoveType {
    CDC("CDC"),
    CDL("CDL"),
    CDP("CDP"),
    RC("RC"),
    RL("RL"),
    RP("RP"),
    SC("SC"),
    SL("SL"),
    SP("SP"),
    SMA("SMA"),
    VCD("VCD"),
    VR("VR"),
    NULL("NULL");

    private String movType;

    MoveType(String movType)
    {
        this.movType=movType;
    }

    public String getMovType() {
        return movType;
    }

    public static MoveType getMovType(String movType)
    {
        switch (movType)
        {
            case "CDC" :
                return CDC;
            case "CDL" :
                return CDL;
            case "CDP" :
                return CDP;
            case "RC" :
                return RC;
            case "RL" :
                return RL;
            case "RP" :
                return RP;
            case "SC" :
                return SC;
            case "SL" :
                return SL;
            case "SP" :
                return SP;
            case "SMA" :
                return SMA;
            case "VCD" :
                return  VCD;
            case "VR":
                return VR;
            default:
                return NULL;
        }
    }
}
