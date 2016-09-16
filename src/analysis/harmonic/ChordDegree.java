package analysis.harmonic;

/**
 * Class that describes a chord degree
 */
public class ChordDegree {
    private int degree_;
    private boolean seventhChord_;
    private int barFractionDen_;

    /**
     * Constructor for the ChordDegree class
     * @param degree Degree of the chord
     * @param seventhChord Specify whether it is a seventh chord or not
     */
    public ChordDegree(int degree, boolean seventhChord, int barFractionDen) {
        this.degree_ = degree;
        this.seventhChord_ = seventhChord;
        this.barFractionDen_ = barFractionDen;
    }

    /**
     * Getter for the degree attribute
     * @return Degree of the chord
     */
    public int getDegree() {
        return degree_;
    }

    /**
     * Getter for the seventhChord attribute
     * @return True if the chord is a seventh chord, False elsewhere
     */
    public boolean isSeventhChord() {
        return seventhChord_;
    }

    /**
     * Getter for the barFractionDen attribute
     * @return Denominator of the fraction of bar corresponding to this degree
     */
    public int getBarFractionDen_() {
        return barFractionDen_;
    }

    /**
     * Equality method
     * @param other other ChordDegree
     * @return True if the two ChordDegrees are equal, False elsewhere and if the param is not a ChordDegree
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ChordDegree))
            return false;
        return degree_ == ((ChordDegree) other).degree_ && seventhChord_== ((ChordDegree) other).seventhChord_;
    }

    /**
     * Conversion to String
     * @return Formatted String corresponding to the object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        switch (degree_) {
            case 0:
                sb.append("Ø");
                break;
            case 1:
                sb.append('I');
                break;
            case 2:
                sb.append("II");
                break;
            case 3:
                sb.append("III");
                break;
            case 4:
                sb.append("IV");
                break;
            case 5:
                sb.append('V');
                break;
            case 6:
                sb.append("VI");
                break;
            case 7:
                sb.append("VII");
                break;
        }
        if (seventhChord_)
            sb.append("7");

        sb.append("-1/").append(barFractionDen_).append(")");

        return sb.toString();
    }

    public void stringToDegree(String str) {
        str = str.substring(1); // remove the first bracket.
        str = str.substring(0,str.length()-1); // remove last bracket.
        String[] parts = str.split("-");
        switch (parts[0]) {
            case "I" :
                degree_ = 1;
                seventhChord_ = false;
                break;
            case "I7":
                degree_ = 1;
                seventhChord_ = true;
                break;
            case "II" :
                degree_ = 2;
                seventhChord_ = false;
                break;
            case "II7":
                degree_ = 2;
                seventhChord_ = true;
                break;
            case "III" :
                degree_ = 3;
                seventhChord_ = false;
                break;
            case "III7":
                degree_ = 3;
                seventhChord_ = true;
                break;
            case "IV" :
                degree_ = 4;
                seventhChord_ = false;
                break;
            case "IV7":
                degree_ = 3;
                seventhChord_ = true;
                break;
            case "V" :
                degree_ = 5;
                seventhChord_ = false;
                break;
            case "V7":
                degree_ = 5;
                seventhChord_ = true;
                break;
            case "VI" :
                degree_ = 6;
                seventhChord_ = false;
                break;
            case "VI7":
                degree_ = 6;
                seventhChord_ = true;
                break;
            case "VII" :
                degree_ = 7;
                seventhChord_ = false;
                break;
            case "VII7":
                degree_ = 7;
                seventhChord_ = true;
                break;
        }
        barFractionDen_ = Integer.parseInt(parts[1].substring(parts[1].length() - 1));
        return;
    }

}
