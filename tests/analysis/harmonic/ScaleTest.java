package analysis.harmonic;

import org.junit.*;
import analysis.harmonic.Scale;
import analysis.harmonic.Tonality;

import java.util.ArrayList;
import java.util.Arrays;

import static jm.constants.Pitches.*;
import static analysis.harmonic.Tonality.Mode.*;

public class ScaleTest {

    // C Major, A Minor, A HarmonicMinor, AMelodic Minor
    @Test
    public void CMajorScale() {
        Scale scale = new Scale(new Tonality(C4, MAJOR, 0, 0, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(0, 2, 4, 5, 7, 9, 11))));
    }
    @Test
    public void AMinorScale() {
        Scale scale = new Scale(new Tonality(A4, MINOR, 0, 0, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(9, 11, 12, 14, 16, 17, 19))));
    }
    @Test
    public void AHarmonicMinorScale() {
        Tonality tonality = new Tonality(A4, MINOR, 0, 0, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(9, 11, 12, 14, 16, 17, 20))));
    }
    @Test
    public void AMelodicMinorScale() {
        Tonality tonality = new Tonality(A4, MINOR, 0, 0, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(9, 11, 12, 14, 16, 18, 20))));
    }

    // All Major sharp tonalities scales.
    @Test
    public void GMajorScale() {
        Scale scale = new Scale(new Tonality(G4, MAJOR, 0, 1, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(7, 9, 11, 12, 14, 16, 18))));
    }
    @Test
    public void DMajorScale() {
        Scale scale = new Scale(new Tonality(D4, MAJOR, 0, 2, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(2, 4, 6, 7, 9, 11, 13))));
    }
    @Test
    public void AMajorScale() {
        Scale scale = new Scale(new Tonality(A4, MAJOR, 0, 3, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(9, 11, 13, 14, 16, 18, 20))));
    }
    @Test
    public void EMajorScale() {
        Scale scale = new Scale(new Tonality(E4, MAJOR, 0, 4, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(4, 6, 8, 9, 11, 13, 15))));
    }
    @Test
    public void BMajorScale() {
        Scale scale = new Scale(new Tonality(B4, MAJOR, 0, 5, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(11, 13, 15, 16, 18, 20, 22))));
    }
    @Test
    public void FSharpMajorScale() {
        Scale scale = new Scale(new Tonality(FS4, MAJOR, 1, 6, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(6, 8, 10, 11, 13, 15, 17))));
    }
    @Test
    public void CSharpMajorScale() {
        Scale scale = new Scale(new Tonality(CS4, MAJOR, 1, 7, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(1, 3, 5, 6, 8, 10, 12))));
    }

    // All Minor Sharp Tonalities.
    @Test
    public void EMinorScale() {
        Scale scale = new Scale(new Tonality(E4, MINOR, 0, 1, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(4, 6, 7, 9, 11, 12, 14))));
    }
    @Test
    public void EHarmonicMinorScale() {
        Tonality tonality = new Tonality(E4, MINOR, 0, 1, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(4, 6, 7, 9, 11, 12, 15))));
    }
    @Test
    public void EMelodicMinorScale() {
        Tonality tonality = new Tonality(E4, MINOR, 0, 1, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(4, 6, 7, 9, 11, 13, 15))));
    }

    @Test
    public void BMinorScale() {
        Scale scale = new Scale(new Tonality(B4, MINOR, 0, 2, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(11, 13, 14, 16, 18, 19, 21))));
    }
    @Test
    public void BHarmonicMinorScale() {
        Tonality tonality = new Tonality(B4, MINOR, 0, 2, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(11, 13, 14, 16, 18, 19, 22))));
    }
    @Test
    public void BMelodicMinorScale() {
        Tonality tonality = new Tonality(B4, MINOR, 0, 2, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(11, 13, 14, 16, 18, 20, 22))));
    }

    @Test
    public void FSharpMinorScale() {
        Scale scale = new Scale(new Tonality(FS4, MINOR, 1, 3, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(6, 8, 9, 11, 13, 14, 16))));
    }
    @Test
    public void FSharpHarmonicMinorScale() {
        Tonality tonality = new Tonality(FS4, MINOR, 1, 3, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(6, 8, 9, 11, 13, 14, 17))));
    }
    @Test
    public void FSharpMelodicMinorScale() {
        Tonality tonality = new Tonality(FS4, MINOR, 1, 3, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(6, 8, 9, 11, 13, 15, 17))));
    }

    @Test
    public void CSharpMinorScale() {
        Scale scale = new Scale(new Tonality(CS4, MINOR, 1, 4, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(1, 3, 4, 6, 8, 9, 11))));
    }
    @Test
    public void CSharpHarmonicMinorScale() {
        Tonality tonality = new Tonality(CS4, MINOR, 1, 4, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(1, 3, 4, 6, 8, 9, 12))));
    }
    @Test
    public void CSharpMelodicMinorScale() {
        Tonality tonality = new Tonality(CS4, MINOR, 1, 4, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(1, 3, 4, 6, 8, 10, 12))));
    }

    @Test
    public void GSharpMinorScale() {
        Scale scale = new Scale(new Tonality(GS4, MINOR, 1, 5, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 11, 13, 15, 16, 18))));
    }
    @Test
    public void GSharpHarmonicMinorScale() {
        Tonality tonality = new Tonality(GS4, MINOR, 1, 5, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 11, 13, 15, 16, 19))));
    }
    @Test
    public void GSharpMelodicMinorScale() {
        Tonality tonality = new Tonality(GS4, MINOR, 1, 5, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 11, 13, 15, 17, 19))));
    }

    @Test
    public void DSharpMinorScale() {
        Scale scale = new Scale(new Tonality(DS4, MINOR, 1, 6, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 6, 8, 10, 11, 13))));
    }
    @Test
    public void DSharpHarmonicMinorScale() {
        Tonality tonality = new Tonality(DS4, MINOR, 1, 6, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 6, 8, 10, 11, 14))));
    }
    @Test
    public void DSharpMelodicMinorScale() {
        Tonality tonality = new Tonality(DS4, MINOR, 1, 6, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 6, 8, 10, 12, 14))));
    }

    @Test
    public void ASharpMinorScale() {
        Scale scale = new Scale(new Tonality(AS4, MINOR, 1, 7, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 13, 15, 17, 18, 20))));
    }
    @Test
    public void ASharpHarmonicMinorScale() {
        Tonality tonality = new Tonality(AS4, MINOR, 1, 7, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 13, 15, 17, 18, 21))));
    }
    @Test
    public void ASharpMelodicMinorScale() {
        Tonality tonality = new Tonality(AS4, MINOR, 1, 7, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 13, 15, 17, 19, 21))));
    }

    // All Major flat tonalities scales.
    @Test
    public void FMajorScale() {
        Scale scale = new Scale(new Tonality(F4, MAJOR, 0, -1, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(5, 7, 9, 10, 12, 14, 16))));
    }
    @Test
    public void BFlatMajorScale() {
        Scale scale = new Scale(new Tonality(BF4, MAJOR, -1, -2, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 14, 15, 17, 19, 21))));
    }
    @Test
    public void EFlatMajorScale() {
        Scale scale = new Scale(new Tonality(EF4, MAJOR, -1, -3, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 7, 8, 10, 12, 14))));
    }
    @Test
    public void AFlatMajorScale() {
        Scale scale = new Scale(new Tonality(AF4, MAJOR, -1, -4, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 12, 13, 15, 17, 19))));
    }
    @Test
    public void DFlatMajorScale() {
        Scale scale = new Scale(new Tonality(DF4, MAJOR, -1, -5, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(1, 3, 5, 6, 8, 10, 12))));
    }
    @Test
    public void GFlatMajorScale() {
        Scale scale = new Scale(new Tonality(GF4, MAJOR, -1, -6, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(6, 8, 10, 11, 13, 15, 17))));
    }
    @Test
    public void CFlatMajorScale() {
        Scale scale = new Scale(new Tonality(CF4, MAJOR, -1, -7, 0), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(11, 13, 15, 16, 18, 20, 22))));
    }

    // All Minor Flat Tonalities.
    @Test
    public void DMinorScale() {
        Scale scale = new Scale(new Tonality(D4, MINOR, 0, -1, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(2, 4, 5, 7, 9, 10, 12))));
    }
    @Test
    public void DHarmonicMinorScale() {
        Tonality tonality = new Tonality(D4, MINOR, 0, -1, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(2, 4, 5, 7, 9, 10, 13))));
    }
    @Test
    public void DMelodicMinorScale() {
        Tonality tonality = new Tonality(D4, MINOR, 0, -1, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(2, 4, 5, 7, 9, 11, 13))));
    }

    @Test
    public void GMinorScale() {
        Scale scale = new Scale(new Tonality(G4, MINOR, 0, -2, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(7, 9, 10, 12, 14, 15, 17))));
    }
    @Test
    public void GHarmonicMinorScale() {
        Tonality tonality = new Tonality(G4, MINOR, 0, -2, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(7, 9, 10, 12, 14, 15, 18))));
    }
    @Test
    public void GMelodicMinorScale() {
        Tonality tonality = new Tonality(G4, MINOR, 0, -2, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(7, 9, 10, 12, 14, 16, 18))));
    }

    @Test
    public void CMinorScale() {
        Scale scale = new Scale(new Tonality(C4, MINOR, 0, -3, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(0, 2, 3, 5, 7, 8, 10))));
    }
    @Test
    public void CHarmonicMinorScale() {
        Tonality tonality = new Tonality(C4, MINOR, 0, -3, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(0, 2, 3, 5, 7, 8, 11))));
    }
    @Test
    public void CMelodicMinorScale() {
        Tonality tonality = new Tonality(C4, MINOR, 0, -3, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(0, 2, 3, 5, 7, 9, 11))));
    }

    @Test
    public void FMinorScale() {
        Scale scale = new Scale(new Tonality(F4, MINOR, 0, -4, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(5, 7, 8, 10, 12, 13, 15))));
    }
    @Test
    public void FHarmonicMinorScale() {
        Tonality tonality = new Tonality(F4, MINOR, 0, -4, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(5, 7, 8, 10, 12, 13, 16))));
    }
    @Test
    public void FMelodicMinorScale() {
        Tonality tonality = new Tonality(F4, MINOR, 0, -4, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(5, 7, 8, 10, 12, 14, 16))));
    }

    @Test
    public void BFlatMinorScale() {
        Scale scale = new Scale(new Tonality(BF4, MINOR, -1, -5, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 13, 15, 17, 18, 20))));
    }
    @Test
    public void BFlatHarmonicMinorScale() {
        Tonality tonality = new Tonality(BF4, MINOR, -1, -5, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 13, 15, 17, 18, 21))));
    }
    @Test
    public void BFlatMelodicMinorScale() {
        Tonality tonality = new Tonality(BF4, MINOR, -1, -5, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(10, 12, 13, 15, 17, 19, 21))));
    }

    @Test
    public void EFlatMinorScale() {
        Scale scale = new Scale(new Tonality(EF4, MINOR, -1, -6, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 6, 8, 10, 11, 13))));
    }
    @Test
    public void EFlatHarmonicMinorScale() {
        Tonality tonality = new Tonality(EF4, MINOR, -1, -6, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 6, 8, 10, 11, 14))));
    }
    @Test
    public void EFlatMelodicMinorScale() {
        Tonality tonality = new Tonality(EF4, MINOR, -1, -6, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(3, 5, 6, 8, 10, 12, 14))));
    }

    @Test
    public void AFlatMinorScale() {
        Scale scale = new Scale(new Tonality(AF4, MINOR, -1, -7, 1), 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 11, 13, 15, 16, 18))));
    }
    @Test
    public void AFlatHarmonicMinorScale() {
        Tonality tonality = new Tonality(AF4, MINOR, -1, -7, 1);
        tonality.setMode_(HARMONICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 11, 13, 15, 16, 19))));
    }
    @Test
    public void AFlatMelodicMinorScale() {
        Tonality tonality = new Tonality(AF4, MINOR, -1, -7, 1);
        tonality.setMode_(MELODICMINOR);
        Scale scale = new Scale(tonality, 1);
        Assert.assertTrue(scale.checkScale(new ArrayList<>(Arrays.asList(8, 10, 11, 13, 15, 17, 19))));
    }

}