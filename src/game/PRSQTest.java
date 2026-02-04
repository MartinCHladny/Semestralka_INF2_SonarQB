package game;

public class PRSQTest {
    public PRSQTest() {
        //No method body needed
    }

    public void doSmothing() {
        int cislo = 1;
        while (true) {
            if (cislo == 0) {
                return;
            }
            if (cislo == 5) {
                cislo = 1;
            }
            cislo++;
        }
    }
}
