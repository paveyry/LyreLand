package tonality;

import org.junit.*;
import static jm.constants.Pitches.*;
import static tonality.Tonality.Mode.*;

public class TonalityTest {

    // C Major & A minor (no alteration).
    @Test
    public void CMajorProcessing() { Assert.assertEquals(new Tonality(C4, MAJOR, 0), new Tonality(0, 0)); }
    @Test
    public void AMinorProcessing() { Assert.assertEquals(new Tonality(A4, MINOR, 0), new Tonality(0, 1)); }

    // All Major Sharp Tonalities.
    @Test
    public void GMajorProcessing() { Assert.assertEquals(new Tonality(G4, MAJOR, 0), new Tonality(1, 0)); }
    @Test
    public void DMajorProcessing() { Assert.assertEquals(new Tonality(D4, MAJOR, 0), new Tonality(2, 0)); }
    @Test
    public void AMajorProcessing() { Assert.assertEquals(new Tonality(A4, MAJOR, 0), new Tonality(3, 0)); }
    @Test
    public void EMajorProcessing() { Assert.assertEquals(new Tonality(E4, MAJOR, 0), new Tonality(4, 0)); }
    @Test
    public void BMajorProcessing() { Assert.assertEquals(new Tonality(B4, MAJOR, 0), new Tonality(5, 0)); }
    @Test
    public void FSharpMajorProcessing() { Assert.assertEquals(new Tonality(FS4, MAJOR, 1), new Tonality(6, 0)); }
    @Test
    public void CSharpMajorProcessing() { Assert.assertEquals(new Tonality(CS4, MAJOR, 1), new Tonality(7, 0)); }

    // All Minor Sharp Tonalities.
    @Test
    public void EMinorProcessing() { Assert.assertEquals(new Tonality(E4, MINOR, 0), new Tonality(1, 1)); }
    @Test
    public void BMinorProcessing() { Assert.assertEquals(new Tonality(B4, MINOR, 0), new Tonality(2, 1)); }
    @Test
    public void FSharpMinorProcessing() { Assert.assertEquals(new Tonality(FS4, MINOR, 1), new Tonality(3, 1)); }
    @Test
    public void CSharpMinorProcessing() { Assert.assertEquals(new Tonality(CS4, MINOR, 1), new Tonality(4, 1)); }
    @Test
    public void GSharpMinorProcessing() { Assert.assertEquals(new Tonality(GS4, MINOR, 1), new Tonality(5, 1)); }
    @Test
    public void DSharpMinorProcessing() { Assert.assertEquals(new Tonality(DS4, MINOR, 1), new Tonality(6, 1)); }
    @Test
    public void ASharpMinorProcessing() { Assert.assertEquals(new Tonality(AS4, MINOR, 1), new Tonality(7, 1)); }

    // All Major Flat Tonalities.
    @Test
    public void FMajorProcessing() { Assert.assertEquals(new Tonality(F4, MAJOR, 0), new Tonality(-1, 0)); }
    @Test
    public void BFlatMajorProcessing() { Assert.assertEquals(new Tonality(BF4, MAJOR, -1), new Tonality(-2, 0)); }
    @Test
    public void EFlatMajorProcessing() { Assert.assertEquals(new Tonality(EF4, MAJOR, -1), new Tonality(-3, 0)); }
    @Test
    public void AFlatMajorProcessing() { Assert.assertEquals(new Tonality(AF4, MAJOR, -1), new Tonality(-4, 0)); }
    @Test
    public void DFlatMajorProcessing() { Assert.assertEquals(new Tonality(DF4, MAJOR, -1), new Tonality(-5, 0)); }
    @Test
    public void GFlatMajorProcessing() { Assert.assertEquals(new Tonality(GF4, MAJOR, -1), new Tonality(-6, 0)); }
    @Test
    public void CFlatMajorProcessing() { Assert.assertEquals(new Tonality(CF4, MAJOR, -1), new Tonality(-7, 0)); }

    // All Minor Flat Tonalities.
    @Test
    public void DMinorProcessing() { Assert.assertEquals(new Tonality(D4, MINOR, 0), new Tonality(-1, 1)); }
    @Test
    public void GMinorProcessing() { Assert.assertEquals(new Tonality(G4, MINOR, 0), new Tonality(-2, 1)); }
    @Test
    public void CMinorProcessing() { Assert.assertEquals(new Tonality(C4, MINOR, 0), new Tonality(-3, 1)); }
    @Test
    public void FMinorProcessing() { Assert.assertEquals(new Tonality(F4, MINOR, 0), new Tonality(-4, 1)); }
    @Test
    public void BFlatMinorProcessing() { Assert.assertEquals(new Tonality(BF4, MINOR, -1), new Tonality(-5, 1)); }
    @Test
    public void EFlatMinorProcessing() { Assert.assertEquals(new Tonality(EF4, MINOR, -1), new Tonality(-6, 1)); }
    @Test
    public void AFlatMinorProcessing() { Assert.assertEquals(new Tonality(AF4, MINOR, -1), new Tonality(-7, 1)); }

}
