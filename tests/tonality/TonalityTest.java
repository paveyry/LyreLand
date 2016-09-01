package tonality;

import org.junit.*;
import static jm.constants.Pitches.*;
import static tonality.Tonality.Mode.*;

public class TonalityTest {
    @Test
    public void CMajorProcessing() {
        Assert.assertEquals(new Tonality(C4, MAJOR, false), new Tonality(0, 0));
    }
    @Test
    public void AMinorProcessing() {
        Assert.assertEquals(new Tonality(A4, MINOR, false), new Tonality(0, 1));
    }
}
