class Simple {
    public static void main(String[] args) {
       System.out.println(System.getProperty("simple.message") + args[0] + " from Simple."+ServerTest.getServer());
    }
}
