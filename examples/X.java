class X extends A {
    public X() {
        super();
    }

    private C b() {
        method1();
        new C().method2();
        return new C();
    }

    public static void main(String... args) {
        b();
    }
}