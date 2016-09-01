package analysis.harmonic;

import org.junit.*;
import tonality.Tonality;
import tonality.Tonality.Mode;
import static jm.constants.Pitches.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ChordDegreeProcessorTest {
    @Test
    public void CMajorChordToDegree() {
        // Testing with C Major
        ChordDegreeProcessor cMajor = new ChordDegreeProcessor(new Tonality(C4, Mode.MAJOR, false));
        ArrayList<Integer> firstChord = new ArrayList<>(Arrays.asList(C4, E2, G6)); // Do Mi Sol
        ArrayList<Integer> secondChord = new ArrayList<>(Arrays.asList(D2, F1, A7)); // Re Fa La
        ArrayList<Integer> secondChordSeventh = new ArrayList<>(Arrays.asList(D1, F3, A3, C6)); // Re Fa La Do
        ArrayList<Integer> thirdChord = new ArrayList<>(Arrays.asList(G1, E2, B6)); // Sol Mi Si
        ArrayList<Integer> fourthChord = new ArrayList<>(Arrays.asList(C4, F4, A4)); // Do Fa La
        ArrayList<Integer> fifthChord = new ArrayList<>(Arrays.asList(G3, D2, B4, G1, D5, B6)); // Sol Re Si Sol Re Si
        ArrayList<Integer> fifthChordSeventh = new ArrayList<>(Arrays.asList(G3, D2, B4, G1, D5, F6)); // Sol Re Si Sol Re Fa
        ArrayList<Integer> sixthChord = new ArrayList<>(Arrays.asList(A3, C2, A2, C5, A1, C3, E1)); // La Do La Do La Do Mi
        ArrayList<Integer> seventhChord = new ArrayList<>(Arrays.asList(B6, D2, F1)); // Si Re Fa
        ArrayList<Integer> notADegreeChord = new ArrayList<>(Arrays.asList(C1, D4, E2, F3, G6, A1, B4)); // Do Re Mi Fa Sol La Si

        Assert.assertTrue(new ChordDegree(1, false, 1).equals(cMajor.chordToDegree(firstChord, 1)));
        Assert.assertTrue(new ChordDegree(2, false, 1).equals(cMajor.chordToDegree(secondChord, 1)));
        Assert.assertTrue(new ChordDegree(2, true, 1).equals(cMajor.chordToDegree(secondChordSeventh, 1)));
        Assert.assertTrue(new ChordDegree(3, false, 1).equals(cMajor.chordToDegree(thirdChord, 1)));
        Assert.assertTrue(new ChordDegree(4, false, 1).equals(cMajor.chordToDegree(fourthChord, 1)));
        Assert.assertTrue(new ChordDegree(5, false, 1).equals(cMajor.chordToDegree(fifthChord, 1)));
        Assert.assertTrue(new ChordDegree(5, true, 1).equals(cMajor.chordToDegree(fifthChordSeventh, 1)));
        Assert.assertTrue(new ChordDegree(6, false, 1).equals(cMajor.chordToDegree(sixthChord, 1)));
        Assert.assertTrue(new ChordDegree(7, false, 1).equals(cMajor.chordToDegree(seventhChord, 1)));

        Assert.assertTrue(new ChordDegree(0, false, 1).equals(cMajor.chordToDegree(notADegreeChord, 1)));
    }

    @Test
    public void CMinorChordToDegree() {
        ChordDegreeProcessor cMinor = new ChordDegreeProcessor(new Tonality(C4, Mode.MINOR, false));
        ArrayList<Integer> firstChord = new ArrayList<>(Arrays.asList(C4, EF2, G6)); // Do Mib Sol
        ArrayList<Integer> secondChord = new ArrayList<>(Arrays.asList(D2, F1, AF7)); // Re Fa La
        ArrayList<Integer> secondChordSeventh = new ArrayList<>(Arrays.asList(D1, F3, AF3, C6)); // Re Fa Lab Do
        ArrayList<Integer> thirdChord = new ArrayList<>(Arrays.asList(G1, EF2, BF6)); // Sol Mib Sib
        ArrayList<Integer> fourthChord = new ArrayList<>(Arrays.asList(C4, F4, AF4)); // Do Fa Lab
        ArrayList<Integer> fifthChord = new ArrayList<>(Arrays.asList(G3, D2, BF4, G1, D5, BF6)); // Sol Re Sib Sol Re Sib
        ArrayList<Integer> fifthChordSeventh = new ArrayList<>(Arrays.asList(G3, D2, BF4, G1, D5, F6)); // Sol Re Sib Sol Re Fa
        ArrayList<Integer> sixthChord = new ArrayList<>(Arrays.asList(AF3, C2, AF2, C5, AF1, C3, EF1)); // Lab Do Lab Do Lab Do Mib
        ArrayList<Integer> seventhChord = new ArrayList<>(Arrays.asList(BF6, D2, F1)); // Sib Re Fa

        Assert.assertTrue(new ChordDegree(1, false, 1).equals(cMinor.chordToDegree(firstChord, 1)));
        Assert.assertTrue(new ChordDegree(2, false, 1).equals(cMinor.chordToDegree(secondChord, 1)));
        Assert.assertTrue(new ChordDegree(2, true, 1).equals(cMinor.chordToDegree(secondChordSeventh, 1)));
        Assert.assertTrue(new ChordDegree(3, false, 1).equals(cMinor.chordToDegree(thirdChord, 1)));
        Assert.assertTrue(new ChordDegree(4, false, 1).equals(cMinor.chordToDegree(fourthChord, 1)));
        Assert.assertTrue(new ChordDegree(5, false, 1).equals(cMinor.chordToDegree(fifthChord, 1)));
        Assert.assertTrue(new ChordDegree(5, true, 1).equals(cMinor.chordToDegree(fifthChordSeventh, 1)));
        Assert.assertTrue(new ChordDegree(6, false, 1).equals(cMinor.chordToDegree(sixthChord, 1)));
        Assert.assertTrue(new ChordDegree(7, false, 1).equals(cMinor.chordToDegree(seventhChord, 1)));

    }
    @Test
    public void GbMajorChordToDegree() {
        ChordDegreeProcessor gBMajor = new ChordDegreeProcessor(new Tonality(GF4, Mode.MAJOR, false));
        ArrayList<Integer> firstChord = new ArrayList<>(Arrays.asList(GF4, BF2, DF6)); // Solb Sib Reb
        ArrayList<Integer> secondChord = new ArrayList<>(Arrays.asList(AF2, CF1, EF7)); // Lab Dob Mib
        ArrayList<Integer> secondChordSeventh = new ArrayList<>(Arrays.asList(AF2, CF1, EF7, GF6)); // Lab Dob Mib Solb
        ArrayList<Integer> thirdChord = new ArrayList<>(Arrays.asList(BF1, DF2, F6)); // Sib Reb Fa
        ArrayList<Integer> fourthChord = new ArrayList<>(Arrays.asList(GF4, CF4, EF4)); // Solb Dob Mib
        ArrayList<Integer> fifthChord = new ArrayList<>(Arrays.asList(DF3, AF2, F4, DF1, AF5, F6)); // Reb Lab Fa Reb Lab Fa
        ArrayList<Integer> fifthChordSeventh = new ArrayList<>(Arrays.asList(DF3, AF2, F4, DF1, AF5, CF6)); // Reb Lab Fa Reb Lab Dob
        ArrayList<Integer> sixthChord = new ArrayList<>(Arrays.asList(EF3, GF3, EF2, GF5, EF1, GF3, BF1)); // Mib Solb Mib Solb Mib Solb Sib
        ArrayList<Integer> seventhChord = new ArrayList<>(Arrays.asList(F6, AF2, CF1)); // Fa Lab Dob

        Assert.assertTrue(new ChordDegree(1, false, 1).equals(gBMajor.chordToDegree(firstChord, 1)));
        Assert.assertTrue(new ChordDegree(2, false, 1).equals(gBMajor.chordToDegree(secondChord, 1)));
        Assert.assertTrue(new ChordDegree(2, true, 1).equals(gBMajor.chordToDegree(secondChordSeventh, 1)));
        Assert.assertTrue(new ChordDegree(3, false, 1).equals(gBMajor.chordToDegree(thirdChord, 1)));
        Assert.assertTrue(new ChordDegree(4, false, 1).equals(gBMajor.chordToDegree(fourthChord, 1)));
        Assert.assertTrue(new ChordDegree(5, false, 1).equals(gBMajor.chordToDegree(fifthChord, 1)));
        Assert.assertTrue(new ChordDegree(5, true, 1).equals(gBMajor.chordToDegree(fifthChordSeventh, 1)));
        Assert.assertTrue(new ChordDegree(6, false, 1).equals(gBMajor.chordToDegree(sixthChord, 1)));
        Assert.assertTrue(new ChordDegree(7, false, 1).equals(gBMajor.chordToDegree(seventhChord, 1)));
    }
}
