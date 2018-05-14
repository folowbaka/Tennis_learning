package modele;

public enum MovType {
    CDC("CDC"),
    CDL("CDL"),
    CDP("CDP"),
    RC("RC"),
    RL("RL"),
    RP("RP"),
    SC("SC"),
    SL("SL"),
    SMA("SMA"),
    VCD("VCD"),
    VR("VR"),
    NULL("NULL");

    private String movType;

    MovType(String movType)
    {
        this.movType=movType;
    }

    public String getMovType() {
        return movType;
    }

    public static  MovType getMovType(String movType)
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
