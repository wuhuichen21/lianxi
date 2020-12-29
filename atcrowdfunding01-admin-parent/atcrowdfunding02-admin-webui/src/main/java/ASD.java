public class ASD {
    private boolean aaa;


    public ASD(boolean aaa) {
        this.aaa = aaa;
    }

    public ASD(){}


    public boolean isAaa() {
        return aaa;
    }

    public void setAaa(boolean aaa) {
        this.aaa = aaa;
    }


    @Override
    public String toString() {
        return "ASD{" +
                "aaa=" + aaa +
                '}';
    }
}
